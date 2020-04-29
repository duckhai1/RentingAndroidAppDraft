package com.example.book2play.db.models;

import com.example.book2play.db.driver.MySqlDataSource;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.utils.ResultSetUtils;
import com.example.test_utils.TimeUtils;
import org.junit.Before;
import org.junit.BeforeClass;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public abstract class ModelTestSetup {

    protected final static Logger LOG = Logger.getLogger("TEST");

    protected static MySQLBooking BOOKING;
    protected static MySQLCity CITY;
    protected static MySQLCourt COURT;
    protected static MySQLPlayer PLAYER;
    protected static MySQLSportCenter SPORT_CENTER;
    protected static MySQLStaff STAFF;
    protected static MySqlDataSource DB;

    @BeforeClass
    public static void setupTest() {
        DB = MySqlDataSource.getInstance();

        BOOKING = new MySQLBooking(DB);
        CITY = new MySQLCity(DB);
        COURT = new MySQLCourt(DB);
        PLAYER = new MySQLPlayer(DB);
        SPORT_CENTER = new MySQLSportCenter(DB);
        STAFF = new MySQLStaff(DB);
    }

    @Before
    public void cleanTables() throws MySQLException {
        clearBooking();
        clearCity();
        clearCourt();
        clearPlayer();
        clearSportCenter();
        clearStaff();
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

    private void clearBooking() throws MySQLException {
        LOG.info("Calling clearBooking");
        Connection conn = null;
        Statement stm = null;

        try {
            conn = DB.getConnection();
            stm = conn.createStatement();

            var updateCount = stm.executeUpdate("DELETE FROM bookings");
            LOG.info("Update count " + updateCount);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
        }
    }

    private void clearCity() throws MySQLException {
        LOG.info("Calling clearCity");
        Connection conn = null;
        Statement stm = null;
        try {
            conn = DB.getConnection();
            stm = conn.createStatement();

            var updateCount = stm.executeUpdate("DELETE FROM cities");
            LOG.info("Update count " + updateCount);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
        }
    }

    private void clearCourt() throws MySQLException {
        LOG.info("Calling clearCourt");
        Connection conn = null;
        Statement stm = null;

        try {
            conn = DB.getConnection();
            stm = conn.createStatement();

            var updateCount = stm.executeUpdate("DELETE FROM courts");
            LOG.info("Update count " + updateCount);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
        }
    }

    private void clearPlayer() throws MySQLException {
        LOG.info("Calling clearPlayer");
        Connection conn = null;
        Statement stm = null;
        try {
            conn = DB.getConnection();
            stm = conn.createStatement();

            var updateCount = stm.executeUpdate("DELETE FROM players");
            LOG.info("Update count " + updateCount);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
        }
    }

    private void clearSportCenter() throws MySQLException {
        LOG.info("Calling clearSportCenter");
        Connection conn = null;
        Statement stm = null;
        try {
            conn = DB.getConnection();
            stm = conn.createStatement();

            var updateCount = stm.executeUpdate("DELETE FROM sportCenters");
            LOG.info("Update count " + updateCount);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
        }

    }

    private void clearStaff() throws MySQLException {
        LOG.info("Calling clearStaff");
        Connection conn = null;
        Statement stm = null;
        try {
            conn = DB.getConnection();
            stm = conn.createStatement();

            var updateCount = stm.executeUpdate("DELETE FROM sportCenters");
            LOG.info("Update count " + updateCount);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
        }
    }
}
