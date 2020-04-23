package com.example.book2play.db.models.utils;

import com.example.book2play.types.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

public class ResultSetUtils {
    private final static Logger LOG = Logger.getLogger("UTILS");

    /**
     * Get a booking from the ResultSet
     * The result set should be check before calling this function
     *
     * @param rs the result set that is storing the booking
     * @return an object of Booking class
     * @throws SQLException if error happened while reading the result set
     */
    public static Booking singleBookingFromResultSet(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getString("bookingId"),
                rs.getTimestamp("createdAt"),
                rs.getDate("bookingDate"),
                rs.getTime("bookingStartTime"),
                rs.getTime("bookingEndTime"),
                rs.getBoolean("isPaid"),
                rs.getString("cityId"),
                rs.getString("sportCenterId"),
                rs.getString("courtId"),
                rs.getString("playerId")
        );
    }

    /**
     * Get a all the bookings from the result set
     *
     * @param rs the result set that is storing the booking
     * @return an object of Booking class
     * @throws SQLException if error happened while reading the result set
     */
    public static Collection<Booking> bookingsFromResultSet(ResultSet rs) throws SQLException {
        var bookings = new LinkedList<Booking>();
        while (rs.next()) {
            bookings.add(singleBookingFromResultSet(rs));
        }
        return bookings;
    }

    /**
     * Get a all the bookings from the result set
     *
     * @param rs the result set that is storing the city
     * @return an object of Booking class
     * @throws SQLException if error happened while reading the result set
     */
    public static City singleCityFromResultSet(ResultSet rs) throws SQLException {
        return new City(rs.getString("cityId"));
    }

    /**
     * Get all the bookings from the result set
     *
     * @param rs the result set that is storing the cities
     * @return a collection of objects of the City class
     * @throws SQLException if error happened while reading the result set
     */
    public static Collection<City> citiesFromResultSet(ResultSet rs) throws SQLException {
        var cities = new LinkedList<City>();
        while (rs.next()) {
            cities.add(singleCityFromResultSet(rs));
        }
        return cities;
    }

    /**
     * Get a sport center from the result set
     *
     * @param rs the result set that is storing the sport center
     * @return an object of SportCenter class
     * @throws SQLException if error happened while reading the result set
     */
    public static SportCenter singleSportCenterFromResultSet(ResultSet rs) throws SQLException {
        return new SportCenter(
                rs.getString("sportCenterId"),
                rs.getString("cityId")
        );
    }

    /**
     * Get all the sport centers from the result set
     *
     * @param rs the result set that is storing the sport centers
     * @return a collection of objects of the SportCenter class
     * @throws SQLException if error happened while reading the result set
     */
    public static Collection<SportCenter> sportCentersFromResultSet(ResultSet rs) throws SQLException {
        var sportCenters = new LinkedList<SportCenter>();
        while (rs.next()) {
            sportCenters.add(singleSportCenterFromResultSet(rs));
        }
        return sportCenters;
    }

    /**
     * Get a court from the result set
     *
     * @param rs the result set that is storing the court
     * @return an object of Court class
     * @throws SQLException if error happened while reading the result set
     */
    public static Court singleCourtFromResultSet(ResultSet rs) throws SQLException {
        return new Court(
                rs.getString("courtId"),
                rs.getString("cityId"),
                rs.getString("sportCenterId")
        );
    }

    /**
     * Get all the courts from the result set
     *
     * @param rs the result set that is storing the courts
     * @return a collection of objects of the Court class
     * @throws SQLException if error happened while reading the result set
     */
    public static Collection<Court> courtsFromResultSet(ResultSet rs) throws SQLException {
        var courts = new LinkedList<Court>();
        while (rs.next()) {
            courts.add(singleCourtFromResultSet(rs));
        }
        return courts;
    }


    /**
     * Get a staff from the result set
     *
     * @param rs the result set that is storing the staff
     * @return an object of Staff class
     * @throws SQLException if error happened while reading the result set
     */
    public static Staff singleStaffFromResultSet(ResultSet rs) throws SQLException {
        return new Staff(
                rs.getString("staffId"),
                rs.getString("cityId"),
                rs.getString("sportCenterId")
        );
    }

    /**
     * Get a player from the result set
     *
     * @param rs the result set that is storing the player
     * @return an object of Player class
     * @throws SQLException if error happened while reading the result set
     */
    public static Player singlePlayerFromResultSet(ResultSet rs) throws SQLException {
        return new Player(
                rs.getString("playerId")
        );
    }


    /**
     * Close a connection and log any error that might happen
     *
     * @param conn the connection that we trying to close
     */
    public static void quietCloseConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOG.warning("Could not close connection: " + e.getMessage());
            }
        }
    }

    /**
     * Close a statement and log any error that might happen
     *
     * @param statement the statement the we trying to close
     */
    public static void quietCloseStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOG.warning("Could not close callable statement: " + e.getMessage());
            }
        }
    }

    /**
     * Close a result set and log any error that might happen
     *
     * @param rs the result set the we trying to close
     */
    public static void quietCloseResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOG.warning("Could not close result set: " + e.getMessage());
            }
        }
    }
}
