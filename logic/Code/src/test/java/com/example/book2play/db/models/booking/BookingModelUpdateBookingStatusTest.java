package com.example.book2play.db.models.booking;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.ModelTestSetup;
import com.example.test_utils.TimeUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.fail;

public class BookingModelUpdateBookingStatusTest extends ModelTestSetup {
    @Before
    public void setupMinimalPreconditionForUpdatingABooking() throws Exception {
        CITY.createCity("HCM");
        SPORT_CENTER.createCityCenter("Q1", "HCM");
        SPORT_CENTER.createCityCenter("Q2", "HCM");
        COURT.createCityCenterCourt("P1", "HCM", "Q1");
        PLAYER.createPlayer("Alice");
        STAFF.createStaff("Bob", "HCM", "Q1");
        STAFF.createStaff("Charlie", "HCM", "Q2");
    }

    @Test
    public void testUpdateOneBookingSuccessfully() throws Exception {
        BOOKING.createBooking(
                //"B1",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(10),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "P1", "Alice"
        );

        BOOKING.updateBookingStatus(true, "B1", "HCM", "Q1", "Bob");
    }

    @Test
    public void testUpdateBookingInvalidBookingId() throws Exception {
        final int EXPECTED_CODE = 465;

        var testCases = new ArrayList<String>();
        testCases.add("B!");
        testCases.add("!B");
        testCases.add("B!!");
        testCases.add("B!w");
        testCases.add("B2");

        BOOKING.createBooking(
                //"B1",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(10),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "P1", "Alice"
        );

        for (var b : testCases) {
            try {
                BOOKING.updateBookingStatus(true, b, "HCM", "Q1", "Bob");
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void testUpdateBookingInvalidCityId() throws Exception {
        final int EXPECTED_CODE = 460;
        var testInputs = new ArrayList<String>();
        testInputs.add("H*M");
        testInputs.add("#aNoi");
        testInputs.add("HaNo!");
        testInputs.add("HaNoi");

        BOOKING.createBooking(
                //"B1",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(10),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "P1", "Alice"
        );

        for (var c : testInputs) {
            try {
                BOOKING.updateBookingStatus(true, "B1", c, "Q1", "Bob");
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void testUpdateBookingInvalidSportCenterId() throws Exception {
        final int EXPECTED_CODE = 461;
        var testInputs = new ArrayList<String>();
        testInputs.add("Q!");
        testInputs.add("!Q");
        testInputs.add("Q!1");
        testInputs.add("Q3");
        testInputs.add("Q4");

        BOOKING.createBooking(
                //"B1",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(10),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "P1", "Alice"
        );

        for (var c : testInputs) {
            try {
                BOOKING.updateBookingStatus(true, "B1", "HCM", c, "Bob");
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void testUpdateBookingWithInvalidStaffId() throws Exception {
        final int EXPECTED_CODE = 463;
        var testInputs = new ArrayList<String>();
        testInputs.add("@lice");
        testInputs.add("Ali(&");
        testInputs.add("B__b");

        BOOKING.createBooking(
                //"B1",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(10),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "P1", "Alice"
        );

        for (var s : testInputs) {
            try {
                BOOKING.updateBookingStatus(true, "B1", "HCM", "Q1", s);
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void testUpdateBookingWithUnauthorizedSportCenter() throws Exception {
        final int EXPECTED_CODE = 401;

        BOOKING.createBooking(
                //"B1",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(10),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 30, 0),
                "HCM", "Q1", "P1", "Alice"
        );

        try {
            BOOKING.updateBookingStatus(true, "B1", "HCM", "Q2", "Charlie");
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
        }
    }
}
