package com.example.book2play.db.models.booking;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.ModelTestSetup;
import com.example.book2play.types.Booking;
import com.example.test_utils.TimeUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.fail;

public class BookingModelGetSportCenterBookingsTest extends ModelTestSetup {

    private static ArrayList<Booking> testBookings;

    @Before
    public void setupBookingsFromMultipleSportCenters() throws Exception {
        CITY.createCity("HCM");
        SPORT_CENTER.createCityCenter("Q1", "HCM");
        SPORT_CENTER.createCityCenter("Q2", "HCM");
        COURT.createCityCenterCourt("Court1", "HCM", "Q1");
        COURT.createCityCenterCourt("Court2", "HCM", "Q2");
        PLAYER.createPlayer("Alice");

        testBookings = new ArrayList<>();
        testBookings.add(new Booking(
                "B1",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(12, 0, 0),
                TimeUtils.getTime(13, 0, 0),
                false, "HCM", "Q1", "Court1", "Alice"
        ));
        testBookings.add(new Booking(
                "B2",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(13, 30, 0),
                TimeUtils.getTime(15, 0, 0),
                false, "HCM", "Q1", "Court1", "Alice"
        ));
        testBookings.add(new Booking(
                "B3",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(12, 30, 0),
                TimeUtils.getTime(14, 0, 0),
                false, "HCM", "Q2", "Court2", "Alice"
        ));

        for (var b : testBookings) {
            BOOKING.createBooking(
                    //b.getBookingId(),
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

        testInputs.add("Q1");
        testInputs.add("Q2");

        var case1 = new HashSet<Booking>();
        case1.add(testBookings.get(0));
        case1.add(testBookings.get(1));
        expectedOutputs.add(case1);

        var case2 = new HashSet<Booking>();
        case2.add(testBookings.get(2));
        expectedOutputs.add(case2);

        for (var i = 0; i < testInputs.size(); i++) {
            var expected = expectedOutputs.get(i);
            var output = new HashSet<>(BOOKING.getSportCenterBookings(testInputs.get(i), "HCM", TimeUtils.getDate(7)));
            Assert.assertEquals(expected, output);
        }
    }

    @Test
    public void getBookingsInvalidSportCenterId() throws Exception {
        final int EXPECTED_CODE = 461;
        var testInputs = new ArrayList<String>();
        testInputs.add("Q!");
        testInputs.add("!Q");
        testInputs.add("Q!1");
        testInputs.add("Q3");
        testInputs.add("Q4");

        for (var i = 0; i < testInputs.size(); i++) {
            try {
                var output = BOOKING.getSportCenterBookings(testInputs.get(i), "HCM", TimeUtils.getDate(7));
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void getBookingsInvalidCityId() throws Exception {
        final int EXPECTED_CODE = 460;
        var testInputs = new ArrayList<String>();
        testInputs.add("H*M");
        testInputs.add("#aNoi");
        testInputs.add("HaNo!");
        testInputs.add("HaNoi");


        for (var i = 0; i < testInputs.size(); i++) {
            try {
                var input = testInputs.get(i);
                var output = BOOKING.getSportCenterBookings("Q1", testInputs.get(i), TimeUtils.getDate(7));
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void getBookingsNoBookingInGivenDate() throws Exception {
        final int EXPECTED_CODE = 466;
        var testInputs = new ArrayList<Date>();
        testInputs.add(TimeUtils.getDate(5));
        testInputs.add(TimeUtils.getDate(20));

        for (var i = 0; i < testInputs.size(); i++) {
            try {
                var output = BOOKING.getSportCenterBookings("Q1", "HCM", testInputs.get(i));
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }
}
