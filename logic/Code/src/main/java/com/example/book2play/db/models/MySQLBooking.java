package com.example.book2play.db.models;

import com.example.book2play.db.AppDataSource;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.utils.ResultSetUtils;
import com.example.book2play.types.Booking;

import java.sql.*;
import java.util.Collection;

/**
 * Implements BookingModel interfaces for working with the stored procedures from MySQL
 * The connection is establish using MySQL DataSource object
 */
public class MySQLBooking extends AbstractModel implements com.example.book2play.db.BookingModel {

    public MySQLBooking(AppDataSource db) {
        super(db);
    }

    /**
     * Get a connection the the data source and execute the stored procedure for getting the collection
     * of bookings that has been made with the given court
     *
     * @param courtId       an id that uniquely identifies the court in the given sport center
     * @param cityId        an id that uniquely identifies the city
     * @param sportCenterId an id that uniquely identifies the sport center in the given city
     * @param date          the booking date of all the bookings in the collection
     * @return the list of booking made with the given court
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
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

    /**
     * Get a connection the the data source and execute the stored procedure for getting the collection
     * of bookings that has been made with the given sport center
     *
     * @param sportCenterId an id that uniquely identifies the sport center in the given city
     * @param cityId        an id that uniquely identifies the city
     * @param date          the booking date of all the bookings in the collection
     * @return the collection of bookings with the given sport center
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
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

    /**
     * Create a new connection to the data source and call the stored procedure
     * to get all bookings made by the given player
     *
     * @param playerId an id that uniquely identifies the player
     * @return collection of bookings made by the player
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
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

    /**
     * Create a new connection to the data source, and get a collection of bookings made by the player
     * constraint by the city and the booking date
     *
     * @param playerId an id that uniquely identifies the player
     * @param cityId   an id that uniquely identifies the city
     * @param date     the booking date of all the bookings in the collection
     * @return collection of bookings that satisfy the constraints
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
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

    /**
     * Create a new connection to the data source and call the stored procedure to create a booking
     *
     * @param timestamp     the date and time of when the booking was created
     * @param date          the date of the booking
     * @param startTime     the start time of the booking
     * @param endTime       the end time of the booking
     * @param cityId        the unique identifier to the city
     * @param sportCenterId the unique identifier, in the given city, to a sport center
     * @param courtId       the unique identifier, in the given sport center, to a court
     * @param playerId      the unique identifier of the player who made the booking
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    @Override
    public void createBooking(
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
            stm = conn.prepareCall("{CALL createBooking(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            stm.setTimestamp(1, timestamp);
            stm.setDate(2, date);
            stm.setTime(3, startTime);
            stm.setTime(4, endTime);
            stm.setString(5, cityId);
            stm.setString(6, sportCenterId);
            stm.setString(7, courtId);
            stm.setString(8, playerId);
            stm.registerOutParameter(9, Types.INTEGER);

            var updateCount = stm.executeUpdate();
            var statusCode = stm.getInt(9);
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

    /**
     * Create a new connection to the data source and call the procedure
     * to to update status of the booking.
     * The action should be made by the staff working at an authorized sport center
     *
     * @param status        the new status
     * @param bookingId     the unique identifier of the booking
     * @param cityId        the unique identifier of the city
     * @param sportCenterId the unique identifier of the sport center in the given city
     * @param staffId       the unique identifier of the staff in the given sport center
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
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

    /**
     * Create a new connection to the data source and call the procedure
     * to to remove the booking from the relation.
     * The action should be made by the player who owns the booking
     *
     * @param bookingId the unique identifier of the booking that will be removed
     * @param playerId  the unique identifier of the player performing this action
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
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

    /**
     * Get a new connection to the data source and clear the relation
     *
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     * @deprecated will be moved to test only
     */
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
