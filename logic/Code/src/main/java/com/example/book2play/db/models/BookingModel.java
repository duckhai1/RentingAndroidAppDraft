package com.example.book2play.db.models;

import com.example.book2play.db.AppDataSource;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.utils.ResultSetUtils;
import com.example.book2play.types.Booking;

import java.sql.*;
import java.util.Collection;

public class BookingModel extends AbstractModel implements com.example.book2play.db.BookingModel {

    public BookingModel(AppDataSource db) {
        super(db);
    }

    @Override
    public Collection<Booking> getCourtBookings(String courtId, String cityId, String sportCenterId, Date date) throws MySQLException {
        LOG.info("Calling getCourtBookings");
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL getCourtBookings(?, ?, ?, ?, ?)}");
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

            return ResultSetUtils.bookingsFromResultSet(rs);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
            ResultSetUtils.quietCloseResultSet(rs);
        }
    }

    @Override
    public Collection<Booking> getSportCenterBookings(String sportCenterId, String cityId, Date date) throws MySQLException {
        LOG.info("Calling getSportCenterBookings");
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL getSportCenterBookings(?, ?, ?, ?)}");
            stm.setString(1, sportCenterId);
            stm.setString(2, cityId);
            stm.setDate(3, date);
            stm.registerOutParameter(4, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(4);
            LOG.info("Received status code " + statusCode);

            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }

            return ResultSetUtils.bookingsFromResultSet(rs);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
            ResultSetUtils.quietCloseResultSet(rs);
        }
    }

    @Override
    public Collection<Booking> getPlayerBookings(String playerId) throws MySQLException {
        LOG.info("Calling getPlayerBookings");
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL getPlayerBookings(?, ?)}");
            stm.setString(1, playerId);
            stm.registerOutParameter(2, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(2);
            LOG.info("Received status code " + statusCode);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }

            return ResultSetUtils.bookingsFromResultSet(rs);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
            ResultSetUtils.quietCloseResultSet(rs);
        }
    }

    @Override
    public Collection<Booking> getPlayerBookingsInCity(String playerId, String cityId, Date date) throws MySQLException {
        LOG.info("Calling getPlayerBookingsInCity");
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL getPlayerBookingsInCity(?, ?, ?, ?)}");
            stm.setString(1, playerId);
            stm.setString(2, cityId);
            stm.setDate(3, date);
            stm.registerOutParameter(4, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(4);
            LOG.info("Received status code " + statusCode);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }

            return ResultSetUtils.bookingsFromResultSet(rs);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
            ResultSetUtils.quietCloseResultSet(rs);
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
        LOG.info("Calling createBooking");
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
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
        }
    }

    @Override
    public void updateBookingStatus(Boolean status, String bookingId, String cityId, String sportCenterId, String staffId) throws MySQLException {
        LOG.info("Calling updateBookingStatus");
        Connection conn = null;
        CallableStatement stm = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL updateBookingStatus(?, ?, ?, ?, ?, ?)}");
            stm.setBoolean(1, status);
            stm.setString(2, bookingId);
            stm.setString(3, cityId);
            stm.setString(4, sportCenterId);
            stm.setString(5, staffId);
            stm.registerOutParameter(6, Types.INTEGER);

            var updateCount = stm.executeUpdate();
            var statusCode = stm.getInt(6);
            LOG.info("Received status code " + statusCode);
            LOG.info("Update count " + updateCount);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
        }
    }

    @Override
    public void cancelBooking(String bookingId, String playerId) throws MySQLException {
        LOG.info("Calling cancelBooking");
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
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
        }
    }

    @Override
    public void clearBooking() throws MySQLException {
        LOG.info("Calling clearBooking");
        Connection conn = null;
        Statement stm = null;

        try {
            conn = this.db.getConnection();
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
}
