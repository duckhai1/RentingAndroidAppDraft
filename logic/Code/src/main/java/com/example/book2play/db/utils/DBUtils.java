package com.example.book2play.db.utils;

import com.example.book2play.db.types.Booking;
import com.example.book2play.db.types.City;
import com.example.book2play.db.types.Court;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

public class DBUtils {
    private final static Logger LOG = Logger.getLogger("UTILS");

    public static Collection<Booking> bookingsFromResultSet(ResultSet rs) throws SQLException {
        var bookings = new LinkedList<Booking>();
        while (rs.next()) {
            bookings.add(new Booking(
                    rs.getString("bookingId"),
                    rs.getDate("bookingDate"),
                    rs.getTime("bookingStartTime"),
                    rs.getTime("bookingEndTime"),
                    rs.getTimestamp("createdAt"),
                    rs.getBoolean("isPaid")
            ));
        }
        return bookings;
    }

    public static Collection<City> citiesFromResultSet(ResultSet rs) throws SQLException {
        var cities = new LinkedList<City>();
        while (rs.next()) {
            cities.add(new City(
                    rs.getString("cityId")
            ));
        }
        return cities;
    }

    public static Collection<Court> courtsFromResultSet(ResultSet rs) throws SQLException {
        var courts = new LinkedList<Court>();
        while (rs.next()) {
            courts.add(new Court(
                    rs.getString("courtId"),
                    rs.getString("cityId"),
                    rs.getString("sportcenterId")
            ));
        }
        return courts;
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
