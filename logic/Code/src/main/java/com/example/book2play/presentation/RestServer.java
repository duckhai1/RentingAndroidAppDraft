package com.example.book2play.presentation;


import com.example.book2play.db.AppDataSource;
import com.example.book2play.db.models.SportCenterModel;
import com.example.book2play.presentation.handler.BookingsHandler;
import com.example.book2play.presentation.handler.SportCenterHandler;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

public class RestServer {

    public static String BOOKINGS_BASE_URL = "/api/bookings";
    public static String SPORT_CENTER_BASE_URL = "/api/centers";

    private AppDataSource ds;
    private HttpServer srv;

    public RestServer(AppDataSource ds, int port) throws IOException {
        this.ds = ds;
        this.srv = HttpServer.create(new InetSocketAddress(port), 0);
    }

    public void run() throws IOException {
        setBookingsHandler();
        srv.setExecutor(null); // creates a default executor
        srv.start();
    }

    private void setBookingsHandler() {
        var ctx = srv.createContext(BOOKINGS_BASE_URL, new BookingsHandler());
        ctx.setAuthenticator(new BasicAuthenticator("myrealm") {
            @Override
            public boolean checkCredentials(String user, String pwd) {
                return user.equals("admin") && pwd.equals("admin");
            }
        });
    }

    private void setSportCentersHandler() {
        var model = new SportCenterModel(ds);
        var ctx = srv.createContext(SPORT_CENTER_BASE_URL, new SportCenterHandler(model));
    }
}
