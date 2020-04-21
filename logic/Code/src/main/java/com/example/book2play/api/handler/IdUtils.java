package com.example.book2play.api.handler;

import com.example.book2play.types.Booking;

import java.util.UUID;

public class IdUtils {
    public static String generateBookingId(Booking booking) {
        String createBookingId =
        booking.getPlayerId()
        + booking.getCourtId()
        + booking.getSportCenterId()
        + booking.getCityId()
        + booking.getBookingStartTime().toString()
        + booking.getBookingEndTime().toString();
        return  UUID.nameUUIDFromBytes(createBookingId.getBytes()).toString().replace("-", "");
    }
}
