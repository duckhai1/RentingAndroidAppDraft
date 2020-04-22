package com.example.book2play.db.models;

import com.example.book2play.App;
import com.example.book2play.db.driver.MySQLDataSource;
import com.example.book2play.db.exceptions.MySQLException;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public abstract class AbstractModelTest {

    protected final static Logger LOG = Logger.getLogger("TEST");

    protected static BookingModel BOOKING;
    protected static CityModel CITY;
    protected static CourtModel COURT;
    protected static PlayerModel PLAYER;
    protected static SportCenterModel SPORT_CENTER;
    protected static StaffModel STAFF;
    protected static MySQLDataSource DB;

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

        DB = new MySQLDataSource(mySqlProps);

        BOOKING = new BookingModel(DB);
        CITY = new CityModel(DB);
        COURT = new CourtModel(DB);
        PLAYER = new PlayerModel(DB);
        SPORT_CENTER = new SportCenterModel(DB);
        STAFF = new StaffModel(DB);
    }

    @Before
    public void cleanTables() throws MySQLException {
        BOOKING.clearBooking();
        CITY.clearCity();
        COURT.clearCourt();
        PLAYER.clearPlayer();
        SPORT_CENTER.clearSportCenter();
        STAFF.clearStaff();
    }



}
