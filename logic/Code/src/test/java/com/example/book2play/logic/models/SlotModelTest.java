package com.example.book2play.logic.models;

import com.example.book2play.types.Booking;
import com.example.book2play.types.Slot;
import com.example.book2play.utils.TimeUtils;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SlotModelTest {

    private final static SlotModel SLOT = new SlotModel("07:00:00", "21:00:00", 45);
    private final static Timestamp timestamp = TimeUtils.getTimestamp();
    private final static Date bookingDate = TimeUtils.getDate(7);
    private final static String cityId = "HCM";
    private final static String sportCenterId = "Q1";
    private final static String courtId = "Court1";
    private final static String playerId = "Alice";

    @Test
    public void testGetAvailableSlotWithOneBookingWithin30MinOfTheOpenTime() {
        var expectedSlots = new ArrayList<Slot>();
        expectedSlots.add(createMockSlot("09:00:00", "21:00:00"));

        var bookings = new ArrayList<Booking>();
        bookings.add(createMockBooking("07:30:00", "09:00:00"));
        var slots = SLOT.getAvailableSlots(bookings, cityId, sportCenterId, courtId);
        assertEquals("incorrect slots returned", expectedSlots, slots);
    }

    private Slot createMockSlot(String startTime, String endTime) {
        return new Slot(courtId, sportCenterId, cityId, Time.valueOf(startTime), Time.valueOf(endTime));
    }

    private Booking createMockBooking(String openTime, String closeTime) {
        return new Booking("booking", timestamp, bookingDate,
                Time.valueOf(openTime), Time.valueOf(closeTime),
                false, cityId, sportCenterId, courtId, playerId
        );
    }
}
