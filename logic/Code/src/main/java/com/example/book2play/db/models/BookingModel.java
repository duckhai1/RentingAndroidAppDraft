package com.example.book2play.db.models;

import com.example.book2play.db.MySQLModel;
import com.example.book2play.db.MySQLServer;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.types.Booking;
import com.example.book2play.db.utils.BookingProcedures;
import com.example.book2play.db.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class BookingModel extends MySQLModel implements BookingProcedures {

    public BookingModel(MySQLServer db) {
        super(db);
    }

    @Override
    public Booking getBookingInfo(String bookingId) throws MySQLException {
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL getBookingInfo(?, ?)}");
            stm.setString(1, bookingId);
            stm.registerOutParameter(2, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(2);
            LOG.info("Received status code " + statusCode);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }

            if (!rs.next()) {
                throw new MySQLException("Data not found");
            }

            return new Booking(
                    rs.getString("bookingId"),
                    rs.getDate("bookingDate"),
                    rs.getTime("bookingStartTime"),
                    rs.getTime("bookingEndTime"),
                    rs.getTimestamp("createdAt"),
                    rs.getBoolean("isPaid")
            );
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
            DBUtils.quietCloseResultSet(rs);
        }
    }

    @Override
    public Collection<Booking> getBookingsInCourt(String courtId, String cityId, String sportCenterId, Date date) throws MySQLException {
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL getBookingsInCourt(?, ?, ?, ?, ?)}");
            stm.setString(1, courtId);
            stm.setString(2, cityId);
            stm.setString(3, sportCenterId);
            stm.setDate(4, date);
            stm.registerOutParameter(5, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(5);
            LOG.info("Received status code " + statusCode);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }

            return DBUtils.bookingsFromResultSet(rs);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
            DBUtils.quietCloseResultSet(rs);
        }
    }

    @Override
    public Collection<Booking> getSportCenterBookings(String sportCenterId, Date date) throws MySQLException {
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL getSportCenterBookings(?, ?, ?)}");
            stm.setString(1, sportCenterId);
            stm.setDate(2, date);
            stm.registerOutParameter(2, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(2);
            LOG.info("Received status code " + statusCode);

            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }

            return DBUtils.bookingsFromResultSet(rs);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
            DBUtils.quietCloseResultSet(rs);
        }
    }

    @Override
    public Collection<Booking> getPlayerBookings(String playerId, String cityId, Date date) throws MySQLException {
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL getPlayerBookings(?, ?, ?)}");
            stm.setString(1, playerId);
            stm.setString(2, cityId);
            stm.registerOutParameter(3, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(3);
            LOG.info("Received status code " + statusCode);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }

            return DBUtils.bookingsFromResultSet(rs);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
            DBUtils.quietCloseResultSet(rs);
        }
    }

    @Override
    public void createBooking(
            String bookingId,
            Timestamp timestamp,
            Date date,
            Time startTime,
            Time endTime,
            String cityId,
            String sportCenterId,
            String courtId,
            String playerId
    ) throws MySQLException {
        Connection conn = null;
        CallableStatement stm = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL createBooking(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            stm.setString(1, bookingId);
            stm.setTimestamp(2, timestamp);
            stm.setDate(3, date);
            stm.setTime(4, startTime);
            stm.setTime(5, endTime);
            stm.setString(6, cityId);
            stm.setString(7, sportCenterId);
            stm.setString(8, courtId);
            stm.setString(9, playerId);
            stm.registerOutParameter(10, Types.INTEGER);

            var updateCount = stm.executeUpdate();
            var statusCode = stm.getInt(10);
            LOG.info("Received status code " + statusCode);
            LOG.info("Update count " + updateCount);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
        }
    }

    @Override
    public void updateBookingStatus(Boolean status, String bookingId, String playerId, String staffId) throws MySQLException {
        Connection conn = null;
        CallableStatement stm = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL updateBookingStatus(?, ?, ?, ?, ?)}");
            stm.setBoolean(1, status);
            stm.setString(2, bookingId);
            stm.setString(3, playerId);
            stm.setString(4, staffId);
            stm.registerOutParameter(5, Types.INTEGER);

            var updateCount = stm.executeUpdate();
            var statusCode = stm.getInt(5);
            LOG.info("Received status code " + statusCode);
            LOG.info("Update count " + updateCount);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
        }
    }

    @Override
    public void cancelBooking(String bookingId, String playerId) throws MySQLException {
        Connection conn = null;
        CallableStatement stm = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL cancelBooking(?, ?, ?)}");
            stm.setString(1, bookingId);
            stm.setString(2, playerId);
            stm.registerOutParameter(3, Types.INTEGER);

            var updateCount = stm.executeUpdate();
            var statusCode = stm.getInt(3);
            LOG.info("Received status code " + statusCode);
            LOG.info("Update count " + updateCount);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
        }
    }

    @Override
    public void clearBooking() throws MySQLException {
        Connection conn = null;
        Statement stm = null;

        try {
            conn = this.db.getConnection();
            stm = conn.createStatement();

            var updateCount = stm.executeUpdate("DELETE FROM bookings");
            LOG.info("Update count " + updateCount);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
        }
    }
}
