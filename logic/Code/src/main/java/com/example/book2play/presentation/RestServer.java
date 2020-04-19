package com.example.book2play.presentation;


import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

public class RestServer {
    // start server
    public static void run() throws IOException {
        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        // For processing /api/bookings

        HttpContext booking_context = server.createContext("/api/bookings", new apiBookingsHandler());
        // for authentication
        booking_context.setAuthenticator(new BasicAuthenticator("myrealm") {
            @Override
            public boolean checkCredentials(String user, String pwd) {
                return user.equals("admin") && pwd.equals("admin");
            }
        });

        // For processing api/center
        HttpContext center_context = server.createContext("/api/centers", new apiCentersHandler());

        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
