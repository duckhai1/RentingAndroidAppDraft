package com.example.book2play.db.models.booking;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.DBModelsTestSetup;
import com.example.book2play.types.Booking;
import com.example.utils.BookingUtils;
import com.example.utils.TimeUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BookingModelCreateBookingTest extends DBModelsTestSetup {

    private static int BOOKINGS_LIMIT = 3;
    private static List<Booking> TEST_BOOKINGS = new ArrayList<>();

    @Before
    public void setupPreconditionsForCreatingABooking() throws MySQLException {
        CITY.createCity("HCM");
        SPORT_CENTER.createCityCenter("Q1", "HCM");
        COURT.createCityCenterCourt("Court1", "HCM", "Q1");
        PLAYER.createPlayer("Alice");
        TEST_BOOKINGS.add(BookingUtils.createBooking(
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 0, 0),
                false, "HCM", "Q1", "Court1", "Alice"
        ));
    }

    @Test
    public void testCreateMultipleBookingsUpToTheLimit() throws Exception {
        for (int i = 0; i < BOOKINGS_LIMIT; i++) {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7 + i),
                    TimeUtils.getTime(11, 0, 0),
                    TimeUtils.getTime(12, 0, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
        }
    }

    @Test
    public void testCreateDuplicateBooking() throws Exception {
        final int EXPECTED_CODE = 407;
        var inputBooking = TEST_BOOKINGS.get(0);
        BOOKING.createBooking(
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(11, 0, 0),
                TimeUtils.getTime(12, 0, 0),
                "HCM", "Q1", "Court1", "Alice"
        );

        try {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(11, 0, 0),
                    TimeUtils.getTime(12, 0, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWhenBookingsLimitReached() throws Exception {
        final int EXPECTED_CODE = 410;

        for (int i = 0; i < BOOKINGS_LIMIT; i++) {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7 + i),
                    TimeUtils.getTime(11, 0, 0),
                    TimeUtils.getTime(12, 0, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
        }

        try {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7 + BOOKINGS_LIMIT + 1),
                    TimeUtils.getTime(11, 0, 0),
                    TimeUtils.getTime(12, 0, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }


    @Test
    public void testCreateABookingWhenPlayerHasOnePaidPastBooking() throws Exception {
        rawInsertBooking(
                "HCMQ1Court1Alice1",
                TimeUtils.getDate(-7),
                TimeUtils.getTime(11, 0, 0),
                TimeUtils.getTime(12, 0, 0),
                true,
                "HCM", "Q1", "Court1", "Alice"
        );

        BOOKING.createBooking(
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(11, 0, 0),
                TimeUtils.getTime(12, 0, 0),
                "HCM", "Q1", "Court1", "Alice"
        );
    }


    @Test
    public void testCreateABookingWhenPlayerHasOneUnpaidPastBooking() throws Exception {
        final int EXPECTED_CODE = 412;

        rawInsertBooking(
                "HCMQ1Court1Alice1",
                TimeUtils.getDate(-7),
                TimeUtils.getTime(11, 0, 0),
                TimeUtils.getTime(12, 0, 0),
                false,
                "HCM", "Q1", "Court1", "Alice"
        );

        try {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(11, 0, 0),
                    TimeUtils.getTime(12, 0, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testIfBookingsInConsecutiveTimeSlotCanBeMade() throws Exception {
        for (int i = 0; i < BOOKINGS_LIMIT; i++) {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9 + i, 0, 0),
                    TimeUtils.getTime(10 + i, 0, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
        }
    }

    @Test
    public void testCreateBookingWithOverlappingTimeCase1() throws Exception {
        final int EXPECTED_CODE = 413;
        BOOKING.createBooking(
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "Court1", "Alice"
        );

        try {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(10, 0, 0),
                    TimeUtils.getTime(11, 30, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWithOverlappingTimeCase2() throws Exception {
        final int EXPECTED_CODE = 413;
        BOOKING.createBooking(
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "Court1", "Alice"
        );

        try {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(8, 30, 0),
                    TimeUtils.getTime(10, 0, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWithOverlappingTimeCase3() throws Exception {
        final int EXPECTED_CODE = 413;
        BOOKING.createBooking(
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "Court1", "Alice"
        );

        try {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 0, 0),
                    TimeUtils.getTime(10, 0, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWithOverlappingTimeCase4() throws Exception {
        final int EXPECTED_CODE = 413;
        BOOKING.createBooking(
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "Court1", "Alice"
        );

        try {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 30, 0),
                    TimeUtils.getTime(10, 30, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWithInvalidCityId() {
        final int EXPECTED_CODE = 460;

        try {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 30, 0),
                    TimeUtils.getTime(10, 30, 0),
                    "HaNoi", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWithInvalidSportCenterId() {
        final int EXPECTED_CODE = 461;

        try {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 30, 0),
                    TimeUtils.getTime(10, 30, 0),
                    "HCM", "Q13", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWithInvalidCourtId() {
        final int EXPECTED_CODE = 462;

        try {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 30, 0),
                    TimeUtils.getTime(10, 30, 0),
                    "HCM", "Q1", "Court2", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWithInvalidPlayerId() {
        final int EXPECTED_CODE = 464;

        try {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 30, 0),
                    TimeUtils.getTime(10, 30, 0),
                    "HCM", "Q1", "Court1", "Bob"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingWithInvalidDate() {
        final int EXPECTED_CODE = 466;

        try {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(-7),
                    TimeUtils.getTime(9, 30, 0),
                    TimeUtils.getTime(10, 30, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBooking30MinDuration() {
        final int EXPECTED_CODE = 467;

        try {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 0, 0),
                    TimeUtils.getTime(9, 30, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBooking2HourDuration() {
        final int EXPECTED_CODE = 467;

        try {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 0, 0),
                    TimeUtils.getTime(11, 0, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingStartTimeNotFactorOf15Min() {
        final int EXPECTED_CODE = 468;

        try {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 10, 0),
                    TimeUtils.getTime(10, 30, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingStartTimeNotInWorkingHours() {
        final int EXPECTED_CODE = 468;

        try {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(6, 0, 0),
                    TimeUtils.getTime(7, 30, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingEndTimeNotFactorOf15Min() {
        final int EXPECTED_CODE = 469;

        try {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 0, 0),
                    TimeUtils.getTime(10, 20, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateBookingEndTimeNotInWorkingHours() {
        final int EXPECTED_CODE = 469;

        try {
            BOOKING.createBooking(
                    TimeUtils.getTimestamp(),
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(20, 0, 0),
                    TimeUtils.getTime(21, 30, 0),
                    "HCM", "Q1", "Court1", "Alice"
            );
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

}
