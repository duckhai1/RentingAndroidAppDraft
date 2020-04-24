package com.example.book2play.db.models.booking;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.ModelTestSetup;
import com.example.test_utils.Pair;
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
        BOOKING.createBooking(
                "B1",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(10),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "P1", "Alice"
        );

        BOOKING.cancelBooking("B1", "Alice");
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

        rawInsertBooking(
                "B1",
                bookingDate,
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                false,
                "HCM", "Q1", "P1", "Alice"
        );

        try {
            BOOKING.cancelBooking("B1", "Alice");
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
                "B1",
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

        BOOKING.createBooking(
                "B1",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(10),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "P1", "Alice"
        );

        for (var id : playerIds) {
            try {
                BOOKING.cancelBooking("B2", id);
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void testCancelBookingWithUnauthorized() throws Exception {
        final int EXPECTED_CODE = 401;
        var idsPairs = new ArrayList<Pair<String, String>>();
        idsPairs.add(new Pair("B1", "Bob"));
        idsPairs.add(new Pair("B2", "Alice"));

        PLAYER.createPlayer("Bob");
        BOOKING.createBooking(
                "B1",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(10),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "P1", "Alice"
        );
        BOOKING.createBooking(
                "B2",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(20),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "P1", "Bob"
        );

        for (var p : idsPairs) {
            try {
                BOOKING.cancelBooking(p.getX(), p.getY());
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
            }
        }
    }


}
