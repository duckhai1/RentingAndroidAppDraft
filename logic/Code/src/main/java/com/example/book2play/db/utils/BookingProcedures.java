package com.example.book2play.db.utils;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.types.Booking;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;

public interface BookingProcedures {

    Booking getBookingInfo(String bookingId) throws MySQLException, SQLException;

    Collection<Booking> getBookingsInCourt(String courtId, String cityId, String sportCenterId, Date date) throws MySQLException;

    Collection<Booking> getSportCenterBookings(String sportCenterId, Date date) throws MySQLException;

    Collection<Booking> getPlayerBookings(String playerId, String cityId, Date date) throws MySQLException;

    void createBooking(
            String bookingId,
            Timestamp timestamp,
            Date date,
            Time startTime,
            Time endTime,
            String cityId,
            String sportCenterId,
            String courtId,
            String playerId
    ) throws MySQLException;

    void updateBookingStatus(Boolean status, String bookingId, String playerId, String staffId) throws MySQLException;

    void cancelBooking(String bookingId, String playerId) throws MySQLException;

    void clearBooking() throws MySQLException;
}
