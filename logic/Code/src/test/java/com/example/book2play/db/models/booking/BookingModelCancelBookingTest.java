package com.example.book2play.db.models.booking;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.ModelTestSetup;
import com.example.book2play.types.Booking;
import com.example.test_utils.BookingUtils;
import com.example.test_utils.TimeUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.fail;

public class BookingModelCancelBookingTest extends ModelTestSetup {
    @Before
    public void setupMinimalPreconditionForCreatingABooking() throws Exception {
        CITY.createCity("HCM");
        SPORT_CENTER.createCityCenter("Q1", "HCM");
        COURT.createCityCenterCourt("P1", "HCM", "Q1");
        PLAYER.createPlayer("Alice");
    }

    @Test
    public void testCancelOneBookingSuccessfully() throws Exception {
        // this generate booking id with md5 for comparison
        var expectedBooking = BookingUtils.createBooking(
                TimeUtils.getDate(10),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                false, "HCM", "Q1", "P1", "Alice"
        );

        BOOKING.createBooking(
                TimeUtils.getTimestamp(),
                expectedBooking.getBookingDate(),
                expectedBooking.getBookingStartTime(),
                expectedBooking.getBookingEndTime(),
                expectedBooking.getCityId(),
                expectedBooking.getSportCenterId(),
                expectedBooking.getCourtId(),
                expectedBooking.getPlayerId()
        );

        BOOKING.cancelBooking(expectedBooking.getBookingId(), expectedBooking.getPlayerId());
    }

    @Test
    public void testCancelBookingInLessThen24HoursEarlier() throws Exception {
        final int EXPECTED_CODE = 411;

        var cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());

        Date bookingDate;
        if (cal.get(Calendar.HOUR) < 9) {
            bookingDate = TimeUtils.getDate(0);
        } else {
            bookingDate = TimeUtils.getDate(1);
        }

        var expectedBooking = BookingUtils.createBooking(
                bookingDate,
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                false, "HCM", "Q1", "P1", "Alice"
        );

        rawInsertBooking(
                expectedBooking.getBookingId(),
                expectedBooking.getBookingDate(),
                expectedBooking.getBookingStartTime(),
                expectedBooking.getBookingEndTime(),
                expectedBooking.isPaid(),
                expectedBooking.getCityId(),
                expectedBooking.getSportCenterId(),
                expectedBooking.getCourtId(),
                expectedBooking.getPlayerId()
        );

        try {
            BOOKING.cancelBooking(expectedBooking.getBookingId(), expectedBooking.getPlayerId());
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            Assert.assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCancelBookingWithInvalidBookingId() throws Exception {
        final int EXPECTED_CODE = 465;
        var bookingIds = new ArrayList<String>();
        bookingIds.add("b2");
        bookingIds.add("b2@");
        bookingIds.add("b@2");

        BOOKING.createBooking(
                //"B1",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(10),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "P1", "Alice"
        );

        for (var id : bookingIds) {
            try {
                BOOKING.cancelBooking(id, "Alice");
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void testCancelBookingWithInvalidPlayerId() throws Exception {
        final int EXPECTED_CODE = 464;
        var playerIds = new ArrayList<String>();
        playerIds.add("bob");
        playerIds.add("b@b");
        playerIds.add("ba$");

        var expectedBooking = BookingUtils.createBooking(
                TimeUtils.getDate(10),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                false, "HCM", "Q1", "P1", "Alice"
        );

        BOOKING.createBooking(
                TimeUtils.getTimestamp(),
                expectedBooking.getBookingDate(),
                expectedBooking.getBookingStartTime(),
                expectedBooking.getBookingEndTime(),
                expectedBooking.getCityId(),
                expectedBooking.getSportCenterId(),
                expectedBooking.getCourtId(),
                expectedBooking.getPlayerId()
        );

        for (var playerId : playerIds) {
            try {
                BOOKING.cancelBooking(expectedBooking.getBookingId(), playerId);
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void testCancelBookingWithUnauthorized() throws Exception {
        final int EXPECTED_CODE = 401;
        var inputBookings = new ArrayList<Booking>();
        inputBookings.add(BookingUtils.createBooking(
                TimeUtils.getDate(10),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                false, "HCM", "Q1", "P1", "Alice"
        ));
        inputBookings.add(BookingUtils.createBooking(
                TimeUtils.getDate(10),
                TimeUtils.getTime(11, 0, 0),
                TimeUtils.getTime(12, 30, 0),
                false, "HCM", "Q1", "P1", "Bob"
        ));

        PLAYER.createPlayer("Bob");
        for (var b : inputBookings) {
            BOOKING.createBooking(
                    b.getCreatedAt(),
                    b.getBookingDate(),
                    b.getBookingStartTime(),
                    b.getBookingEndTime(),
                    b.getCityId(), b.getSportCenterId(),
                    b.getCourtId(), b.getPlayerId()
            );
        }

        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 1; j++) {
                if (i != j) {
                    try {
                        BOOKING.cancelBooking(
                                inputBookings.get(i).getBookingId(),
                                inputBookings.get(j).getPlayerId()
                        );
                        fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
                    } catch (MySQLException e) {
                        Assert.assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
                    }
                }
            }
        }
    }
}
