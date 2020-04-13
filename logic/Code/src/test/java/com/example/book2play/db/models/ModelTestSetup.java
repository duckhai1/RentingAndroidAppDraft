package com.example.book2play.db.models;

import com.example.book2play.App;
import com.example.book2play.db.MySQLServer;
import com.example.book2play.db.exceptions.MySQLException;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Logger;

public abstract class ModelTestSetup {

    protected final static Logger LOG = Logger.getLogger("TEST");

    protected static BookingModel BOOKING;
    protected static CityModel CITY;
    protected static CourtModel COURT;
    protected static PlayerModel PLAYER;
    protected static SportCenterModel SPORT_CENTER;
    protected static StaffModel STAFF;
    protected static MySQLServer DB;

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

        DB = new MySQLServer(mySqlProps);

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

    protected java.sql.Timestamp getTimestamp() {
        var cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());

        var date = new java.sql.Timestamp(cal.getTimeInMillis());
        LOG.info("getDate() " + date);
        return date;
    }

    protected java.sql.Date getDate(int delta) {
        var cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DATE, delta);

        var date = new java.sql.Date(cal.getTimeInMillis());
        LOG.info("getDate() " + date);
        return date;
    }

    protected java.sql.Time getTime(int hours, int minutes, int seconds) {
        var cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, seconds);

        var time = new java.sql.Time(cal.getTimeInMillis());
        LOG.info("getTime() " + time);
        return time;
    }


}
