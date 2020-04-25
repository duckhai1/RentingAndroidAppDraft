package com.example.book2play.api;


import com.example.book2play.api.handler.BookingsHandler;
import com.example.book2play.api.handler.CourtsHandler;
import com.example.book2play.api.handler.SportCentersHandler;
import com.example.book2play.db.AppDataSource;
import com.example.book2play.db.models.BookingModel;
import com.example.book2play.db.models.CourtModel;
import com.example.book2play.db.models.SportCenterModel;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Simple class to encapsulate the data source, and connection information used for the HTTP API server
 */
public class Server {

    public static String BOOKINGS_BASE_URL = "/api/bookings";
    public static String SPORT_CENTER_BASE_URL = "/api/centers";
    public static String COURT_BASE_URL = "/api/courts";

    private AppDataSource ds;
    private HttpServer srv;

    public Server(AppDataSource ds, int port) throws IOException {
        this.ds = ds;
        this.srv = HttpServer.create(new InetSocketAddress(port), 0);
        setRoutes();
    }

    /**
     * Starting the server and listen for requests on the given port
     * Block the current thread where it is called
     */
    public void start() {
        srv.setExecutor(null); // creates a default executor
        srv.start();
    }

    private void setRoutes() {
        setBookingsHandler();
        setSportCentersHandler();
        setCourtsHandler();
    }

    private void setBookingsHandler() {
        var model = new BookingModel(ds);
        var ctx = srv.createContext(BOOKINGS_BASE_URL, new BookingsHandler(model));
        ctx.setAuthenticator(new BasicAuthenticator("myrealm") {
            @Override
            public boolean checkCredentials(String user, String pwd) {
                return user.equals("admin") && pwd.equals("admin");
            }
        });
    }

    private void setSportCentersHandler() {
        var model = new SportCenterModel(ds);
        var ctx = srv.createContext(SPORT_CENTER_BASE_URL, new SportCentersHandler(model));
    }

    private void setCourtsHandler() {
        var model = new CourtModel(ds);
        var ctx = srv.createContext(COURT_BASE_URL, new CourtsHandler(model));
    }
}
