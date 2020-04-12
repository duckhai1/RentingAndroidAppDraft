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
import java.util.logging.Logger;

public class BookingModel extends MySQLModel implements BookingProcedures {

    final static Logger LOG = Logger.getAnonymousLogger();

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

            stm = conn.prepareCall("{call getBookingInfo(?,?)}");
            stm.setString(1, bookingId);
            stm.registerOutParameter(2, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(2);

            LOG.info("Received status code " + statusCode);

            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }

            return new Booking(
                    rs.getInt("bookingPK"),
                    rs.getString("bookingId"),
                    rs.getDate("bookingDate"),
                    rs.getTime("bookingStartTime"),
                    rs.getTime("bookingEndTime"),
                    rs.getTimestamp("createAt"),
                    rs.getBoolean("isPaid"),
                    rs.getInt("playerPk"),
                    rs.getInt("courtPk")
            );

        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
            DBUtils.quietCloseResultSet(rs);
        }
    }

    @Override
    public Collection<Booking> getBookings(String cityId, String sportCenterId, String courtId, Date date) throws MySQLException {
        ArrayList<Booking> bookings = new ArrayList<>();
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;
        try {
            conn = this.db.getConnection();

            stm = conn.prepareCall("{call getBookings(?,?,?,?,?)}");
            stm.setString(1, cityId);
            stm.setString(2, sportCenterId);
            stm.setString(3, courtId);
            stm.setDate(4, date);
            stm.registerOutParameter(5, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(5);
            LOG.info("Received status code " + statusCode);

            if (statusCode > 5000) {
                throw new MySQLException(statusCode);
            }
            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getInt("bookingPK"),
                        rs.getString("bookingId"),
                        rs.getDate("bookingDate"),
                        rs.getTime("bookingStartTime"),
                        rs.getTime("bookingEndTime"),
                        rs.getTimestamp("createAt"),
                        rs.getBoolean("isPaid"),
                        rs.getInt("playerPk"),
                        rs.getInt("courtPk")
                ));
            }
            return bookings;
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
        ArrayList<Booking> bookings = new ArrayList<>();
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;
        try {
            conn = this.db.getConnection();

            stm = conn.prepareCall("{call getSportenterBookings(?,?,?)}");
            stm.setString(1, sportCenterId);
            stm.setDate(2, date);
            stm.registerOutParameter(2, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(2);
            LOG.info("Received status code " + statusCode);

            if (statusCode > 500) {
                throw new MySQLException(statusCode);
            }
            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getInt("bookingPK"),
                        rs.getString("bookingId"),
                        rs.getDate("bookingDate"),
                        rs.getTime("bookingStartTime"),
                        rs.getTime("bookingEndTime"),
                        rs.getTimestamp("createAt"),
                        rs.getBoolean("isPaid"),
                        rs.getInt("playerPk"),
                        rs.getInt("courtPk")
                ));
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
            DBUtils.quietCloseResultSet(rs);
        }
        return bookings;
    }

    @Override
    public Collection<Booking> getPlayerBookings(String playerId, String cityId, Date date) throws MySQLException {
        ArrayList<Booking> bookings = new ArrayList<>();
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;
        try {
            conn = this.db.getConnection();

            stm = conn.prepareCall("{call getPlayerBookings(?,?,?)}");
            stm.setString(1, playerId);
            stm.setString(2, cityId);
            stm.registerOutParameter(3, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(3);

            LOG.info("Received status code " + statusCode);

            if (statusCode > 500) {
                throw new MySQLException(statusCode);
            }
            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getInt("bookingPK"),
                        rs.getString("bookingId"),
                        rs.getDate("bookingDate"),
                        rs.getTime("bookingStartTime"),
                        rs.getTime("bookingEndTime"),
                        rs.getTimestamp("createAt"),
                        rs.getBoolean("isPaid"),
                        rs.getInt("playerPk"),
                        rs.getInt("courtPk")
                ));
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
            DBUtils.quietCloseResultSet(rs);
        }
        return bookings;
    }

    @Override
    public void createBooking(String bookingId, Timestamp timestamp, Date date, Time startTime, Time endTime, String cityId, String sportCenterId, String courtId, String playerId) throws MySQLException {
        Connection conn = null;
        CallableStatement stm = null;
        try {
            conn = this.db.getConnection();

            stm = conn.prepareCall("{call createBooking(?,?,?,?,?,?,?,?,?)}");
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

            stm.executeUpdate();
            var statusCode = stm.getInt(10);

            LOG.info("Received status code " + statusCode);

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

            stm.executeUpdate();
            var statusCode = stm.getInt(5);

            LOG.info("Received status code " + statusCode);

            if (statusCode > 500) {
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

            stm = conn.prepareCall("{call cancelBooking(?,?,?)}");
            stm.setString(1, bookingId);
            stm.setString(2, playerId);
            stm.registerOutParameter(3, Types.INTEGER);


            stm.executeUpdate();
            var statusCode = stm.getInt(3);

            LOG.info("Received status code " + statusCode);

            if (statusCode > 500) {
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
        }
    }

    // TODO: clear all
    @Override
    public void clearBooking(String bookingId) throws MySQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = this.db.getConnection();
            stm = conn.prepareStatement("DELETE FROM bookings WHERE bookingId = ?");
            stm.setString(1, bookingId);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
        }
    }
}
