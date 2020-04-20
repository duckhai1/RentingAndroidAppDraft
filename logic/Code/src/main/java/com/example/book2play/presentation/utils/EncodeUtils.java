package com.example.book2play.presentation.utils;

import com.example.book2play.types.Booking;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class EncodeUtils {
    public static JsonObject encodeBooking(Booking b){
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("bookingId", b.getBookingId());
        responseJson.addProperty("bookingDate", b.getBookingDate().toString());
        responseJson.addProperty("bookingStartTime", b.getBookingStartTime().toString());
        responseJson.addProperty("bookingEndTime", b.getBookingEndTime().toString());
        responseJson.addProperty("city", b.getCityId());
        responseJson.addProperty("center", b.getSportCenterId());
        responseJson.addProperty("court", b.getCourtId());
        responseJson.addProperty("player", b.getPlayerId());
        responseJson.addProperty("isPaid", b.isPaid());

        return responseJson;
    }

    public static ArrayList<JsonObject> encodeBookings(ArrayList<Booking> b_array){
        ArrayList<JsonObject> jsonObjectArray = new ArrayList<>();
        for (Booking b : b_array){
            jsonObjectArray.add(encodeBooking(b));
        }
        return jsonObjectArray;
    }
}
