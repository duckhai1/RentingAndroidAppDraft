package com.example.book2play.db.models.booking;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.DBModelsTestSetup;
import com.example.book2play.types.Booking;
import com.example.utils.BookingUtils;
import com.example.utils.TimeUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.fail;

public class BookingModelGetCourtBookingsTest extends DBModelsTestSetup {

    private static List<Booking> TEST_BOOKINGS;

    @Before
    public void setupBookingsFromMultipleCourts() throws Exception {
        CITY.createCity("HCM");
        SPORT_CENTER.createCityCenter("Q1", "HCM");
        COURT.createCityCenterCourt("Court1", "HCM", "Q1");
        COURT.createCityCenterCourt("Court2", "HCM", "Q1");
        PLAYER.createPlayer("Alice");

        TEST_BOOKINGS = new ArrayList<>();
        TEST_BOOKINGS.add(BookingUtils.createBooking(
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(12, 0, 0),
                TimeUtils.getTime(13, 0, 0),
                false, "HCM", "Q1", "Court1", "Alice"
        ));
        TEST_BOOKINGS.add(BookingUtils.createBooking(
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(13, 30, 0),
                TimeUtils.getTime(15, 0, 0),
                false, "HCM", "Q1", "Court1", "Alice"
        ));
        TEST_BOOKINGS.add(BookingUtils.createBooking(
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(12, 0, 0),
                TimeUtils.getTime(13, 0, 0),
                false, "HCM", "Q1", "Court2", "Alice"
        ));

        for (var b : TEST_BOOKINGS) {
            BOOKING.createBooking(
                    b.getCreatedAt(),
                    b.getBookingDate(),
                    b.getBookingStartTime(),
                    b.getBookingEndTime(),
                    b.getCityId(),
                    b.getSportCenterId(),
                    b.getCourtId(),
                    b.getPlayerId()
            );
        }
    }

    @Test
    public void getBookingsSuccessfully() throws Exception {
        var expectedOutputs = new ArrayList<HashSet<Booking>>();
        var testInputs = new ArrayList<String>();

        testInputs.add("Court1");
        testInputs.add("Court2");

        var case1 = new HashSet<Booking>();
        case1.add(TEST_BOOKINGS.get(0));
        case1.add(TEST_BOOKINGS.get(1));
        expectedOutputs.add(case1);

        var case2 = new HashSet<Booking>();
        case2.add(TEST_BOOKINGS.get(2));
        expectedOutputs.add(case2);

        for (var i = 0; i < testInputs.size(); i++) {
            var expected = expectedOutputs.get(i);
            var output = new HashSet<>(BOOKING.getCourtBookings(
                    testInputs.get(i),
                    "HCM",
                    "Q1",
                    TimeUtils.getDate(7)
            ));
            Assert.assertEquals(expected, output);
        }
    }

    @Test
    public void getBookingsInvalidCourtId() {
        final int EXPECTED_CODE = 462;
        var testInputs = new ArrayList<String>();
        testInputs.add("Court!");
        testInputs.add("!Court");
        testInputs.add("Court!1");
        testInputs.add("Court4");
        testInputs.add("Court5");

        for (String testInput : testInputs) {
            try {
                var output = BOOKING.getCourtBookings(testInput, "HCM", "Q1", TimeUtils.getDate(7));
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void getBookingsInvalidSportCenterId() {
        final int EXPECTED_CODE = 461;
        var testInputs = new ArrayList<String>();
        testInputs.add("Q!");
        testInputs.add("!Q");
        testInputs.add("Q!1");
        testInputs.add("HoanKiem");

        for (var sportCenterId : testInputs) {
            try {
                var output = BOOKING.getCourtBookings("Court1", "HCM", sportCenterId, TimeUtils.getDate(7));
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void getBookingsInvalidCityId() {
        final int EXPECTED_CODE = 460;
        var testInputs = new ArrayList<String>();
        testInputs.add("H*M");
        testInputs.add("#aNoi");
        testInputs.add("HaNo!");
        testInputs.add("HaNoi");

        for (var cityId : testInputs) {
            try {
                var output = BOOKING.getCourtBookings("Court1", cityId, "Q1", TimeUtils.getDate(7));
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void getBookingsNoBookingInGivenDate() throws Exception {
        var testInputs = new ArrayList<Date>();
        testInputs.add(TimeUtils.getDate(5));
        testInputs.add(TimeUtils.getDate(20));

        for (Date testInput : testInputs) {
            var output = BOOKING.getCourtBookings("Court1", "HCM", "Q1", testInput);
            Assert.assertEquals(0, output.size());
        }
    }
}
