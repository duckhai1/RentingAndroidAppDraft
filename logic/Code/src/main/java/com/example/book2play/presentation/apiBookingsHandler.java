package com.example.book2play.presentation;

import com.example.book2play.types.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class apiBookingsHandler extends apiHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            // handle request
            Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());
            String requestJSON = new Gson().toJson(params);
            System.out.println("requestJSON: " + requestJSON);

            // get all list of booking
            ArrayList<Booking> bookings = new ArrayList<Booking>();
            for (String bookingId : params.get("bookingId")){
                // TODO replace this with real access database
                // Booking b = getBooking(bookingID)
                // bookings.add(b);

            }

            // handle response
            String responseJson = new Gson().toJson(bookings);
            exchange.sendResponseHeaders(200, responseJson.getBytes().length);
            // make a response body
            OutputStream output = exchange.getResponseBody();
            output.write(responseJson.getBytes());
            output.flush();
        } else if("POST".equals(exchange.getRequestMethod())){
            // handle request
            InputStream input = exchange.getRequestBody();
            StringBuilder stringBuilder = new StringBuilder();
            new BufferedReader(new InputStreamReader(input))
                    .lines()
                    .forEach( (String s) -> stringBuilder.append(s + "\n") );
            System.out.println("requestJSON: " + stringBuilder);

            // process request
            // TODO process the request and create booking in database
            JsonObject request = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);
            String bookingId = "booking5";
            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
            Date bookingDate = Date.valueOf(request.get("date").getAsString());
            Time bookingStartTime = Time.valueOf((request.get("start")).getAsString());
            Time bookingEndTime = Time.valueOf((request.get("end")).getAsString());
            boolean isPaid = request.get("status").getAsBoolean();
            Court court = new Court(request.get("court").getAsString(),
                    new SportCenter(request.get("center").getAsString(),
                            new City(request.get("city").getAsString())));      // TODO create court
            Player player = new Player(request.get("player").getAsString());
            Booking b = new Booking(bookingId, createdAt,bookingDate,bookingStartTime, bookingEndTime, isPaid, court, player);
            // createBooking(b)

            // handle response
            String responseJson = new Gson().toJson(b);
            System.out.println("responseJson: " + responseJson);
            exchange.sendResponseHeaders(200, responseJson.getBytes().length);
            // make a response body
            OutputStream output = exchange.getResponseBody();
            output.write(responseJson.getBytes());
            output.flush();
        } else if("PUT".equals(exchange.getRequestMethod())) {
            // TODO
        } else if("DELETE".equals(exchange.getRequestMethod())) {
            // TODO
        }
        else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }
}
