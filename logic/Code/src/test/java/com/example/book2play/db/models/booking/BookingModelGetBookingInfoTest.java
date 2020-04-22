package com.example.book2play.db.models.booking;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.ModelTestSetup;
import com.example.book2play.types.Booking;
import com.example.book2play.types.Staff;
import com.example.test_utils.TimeUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class BookingModelGetBookingInfoTest extends ModelTestSetup {

    private static List<Booking> testBookings;

    @Before
    public void setBookings() throws Exception {
        CITY.createCity("HCM");
        SPORT_CENTER.createCityCenter("Q1", "HCM");
        COURT.createCityCenterCourt("P1", "HCM", "Q1");
        PLAYER.createPlayer("Alice");

        testBookings = new ArrayList<>();
        testBookings.add(new Booking(
                "B1",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(12, 0, 0),
                TimeUtils.getTime(13, 0, 0),
                false, "HCM", "Q1", "P1", "Alice"
        ));
        testBookings.add(new Booking(
                "B2",
                TimeUtils.getTimestamp(),
                TimeUtils.getDate(7),
                TimeUtils.getTime(13, 30, 0),
                TimeUtils.getTime(15, 0, 0),
                false, "HCM", "Q1", "P1", "Alice"
        ));

        for (var b : testBookings) {
            LOG.info(b.getBookingId());
            BOOKING.createBooking(
                    b.getBookingId(),
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
    public void testGetBookingInfoSuccess() throws Exception {
        var testInputs = new ArrayList<String>();
        testInputs.add("B1");
        testInputs.add("B2");

        var expected = new ArrayList<Booking>();
        expected.add(testBookings.get(0));
        expected.add(testBookings.get(1));

        for (var i = 0; i < testInputs.size(); i++) {
            var output = BOOKING.getBookingInfo(testInputs.get(i));
            Assert.assertEquals(expected.get(i), output);
        }
    }

    @Test
    public void testGetStaffInfoInvalidBookingId() throws Exception {
        final int EXPECTED_CODE = 465;
        var testInputs = new ArrayList<String>();
        testInputs.add("B!");
        testInputs.add("!B");
        testInputs.add("B__1");

        for (String bookingId : testInputs) {
            try {
                BOOKING.getBookingInfo(bookingId);
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
            }
        }
    }
}
