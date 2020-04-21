package com.example.book2play.api.handler;

import com.example.book2play.App;
import com.example.book2play.api.utils.EncodeUtils;
import com.example.book2play.db.driver.MySQLDataSource;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.BookingModel;
import com.example.book2play.types.Booking;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

public class BookingsHandler extends AbstractHandler {

    BookingModel model;

    public BookingsHandler(BookingModel model) {
        super();
        this.model = model;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            execGet(exchange);
        } else if("POST".equals(exchange.getRequestMethod())){
            execPost(exchange);
        } else if("PUT".equals(exchange.getRequestMethod())) {
            execPut(exchange);
        } else if("DELETE".equals(exchange.getRequestMethod())) {
            execDelete(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1); // NOT ALLOWED
        }
        exchange.close();
    }

    // TODO: this make no sense
    private void execGet(HttpExchange exchange) throws IOException {
        // handle request
        Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());
        String requestJSON = new Gson().toJson(params);
        System.out.println("requestJSON: " + requestJSON);

        // get all list of booking
        ArrayList<Booking> bookings = new ArrayList<>();

        String courtId = params.get("courtId").toString();
        String cityId = params.get("cityId").toString();
        String sportCenterId = params.get("sportCenterId").toString();
        Date date = Date.valueOf(params.get("date").toString());
        try {
            bookings = (ArrayList<Booking>) model.getBookingsInCourt(courtId, cityId, sportCenterId, date);
        } catch (MySQLException e) {
            e.printStackTrace();
        }
        // handle response
        ArrayList<JsonObject> responseJsonList = EncodeUtils.encodeBookings(bookings);
        String responseJson = new Gson().toJson(responseJsonList);
        exchange.sendResponseHeaders(200, responseJson.getBytes().length);
        // make a response body
        OutputStream output = exchange.getResponseBody();
        output.write(responseJson.getBytes());
        output.flush();
        // TODO: This is the original one
        // handle request

        // InputStream input = exchange.getRequestBody();
        // StringBuilder stringBuilder = new StringBuilder();
        // new BufferedReader(new InputStreamReader(input))
        //        .lines()
        //        .forEach( (String s) -> stringBuilder.append(s + "\n") );
        // System.out.println("requestJSON: " + stringBuilder);

        // process request
        // JsonObject request = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);
        // ArrayList<Booking> bookings = new ArrayList<>();
        //  // for (String bookingId : params.get("bookingId")){
        //     try {
        //         Booking b = model.getBookingInfo(bookingId);
        //         bookings.add(b);
        //     } catch (MySQLException e) {
        //         e.printStackTrace();
        //     }
        //     // Booking b = getBooking(bookingID)
        //     // bookings.add(b);


        // // handle response
        // ArrayList<JsonObject> responseJsonList = EncodeUtils.encodeBookings(bookings);
        // String responseJson = new Gson().toJson(responseJsonList);
        // exchange.sendResponseHeaders(200, responseJson.getBytes().length);
        // make a response body
        // OutputStream output = exchange.getResponseBody();
        // output.write(responseJson.getBytes());
        // output.flush();
    }

    // TODO: refactor hard
    private void execPost(HttpExchange exchange) throws IOException {
        // handle request
        InputStream input = exchange.getRequestBody();
        StringBuilder stringBuilder = new StringBuilder();
        new BufferedReader(new InputStreamReader(input))
                .lines()
                .forEach( (String s) -> stringBuilder.append(s + "\n") );
        System.out.println("requestJSON: " + stringBuilder);

        // process request
        JsonObject request = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);
        String bookingId = "booking1";
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
        Date bookingDate = Date.valueOf(request.get("bookingDate").getAsString());
        Time bookingStartTime = Time.valueOf((request.get("bookingStartTime")).getAsString());
        Time bookingEndTime = Time.valueOf((request.get("bookingEndTime")).getAsString());
        boolean isPaid = request.get("isPaid").getAsBoolean();
        String courtId = request.get("court").getAsString();
        String sportCenterId = request.get("center").getAsString();
        String cityId = request.get("city").getAsString();
        String playerId = request.get("player").getAsString();

        try {
            model.createBooking(bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, cityId, sportCenterId, courtId, playerId);
        } catch (MySQLException e) {
            e.printStackTrace();
        }
        // get booking from data base if successful;
        Booking b = null;
        try {
            b = model.getBookingInfo(bookingId);
        } catch (MySQLException e) {
            e.printStackTrace();
        }
        // createBooking(b)

        // handle response
        JsonObject responseJsonObject = EncodeUtils.encodeBooking(b);
        String responseJson = new Gson().toJson(responseJsonObject);
        System.out.println("responseJson: " + responseJson);
        exchange.sendResponseHeaders(200, responseJson.getBytes().length);
        // make a response body
        OutputStream output = exchange.getResponseBody();
        output.write(responseJson.getBytes());
        output.flush();
    }

    private void execPut(HttpExchange exchange) {
    }

    private void execDelete(HttpExchange exchange) {
    }
}
