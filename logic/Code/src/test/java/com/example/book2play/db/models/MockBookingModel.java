package com.example.book2play.db.models;

import com.example.book2play.db.BookingModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Booking;
import com.example.test_utils.BookingUtils;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

// TODO: throws exception
public class MockBookingModel implements BookingModel {
    private Set<Booking> bookings;

    public MockBookingModel() {
        this.bookings = new HashSet<>();
    }

    @Override
    public Collection<Booking> getCourtBookings(String courtId, String cityId, String sportCenterId, Date date) throws MySQLException {
        return this.bookings.stream()
                .filter(b -> b.getCourtId().equals(courtId)
                        && b.getCityId().equals(cityId)
                        && b.getSportCenterId().equals(sportCenterId)
                        && b.getBookingDate().equals(date))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Booking> getSportCenterBookings(String sportCenterId, String cityId, Date date) throws MySQLException {
        return this.bookings.stream()
                .filter(b -> b.getCityId().equals(cityId)
                        && b.getSportCenterId().equals(sportCenterId)
                        && b.getBookingDate().equals(date))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Booking> getPlayerBookings(String playerId) throws MySQLException {
        return this.bookings.stream()
                .filter(b -> b.getPlayerId().equals(playerId))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Booking> getPlayerBookingsInCity(String playerId, String cityId, Date date) throws MySQLException {
        return this.bookings.stream()
                .filter(b -> b.getPlayerId().equals(playerId)
                        && b.getCityId().equals(cityId)
                        && b.getBookingDate().equals(date))
                .collect(Collectors.toSet());
    }

    @Override
    public void createBooking(Timestamp timestamp, Date date, Time startTime, Time endTime, String cityId, String sportCenterId, String courtId, String playerId) throws MySQLException {
        this.bookings.add(BookingUtils.createBooking(
                timestamp, date, startTime, endTime, false, cityId, sportCenterId, courtId, playerId
        ));
    }

    @Override
    public void updateBookingStatus(Boolean status, String bookingId, String cityId, String sportCenterId, String staffId) throws MySQLException {
        Booking updatedBooking = null;
        for (var b : bookings) {
            if (b.getBookingId().equals(bookingId) && b.getCityId().equals(cityId) && b.getSportCenterId().equals(sportCenterId)) {
                updatedBooking = b;
                break;
            }
        }

        if (updatedBooking != null) {
            bookings.remove(updatedBooking);
            bookings.add(BookingUtils.createBooking(
                    updatedBooking.getCreatedAt(),
                    updatedBooking.getBookingDate(),
                    updatedBooking.getBookingStartTime(),
                    updatedBooking.getBookingEndTime(),
                    updatedBooking.isPaid(),
                    updatedBooking.getCityId(),
                    updatedBooking.getSportCenterId(),
                    updatedBooking.getCourtId(),
                    updatedBooking.getPlayerId()
            ));
        }
    }

    @Override
    public void cancelBooking(String bookingId, String playerId) throws MySQLException {
        Booking cancelledBooking = null;
        for (var b : bookings) {
            if (b.getBookingId().equals(bookingId) && b.getPlayerId().equals(playerId)) {
                cancelledBooking = b;
                break;
            }
        }

        if (cancelledBooking != null) {
            bookings.remove(cancelledBooking);
        }
    }
}
