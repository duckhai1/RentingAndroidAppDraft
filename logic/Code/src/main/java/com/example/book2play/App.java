package com.example.book2play;

import com.example.book2play.api.Server;
import com.example.book2play.db.AppDataSource;
import com.example.book2play.db.driver.MySqlDataSource;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Storing configuration for starting an HTTP server on the specified port
 */
public class App {

    private final static Logger LOG = Logger.getAnonymousLogger();
    private final static int SRV_PORT = 8000;

    private Properties mySqlProps = new Properties();

    /**
     * The main entry point of the application, which initializes a new application
     * then start the HTTP API server on the default port
     */
    public static void main(String[] args) {
        var app = new App();
        app.start();
    }

    private void start() {
        var ds = MySqlDataSource.getInstance();

        setupExample(ds); // set up starting state for testing

        try {
            System.out.println("Starting server on port " + SRV_PORT);
            var srv = new Server(ds, SRV_PORT);
            srv.start();
        } catch (IOException e) {
            LOG.severe(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void setupExample(AppDataSource ds) {
        MySQLBooking bookingModel = new MySQLBooking(ds);
        MySQLCity cityModel = new MySQLCity(ds);
        MySQLCourt courtModel = new MySQLCourt(ds);
        MySQLPlayer playerModel = new MySQLPlayer(ds);
        MySQLSportCenter sportCenterModel = new MySQLSportCenter(ds);
        MySQLStaff staffModel = new MySQLStaff(ds);

        try {
            cityModel.createCity("city1");
            sportCenterModel.createCityCenter("center1", "city1");
            courtModel.createCityCenterCourt("court1", "city1", "center1");
            playerModel.createPlayer("player1");
            staffModel.createStaff("staff1","city1","center1");
            bookingModel.createBooking(
                    //"booking1",
                    new Timestamp(System.currentTimeMillis()),
                    Date.valueOf("2020-05-20"),
                    Time.valueOf("08:30:00"),
                    Time.valueOf("09:30:00"),
                    "city1",
                    "center1",
                    "court1",
                    "player1"
            );
            bookingModel.createBooking(
                    //"booking1",
                    new Timestamp(System.currentTimeMillis()),
                    Date.valueOf("2020-05-20"),
                    Time.valueOf("09:30:00"),
                    Time.valueOf("10:30:00"),
                    "city1",
                    "center1",
                    "court1",
                    "player1"
            );
        } catch (MySQLException e) {
            LOG.warning("Error while setting up example " + e.getMessage());
            e.printStackTrace();
        }
    }
}
