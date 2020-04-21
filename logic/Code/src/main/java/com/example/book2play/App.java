package com.example.book2play;

import com.example.book2play.api.Server;
import com.example.book2play.db.AppDataSource;
import com.example.book2play.db.driver.MySQLDataSource;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Logger;

public class App {

    private final static Logger LOG = Logger.getAnonymousLogger();
    private final static int SRV_PORT = 8000;

    private Properties mySqlProps = new Properties();

    public App() {
        var stream = App.class
                .getClassLoader()
                .getResourceAsStream("mysql_database.properties");

        try {
            mySqlProps.load(stream);
        } catch (Exception e) {
            LOG.warning("Could not read mysql properties, using default values" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        var app = new App();
        app.start();
    }

    private void start() {
        LOG.info("Connecting to MySQL at " + mySqlProps.getProperty("url"));
        var ds = new MySQLDataSource(mySqlProps);

        setupExample(ds);

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
        BookingModel bookingModel = new BookingModel(ds);
        CityModel cityModel = new CityModel(ds);
        CourtModel courtModel = new CourtModel(ds);
        PlayerModel playerModel = new PlayerModel(ds);
        SportCenterModel sportCenterModel = new SportCenterModel(ds);

        try {
            cityModel.createCity("city1");
            sportCenterModel.createCityCenter("center1", "city1");
            courtModel.createCityCenterCourt("court1", "city1", "center1");
            playerModel.createPlayer("player1");
            bookingModel.createBooking(
                    "booking1",
                    new Timestamp(System.currentTimeMillis()),
                    Date.valueOf("2020-05-20"),
                    Time.valueOf("08:30:00"),
                    Time.valueOf("09:30:00"),
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
