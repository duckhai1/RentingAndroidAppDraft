package com.example.book2play.api;


import com.example.book2play.api.handler.*;
import com.example.book2play.api.utils.FbTokenValidator;
import com.example.book2play.db.*;
import com.example.book2play.db.models.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Simple class to encapsulate the data source, and connection information used for the HTTP API server
 */
public class Server {

    public static String BOOKING_BASE_URL = "/api/bookings";
    public static String SPORT_CENTER_BASE_URL = "/api/centers";
    public static String COURT_BASE_URL = "/api/courts";
    public static String PLAYER_BASE_URL = "/api/players";
    public static String CITY_BASE_URL = "/api/cities";
    public static String STAFF_BASE_URL = "/api/staffs";
    public static String SLOT_BASE_URL = "/api/slots";

    protected AppDataSource ds;
    protected TokenAuthenticator authModel;
    protected BookingModel bookingModel;
    protected CityModel cityModel;
    protected CourtModel courtModel;
    protected PlayerModel playerModel;
    protected SportCenterModel sportCenterModel;
    protected StaffModel staffModel;
    protected HttpServer srv;

    public Server(AppDataSource ds, int port) throws IOException {
        this.srv = HttpServer.create(new InetSocketAddress(port), 0);
        this.ds = ds;
    }

    protected void setupModels() {
        bookingModel = new MySQLBooking(ds);
        cityModel = new MySQLCity(ds);
        courtModel = new MySQLCourt(ds);
        playerModel = new MySQLPlayer(ds);
        sportCenterModel = new MySQLSportCenter(ds);
        staffModel = new MySQLStaff(ds);
        authModel = new FbTokenValidator(playerModel, staffModel);
    }

    /**
     * Starting the server and listen for requests on the given port
     * Block the current thread where it is called
     */
    public void start() {
        setupModels();
        setRoutes();
        srv.setExecutor(null); // creates a default executor
        srv.start();
    }

    private void setRoutes() {
        srv.createContext(BOOKING_BASE_URL, new BookingsHandler(bookingModel, authModel));
        srv.createContext(SPORT_CENTER_BASE_URL, new SportCentersHandler(sportCenterModel, authModel));
        srv.createContext(CITY_BASE_URL, new CityHandler(cityModel, authModel));
        srv.createContext(COURT_BASE_URL, new CourtsHandler(courtModel, authModel));
        srv.createContext(PLAYER_BASE_URL, new PlayerHandler(playerModel, authModel));
        srv.createContext(STAFF_BASE_URL, new StaffHandler(staffModel, authModel));
        srv.createContext(SLOT_BASE_URL, new SlotHandler(bookingModel, sportCenterModel, authModel));
    }
}
