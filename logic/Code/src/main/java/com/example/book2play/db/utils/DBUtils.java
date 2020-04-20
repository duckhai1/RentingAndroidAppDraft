package com.example.book2play.db.utils;

import com.example.book2play.types.*;
import com.mysql.cj.protocol.Resultset;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

public class DBUtils {
    private final static Logger LOG = Logger.getLogger("UTILS");

    public static Booking singleBookingFromResultSet(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getString("bookingId"),
                rs.getTimestamp("createdAt"),
                rs.getDate("bookingDate"),
                rs.getTime("bookingStartTime"),
                rs.getTime("bookingEndTime"),
                rs.getBoolean("isPaid"),
                new Court(rs.getString("courtId"),
                        new SportCenter(rs.getString("sportCenterId"),
                                new City(rs.getString("cityId")))),
                new Player(rs.getString("playerId"))
        );
    }

    public static Collection<Booking> bookingsFromResultSet(ResultSet rs) throws SQLException {
        var bookings = new LinkedList<Booking>();
        while (rs.next()) {
            bookings.add(singleBookingFromResultSet(rs));
        }
        return bookings;
    }

    public static City singleCityFromResultSet(ResultSet rs) throws SQLException {
        return new City(rs.getString("cityId"));
    }

    public static Collection<City> citiesFromResultSet(ResultSet rs) throws SQLException {
        var cities = new LinkedList<City>();
        while (rs.next()) {
            cities.add(singleCityFromResultSet(rs));
        }
        return cities;
    }

    public static Court singleCourtFromResultSet(ResultSet rs) throws SQLException {
        return new Court(
                rs.getString("courtId"),
                new SportCenter(rs.getString("sportCenterId"),
                        new City(rs.getString("cityId")))
        );
    }

    public static Collection<Court> courtsFromResultSet(ResultSet rs) throws SQLException {
        var courts = new LinkedList<Court>();
        while (rs.next()) {
            courts.add(singleCourtFromResultSet(rs));
        }
        return courts;
    }

    public static Staff singleStaffFromResultSet(ResultSet rs) throws SQLException {
        return new Staff(
                rs.getString("staffId"),
                new SportCenter(rs.getString("sportCenterId"),
                        new City(rs.getString("cityId")))
        );
    }

    public static SportCenter singleSportCenterFromResultSet(ResultSet rs) throws SQLException {
        return new SportCenter(
                rs.getString("sportCenterId"),
                new City(rs.getString("cityId"))
        );
    }

    public static Collection<SportCenter> sportCentersFromResultSet(ResultSet rs) throws SQLException {
        var sportCenters = new LinkedList<SportCenter>();
        while (rs.next()) {
            sportCenters.add(singleSportCenterFromResultSet(rs));
        }
        return sportCenters;
    }

    public static void quietCloseConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOG.warning("Could not close connection: " + e.getMessage());
            }
        }
    }

    public static void quietCloseStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOG.warning("Could not close callable statement: " + e.getMessage());
            }
        }
    }

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
