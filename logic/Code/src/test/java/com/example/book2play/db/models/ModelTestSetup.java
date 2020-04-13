package com.example.book2play.db.models;

import com.example.book2play.App;
import com.example.book2play.db.MySQLServer;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.*;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import static org.junit.Assert.fail;

public abstract class ModelTestSetup {

    protected final static Logger LOG = Logger.getLogger("TEST");

    protected static BookingModel bookingModel;
    protected static CityModel cityModel;
    protected static CourtModel courtModel;
    protected static PlayerModel playerModel;
    protected static SportCenterModel sportCenterModel;
    protected static StaffModel staffModel;

    @BeforeClass
    public static void setupTest() {
        ClassLoader loader = App.class.getClassLoader();
        InputStream stream = loader.getResourceAsStream("mysql_database.properties");

        var mySqlProps = new Properties();
        try {
            mySqlProps.load(stream);
        } catch (Exception e) {
            LOG.warning("Could not get configurations for the test database");
        }

        var srv = new MySQLServer(mySqlProps);

        bookingModel = new BookingModel(srv);
        cityModel = new CityModel(srv);
        courtModel = new CourtModel(srv);
        playerModel = new PlayerModel(srv);
        sportCenterModel = new SportCenterModel(srv);
        staffModel = new StaffModel(srv);
    }

    @Before
    public void cleanTables() {
        try {
            bookingModel.clearBooking();
            cityModel.clearCity();
            courtModel.clearCourt();
            playerModel.clearPlayer();
            sportCenterModel.clearSportCenter();
            staffModel.clearStaff();
        } catch (MySQLException e) {
            fail("Error encountered while cleaning tables " + e.getMessage());
        }
    }
}
