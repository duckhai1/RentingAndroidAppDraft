package com.example.book2play.presentation;

import com.example.book2play.types.Booking;
import com.google.gson.Gson;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.*;

public class RestServer {

    // start server
    public static void run() throws IOException {
        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        // For processing /api/bookings
        HttpContext booking_context = server.createContext("/api/bookings", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                // handle request
                Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());
                String requestJSON = new Gson().toJson(params);
                System.out.println("requestJSON: " + requestJSON);

                // get all list of booking
                ArrayList<Booking> bookings = new ArrayList<Booking>();
                for (String bookingId : params.get("booking")){
                    /* TODO
                     Booking b = getBooking(bookingID)
                     bookings.add(b)
                     */
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
                Booking b = new Gson().fromJson(stringBuilder.toString(), Booking.class);
                /* TODO
                createBooking(b)
                 */

                // handle response
                String responseJson = new Gson().toJson(b);
                exchange.sendResponseHeaders(200, responseJson.getBytes().length);
                // make a response body
                OutputStream output = exchange.getResponseBody();
                output.write(responseJson.getBytes());
                output.flush();
            }
            else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
        // for authentication
        booking_context.setAuthenticator(new BasicAuthenticator("myrealm") {
            @Override
            public boolean checkCredentials(String user, String pwd) {
                return user.equals("admin") && pwd.equals("admin");
            }
        });

        // For processing api/center
        HttpContext center_context = server.createContext("/api/bookings", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                // TODO
            }
            else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));

        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static Map<String, List<String>> splitQuery(String query) {
        if (query == null || "".equals(query)) {
            return Collections.emptyMap();
        }

        return Pattern.compile("&").splitAsStream(query)
                .map(s -> Arrays.copyOf(s.split("="), 2))
                .collect(groupingBy(s -> decode(s[0]), mapping(s -> decode(s[1]), toList())));

    }
    private static String decode(final String encoded) {
        try {
            return encoded == null ? null : URLDecoder.decode(encoded, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 is a required encoding", e);
        }
    }
}
