package com.example.book2play.logic.models;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.utils.BookingModel;
import com.example.book2play.types.Booking;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MockBooking implements BookingModel {

    private Map<String, Booking> bookings;

    public MockBooking() {
        this.bookings = new HashMap<>();
    }

    @Override
    public Booking getBookingInfo(String bookingId) throws MySQLException {
        return null;
    }

    @Override
    public Collection<Booking> getBookingsInCourt(String courtId, String cityId, String sportCenterId, Date date) throws MySQLException {
        var matchedBookings = new ArrayList<Booking>();
        return matchedBookings;
    }

    @Override
    public Collection<Booking> getSportCenterBookings(String sportCenterId, Date date) throws MySQLException {
        return null;
    }

    @Override
    public Collection<Booking> getPlayerBookings(String playerId, String cityId, Date date) throws MySQLException {
        return null;
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
        if (bookings.containsKey(bookingId)) {
            throw new MySQLException();
        }
        bookings.put(bookingId, new Booking(
                bookingId,
                timestamp,
                date,
                startTime,
                endTime,
                false,
                cityId,
                sportCenterId,
                courtId,
                playerId)
        );
    }

    @Override
    public void updateBookingStatus(Boolean status, String bookingId, String playerId, String staffId) throws MySQLException {

    }

    @Override
    public void cancelBooking(String bookingId, String playerId) throws MySQLException {

    }

    @Override
    public void clearBooking() throws MySQLException {

    }
}
