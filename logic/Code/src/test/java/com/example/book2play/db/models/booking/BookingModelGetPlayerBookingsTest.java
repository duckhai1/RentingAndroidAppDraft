package com.example.book2play.db.models.booking;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.ModelTestSetup;
import com.example.book2play.types.Booking;
import com.example.test_utils.BookingUtils;
import com.example.test_utils.TimeUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.fail;

public class BookingModelGetPlayerBookingsTest extends ModelTestSetup {

    private static ArrayList<Booking> TEST_BOOKINGS;

    @Before
    public void setupBookingsFromMultiplePlayers() throws Exception {
        CITY.createCity("HCM");
        SPORT_CENTER.createCityCenter("Q1", "HCM");
        COURT.createCityCenterCourt("Court1", "HCM", "Q1");
        PLAYER.createPlayer("Alice");
        PLAYER.createPlayer("Bob");

        TEST_BOOKINGS = new ArrayList<>();
        TEST_BOOKINGS.add(BookingUtils.createBooking(
                TimeUtils.getDate(7),
                TimeUtils.getTime(12, 0, 0),
                TimeUtils.getTime(13, 0, 0),
                false, "HCM", "Q1", "Court1", "Alice"
        ));
        TEST_BOOKINGS.add(BookingUtils.createBooking(
                TimeUtils.getDate(7),
                TimeUtils.getTime(13, 30, 0),
                TimeUtils.getTime(15, 0, 0),
                false, "HCM", "Q1", "Court1", "Alice"
        ));
        TEST_BOOKINGS.add(BookingUtils.createBooking(
                TimeUtils.getDate(7),
                TimeUtils.getTime(9, 0, 0),
                TimeUtils.getTime(10, 0, 0),
                false, "HCM", "Q1", "Court1", "Bob"
        ));

        for (var b : TEST_BOOKINGS) {
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
    public void getPlayerBookingsSuccessfully() throws Exception {
        var expectedOutputs = new ArrayList<HashSet<Booking>>();
        var testInputs = new ArrayList<String>();
        testInputs.add("Alice");
        testInputs.add("Bob");

        var case1 = new HashSet<Booking>();
        case1.add(TEST_BOOKINGS.get(0));
        case1.add(TEST_BOOKINGS.get(1));
        expectedOutputs.add(case1);

        var case2 = new HashSet<Booking>();
        case2.add(TEST_BOOKINGS.get(2));
        expectedOutputs.add(case2);

        for (var i = 0; i < testInputs.size(); i++) {
            var expected = expectedOutputs.get(i);
            var output = new HashSet<>(BOOKING.getPlayerBookings(testInputs.get(i)));
            Assert.assertEquals(expected, output);
        }
    }

    @Test
    public void getPlayerBookingsInvalidPlayerId() {
        final int EXPECTED_CODE = 464;
        var testInputs = new ArrayList<String>();
        testInputs.add("@lice");
        testInputs.add("Ali(&");
        testInputs.add("B__b");

        for (var testInput : testInputs) {
            try {
                var output = BOOKING.getPlayerBookings(testInput);
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }
}
