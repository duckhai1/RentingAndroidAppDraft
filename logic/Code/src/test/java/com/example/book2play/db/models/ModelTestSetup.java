package com.example.book2play.db.models;

import com.example.book2play.App;
import com.example.book2play.db.driver.MySqlDataSource;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.test_utils.TimeUtils;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.InputStream;
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
    protected static MySqlDataSource DB;

    @BeforeClass
    public static void setupTest() {
        DB = MySqlDataSource.getInstance();

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

    protected void rawInsertBooking(
            String bookingId,
            java.sql.Date bookingDate,
            java.sql.Time bookingStartTime,
            java.sql.Time bookingEndTime,
            Boolean isPaid,
            String cityId,
            String sportCenterId,
            String courtId,
            String playerId
    ) throws Exception {
        var conn = DB.getConnection();

        // get courtPk
        var stmt1 = conn.prepareStatement("SELECT courtPk FROM courts NATURAL JOIN sportCenters NATURAL JOIN cities WHERE cityId = ? AND sportCenterId = ? AND courtId = ?");
        stmt1.setString(1, cityId);
        stmt1.setString(2, sportCenterId);
        stmt1.setString(3, courtId);
        var stmt1RS = stmt1.executeQuery();
        if (!stmt1RS.next()) {
            throw new Exception("Data not found");
        }
        var courtPk = stmt1RS.getString("courtPk");
        LOG.info("courtPk " + courtPk);

        // get playerPk
        var stmt2 = conn.prepareStatement("SELECT playerPk FROM players WHERE playerId = ?");
        stmt2.setString(1, playerId);
        var stmt2RS = stmt2.executeQuery();
        if (!stmt2RS.next()) {
            throw new Exception("Data not found");
        }
        var playerPk = stmt2RS.getString("playerPk");
        LOG.info("courtPk " + playerPk);

        // create booking
        var stmt = conn.prepareStatement("" +
                "INSERT INTO bookings (" +
                "bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, courtPk, playerPk" +
                ")" + " VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, bookingId);
        stmt.setTimestamp(2, TimeUtils.getTimestamp());
        stmt.setDate(3, bookingDate);
        stmt.setTime(4, bookingStartTime);
        stmt.setTime(5, bookingEndTime);
        stmt.setBoolean(6, isPaid);
        stmt.setString(7, courtPk);
        stmt.setString(8, playerPk);
        if (stmt.executeUpdate() != 1) {
            throw new Exception("Incorrect number of updated rows");
        }
    }
}
