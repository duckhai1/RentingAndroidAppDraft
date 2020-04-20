package com.example.book2play.presentation.handler;

import com.example.book2play.App;
import com.example.book2play.db.driver.MySQLDataSource;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.BookingModel;
import com.example.book2play.presentation.utils.EncodeUtils;
import com.example.book2play.types.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class apiBookingsHandler extends apiHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //create connection with db
        ClassLoader loader = App.class.getClassLoader();
        InputStream stream = loader.getResourceAsStream("mysql_database.properties");
        var mySqlProps = new Properties();
        try {
            mySqlProps.load(stream);
        } catch (Exception e) {
            System.out.println("Could not get configurations for the database");
        }
        MySQLDataSource db= new MySQLDataSource(mySqlProps);
        BookingModel bookingModel = new BookingModel(db);

        if ("GET".equals(exchange.getRequestMethod())) {
            // handle request
            Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());
            String requestJSON = new Gson().toJson(params);
            System.out.println("requestJSON: " + requestJSON);

            // get all list of booking
            ArrayList<Booking> bookings = new ArrayList<>();
            for (String bookingId : params.get("bookingId")){
                // TODO replace this with real access database
                try {
                    Booking b = bookingModel.getBookingInfo(bookingId);
                    bookings.add(b);
                } catch (MySQLException e) {
                    e.printStackTrace();
                }
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
            String bookingId = "bookingId";
            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
            Date bookingDate = Date.valueOf(request.get("bookingDate").getAsString());
            Time bookingStartTime = Time.valueOf((request.get("bookingStartTime")).getAsString());
            Time bookingEndTime = Time.valueOf((request.get("bookingEndTime")).getAsString());
            boolean isPaid = request.get("isPaid").getAsBoolean();
            String courtId = request.get("court").getAsString();
            String sportCenterId = request.get("center").getAsString();
            String cityId = request.get("city").getAsString();
            String playerId = request.get("player").getAsString();
            //City city = new City(cityId);
            //SportCenter sportCenter = new SportCenter(sportCenterId, city);
            //Court court = new Court(courtId, sportCenter);     // TODO create court
            //Player player = new Player(playerId);


            try {
                bookingModel.createBooking(bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, cityId, sportCenterId, courtId, playerId);
            } catch (MySQLException e) {
                e.printStackTrace();
            }
            // get booking from data base if successful;
            Booking b = null;
            try {
                b = bookingModel.getBookingInfo(bookingId);
            } catch (MySQLException e) {
                e.printStackTrace();
            }
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
