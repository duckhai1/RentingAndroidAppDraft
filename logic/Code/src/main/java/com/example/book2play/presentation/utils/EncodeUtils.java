package com.example.book2play.presentation.utils;

import com.example.book2play.types.Booking;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class EncodeUtils {
    public static String encodeBooking(Booking b){
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("bookingId", b.getBookingId());
        responseJson.addProperty("bookingDate", b.getBookingDate().toString());
        responseJson.addProperty("bookingStartTime", b.getBookingStartTime().toString());
        responseJson.addProperty("bookingEndTime", b.getBookingEndTime().toString());
        responseJson.addProperty("city", b.getCourt().getSportCenter().getCity().getCityId());
        responseJson.addProperty("center", b.getCourt().getSportCenter().getSportCenterId());
        responseJson.addProperty("court", b.getCourt().getCourtId());
        responseJson.addProperty("player", b.getPlayer().getPlayerId());
        responseJson.addProperty("isPaid", b.isPaid());

        return new Gson().toJson(responseJson);
    }
}
