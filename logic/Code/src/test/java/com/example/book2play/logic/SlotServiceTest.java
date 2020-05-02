package com.example.book2play.logic;

import com.example.book2play.types.Booking;
import com.example.book2play.types.Slot;
import com.example.utils.TimeUtils;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SlotServiceTest {

    private final static SlotService SLOT = new SlotService("07:00:00", "21:00:00", 45);
    private final static Timestamp timestamp = TimeUtils.getTimestamp();
    private final static Date bookingDate = TimeUtils.getDate(7);
    private final static String courtId = "Court1";
    private final static String sportCenterId = "Q1";
    private final static String cityId = "HCM";
    private final static String playerId = "Alice";

    @Test
    public void testGetAvailableSlotWithNoBooking() {
        var bookings = new ArrayList<Booking>();

        var expectedSlots = new ArrayList<com.example.book2play.types.Slot>();
        expectedSlots.add(createMockSlot("07:00:00", "21:00:00"));

        var slots = SLOT.getAvailableSlots(bookings, cityId, sportCenterId, courtId);
        assertEquals("incorrect slots returned", expectedSlots, slots);
    }

    @Test
    public void testGetAvailableSlotsWithOneBookingWithin30MinOfTheOpenTime() {
        var bookings = new ArrayList<Booking>();
        bookings.add(createMockBooking("07:30:00", "09:00:00"));

        var expectedSlots = new ArrayList<com.example.book2play.types.Slot>();
        expectedSlots.add(createMockSlot("09:00:00", "21:00:00"));

        var slots = SLOT.getAvailableSlots(bookings, cityId, sportCenterId, courtId);
        assertEquals("incorrect slots returned", expectedSlots, slots);
    }

    @Test
    public void testGetAvailableSlotsWithOneBookingWithin30MinOfTheCloseTime() {
        var bookings = new ArrayList<Booking>();
        bookings.add(createMockBooking("19:00:00", "20:30:00"));

        var expectedSlots = new ArrayList<com.example.book2play.types.Slot>();
        expectedSlots.add(createMockSlot("07:00:00", "19:00:00"));

        var slots = SLOT.getAvailableSlots(bookings, cityId, sportCenterId, courtId);
        assertEquals("incorrect slots returned", expectedSlots, slots);
    }

    @Test
    public void testGetAvailableSlotsOneBookingInTheMiddleOfTheWorkingHours() {
        var bookings = new ArrayList<Booking>();
        bookings.add(createMockBooking("13:00:00", "14:00:00"));

        var expectedSlots = new ArrayList<com.example.book2play.types.Slot>();
        expectedSlots.add(createMockSlot("07:00:00", "13:00:00"));
        expectedSlots.add(createMockSlot("14:00:00", "21:00:00"));

        var slots = SLOT.getAvailableSlots(bookings, cityId, sportCenterId, courtId);
        assertEquals("incorrect slots returned", expectedSlots, slots);
    }

    @Test
    public void testGetAvailableSlotsTwoBookingsInTheMiddleOfTheWorkingHoursSeparateBy30Min() {
        var bookings = new ArrayList<Booking>();
        bookings.add(createMockBooking("12:00:00", "13:30:00"));
        bookings.add(createMockBooking("14:00:00", "15:30:00"));

        var expectedSlots = new ArrayList<com.example.book2play.types.Slot>();
        expectedSlots.add(createMockSlot("07:00:00", "12:00:00"));
        expectedSlots.add(createMockSlot("15:30:00", "21:00:00"));

        var slots = SLOT.getAvailableSlots(bookings, cityId, sportCenterId, courtId);
        assertEquals("incorrect slots returned", expectedSlots, slots);
    }

    @Test
    public void testGetAvailableSlotsTwoBookingsInTheMiddleOfTheWorkingHoursSeparateBy45Minutes() {
        var bookings = new ArrayList<Booking>();
        bookings.add(createMockBooking("10:00:00", "11:00:00"));
        bookings.add(createMockBooking("11:45:00", "12:30:00"));

        var expectedSlots = new ArrayList<com.example.book2play.types.Slot>();
        expectedSlots.add(createMockSlot("07:00:00", "10:00:00"));
        expectedSlots.add(createMockSlot("11:00:00", "11:45:00"));
        expectedSlots.add(createMockSlot("12:30:00", "21:00:00"));

        var slots = SLOT.getAvailableSlots(bookings, cityId, sportCenterId, courtId);
        assertEquals("incorrect slots returned", expectedSlots, slots);
    }

    @Test
    public void testGetAvailableSlotsFullBookingsSeparatedBy30Mins() {
        var bookings = new ArrayList<Booking>();
        bookings.add(createMockBooking("07:00:00", "08:30:00"));
        bookings.add(createMockBooking("09:00:00", "10:30:00"));
        bookings.add(createMockBooking("11:00:00", "12:30:00"));
        bookings.add(createMockBooking("13:00:00", "14:30:00"));
        bookings.add(createMockBooking("15:00:00", "16:30:00"));
        bookings.add(createMockBooking("17:00:00", "18:30:00"));
        bookings.add(createMockBooking("19:00:00", "20:30:00"));

        var expectedSlots = new ArrayList<com.example.book2play.types.Slot>();

        var slots = SLOT.getAvailableSlots(bookings, cityId, sportCenterId, courtId);
        assertEquals("incorrect slots returned", expectedSlots, slots);
    }

    @Test
    public void testGetAvailableSlotsFullBookingsSeparatedBy45Mins() {
        var bookings = new ArrayList<Booking>();
        bookings.add(createMockBooking("07:00:00", "08:15:00"));
        bookings.add(createMockBooking("09:00:00", "10:15:00"));
        bookings.add(createMockBooking("11:00:00", "12:15:00"));
        bookings.add(createMockBooking("13:00:00", "14:15:00"));
        bookings.add(createMockBooking("15:00:00", "16:15:00"));
        bookings.add(createMockBooking("17:00:00", "18:15:00"));
        bookings.add(createMockBooking("19:00:00", "20:15:00"));

        var expectedSlots = new ArrayList<com.example.book2play.types.Slot>();
        expectedSlots.add(createMockSlot("08:15:00", "09:00:00"));
        expectedSlots.add(createMockSlot("10:15:00", "11:00:00"));
        expectedSlots.add(createMockSlot("12:15:00", "13:00:00"));
        expectedSlots.add(createMockSlot("14:15:00", "15:00:00"));
        expectedSlots.add(createMockSlot("16:15:00", "17:00:00"));
        expectedSlots.add(createMockSlot("18:15:00", "19:00:00"));
        expectedSlots.add(createMockSlot("20:15:00", "21:00:00"));

        var slots = SLOT.getAvailableSlots(bookings, cityId, sportCenterId, courtId);
        assertEquals("incorrect slots returned", expectedSlots, slots);
    }

    private Slot createMockSlot(String startTime, String endTime) {
        return new com.example.book2play.types.Slot(Time.valueOf(startTime), Time.valueOf(endTime), cityId, sportCenterId, courtId);
    }

    private Booking createMockBooking(String openTime, String closeTime) {
        return new Booking(
                "booking",
                timestamp,
                bookingDate,
                Time.valueOf(openTime),
                Time.valueOf(closeTime),
                false,
                cityId,
                sportCenterId,
                courtId,
                playerId
        );
    }
}
