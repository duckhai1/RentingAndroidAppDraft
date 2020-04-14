package com.example.book2play.logic.models;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.BookingModel;
import com.example.book2play.db.models.CourtModel;
import com.example.book2play.db.utils.DBManager;
import com.example.book2play.logic.types.Slot;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;

public class SlotModel {

    private final Time OPEN_TIME = Time.valueOf("07:00:00");
    private final Time CLOSE_TIME = Time.valueOf("21:00:00");
    private final long MIN_DURATION = 46 * 60; // seconds (60 minutes)
    private CourtModel courtModel;
    private BookingModel bookingModel;

    public SlotModel(DBManager db) {
        courtModel = new CourtModel(db);
        bookingModel = new BookingModel(db);
    }

    public SlotModel(CourtModel courtModel, BookingModel bookingModel) {
        this.courtModel = courtModel;
        this.bookingModel = bookingModel;
    }

    public Collection<Slot> getAvailableSlots(String cityId, String sportCenterId, String courtId, Date date) throws MySQLException {
        var slots = new ArrayList<Slot>();
        var bookings = bookingModel.getBookingsInCourt(cityId, sportCenterId, courtId, date);

        var prevEndTime = OPEN_TIME;
        for (var booking : bookings) {
            var currStartTime = booking.getBookingStartTime();
            if (currStartTime.getTime() - prevEndTime.getTime() >= MIN_DURATION) {
                slots.add(new Slot(courtId, sportCenterId, cityId, prevEndTime, currStartTime));
            }
            prevEndTime = booking.getBookingEndTime();
        }

        if (CLOSE_TIME.getTime() - prevEndTime.getTime() >= MIN_DURATION) {
            slots.add(new Slot(courtId, sportCenterId, cityId, prevEndTime, CLOSE_TIME));
        }
        return slots;
    }

    public Collection<Slot> getCityAvailableSlots(String cityId, Date date) throws MySQLException {
        var slots = new ArrayList<Slot>();
        var courts = courtModel.getCourtsInCity(cityId);
        for (var court : courts) {
            slots.addAll(getAvailableSlots(cityId, court.getSportCenterId(), court.getCourtId(), date));
        }

        return slots;
    }
}
