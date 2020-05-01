package com.example.book2play.db.models;

import com.example.book2play.db.BookingModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Booking;
import com.example.utils.BookingUtils;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.stream.Collectors;

// TODO: throws exception
public class MockBookingModel extends MockModel implements BookingModel {

    private MockModelDataSource ds;

    public MockBookingModel(MockModelDataSource ds) {
        this.ds = ds;
    }


    @Override
    public Collection<Booking> getCourtBookings(String courtId, String cityId, String sportCenterId, Date date) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }
        var cityModel = new MockCityModel(ds);
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        var sportCenterModel = new MockSportCenterModel(ds);
        if (!sportCenterModel.isSportCenterExist(sportCenterId, cityId)) {
            throw new MySQLException(461);
        }

        var courtModel = new MockCourtModel(ds);
        if (!courtModel.isCourtExist(courtId, cityId, sportCenterId)) {
            throw new MySQLException(462);
        }

        return this.ds.getBookings().stream()
                .filter(b -> b.getCourtId().equals(courtId)
                        && b.getCityId().equals(cityId)
                        && b.getSportCenterId().equals(sportCenterId)
                        && b.getBookingDate().equals(date))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Booking> getSportCenterBookings(String sportCenterId, String cityId, Date date) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }
        var cityModel = new MockCityModel(ds);
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        var sportCenterModel = new MockSportCenterModel(ds);
        if (!sportCenterModel.isSportCenterExist(sportCenterId, cityId)) {
            throw new MySQLException(461);
        }

        return this.ds.getBookings().stream()
                .filter(b -> b.getCityId().equals(cityId)
                        && b.getSportCenterId().equals(sportCenterId)
                        && b.getBookingDate().equals(date))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Booking> getPlayerBookings(String playerId) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }
        var playerModel = new MockPlayerModel(ds);
        if (!playerModel.isPlayerExist(playerId)) {
            throw new MySQLException(464);
        }

        return this.ds.getBookings().stream()
                .filter(b -> b.getPlayerId().equals(playerId))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Booking> getPlayerBookingsInCity(String playerId, String cityId, Date date) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }
        var playerModel = new MockPlayerModel(ds);
        if (!playerModel.isPlayerExist(playerId)) {
            throw new MySQLException(464);
        }

        var cityModel = new MockCityModel(ds);
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        return this.ds.getBookings().stream()
                .filter(b -> b.getPlayerId().equals(playerId)
                        && b.getCityId().equals(cityId)
                        && b.getBookingDate().equals(date))
                .collect(Collectors.toSet());
    }

    @Override
    public void createBooking(Timestamp timestamp, Date date, Time startTime, Time endTime, String cityId, String sportCenterId, String courtId, String playerId) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }

        var cityModel = new MockCityModel(ds);
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        var sportCenterModel = new MockSportCenterModel(ds);
        if (!sportCenterModel.isSportCenterExist(sportCenterId, cityId)) {
            throw new MySQLException(461);
        }

        var courtModel = new MockCourtModel(ds);
        if (!courtModel.isCourtExist(courtId, cityId, sportCenterId)) {
            throw new MySQLException(462);
        }

        var playerModel = new MockPlayerModel(ds);
        if (!playerModel.isPlayerExist(playerId)) {
            throw new MySQLException(464);
        }

        if (date.before(new java.util.Date())) {
            throw new MySQLException(466);
        }

        if (endTime.getTime() - startTime.getTime() >= 45 * 60 * 1000) {
            throw new MySQLException(467);
        }

        if (startTime.before(Time.valueOf("07:00:00"))) {
            throw new MySQLException(468);
        }

        if (endTime.after(Time.valueOf("21:00:00"))) {
            throw new MySQLException(469);
        }

        this.ds.getBookings().add(BookingUtils.createBooking(
                timestamp, date, startTime, endTime, false, cityId, sportCenterId, courtId, playerId
        ));
    }

    @Override
    public void updateBookingStatus(Boolean status, String bookingId, String cityId, String sportCenterId, String staffId) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }

        var cityModel = new MockCityModel(ds);
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        var sportCenterModel = new MockSportCenterModel(ds);
        if (!sportCenterModel.isSportCenterExist(sportCenterId, cityId)) {
            throw new MySQLException(461);
        }

        var staffModel = new MockStaffModel(ds);
        if (!staffModel.isStaffExist(staffId, cityId, sportCenterId)) {
            throw new MySQLException(463);
        }

        Booking updatedBooking = null;
        for (var b : ds.getBookings()) {
            if (b.getBookingId().equals(bookingId) && b.getCityId().equals(cityId) && b.getSportCenterId().equals(sportCenterId)) {
                updatedBooking = b;
                break;
            }
        }

        if (updatedBooking != null) {
            ds.getBookings().remove(updatedBooking);
            ds.getBookings().add(BookingUtils.createBooking(
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
        } else {
            throw new MySQLException(465);
        }
    }

    @Override
    public void cancelBooking(String bookingId, String playerId) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }

        var playerModel = new MockPlayerModel(ds);
        if (!playerModel.isPlayerExist(playerId)) {
            throw new MySQLException(464);
        }

        Booking cancelledBooking = null;
        for (var b : ds.getBookings()) {
            if (b.getBookingId().equals(bookingId) && b.getPlayerId().equals(playerId)) {
                cancelledBooking = b;
                break;
            }
        }

        if (cancelledBooking != null) {
            ds.getBookings().remove(cancelledBooking);
        } else {
            throw new MySQLException(465);
        }
    }

    public void clearBookings() {
        ds.getBookings().clear();
    }
}
