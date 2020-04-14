package com.example.book2play.logic.models;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Booking;
import com.example.book2play.types.Court;
import com.example.book2play.types.Slot;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SlotModel {

    private final Time OPEN_TIME = Time.valueOf("07:00:00");
    private final Time CLOSE_TIME = Time.valueOf("21:00:00");
    private final long MIN_DURATION = 46 * 60; // seconds (60 minutes)

    public List<Slot> getAvailableSlots(List<Booking> courtBookings, String cityId, String sportCenterId, String courtId) {
        var slots = new ArrayList<Slot>();
        var prevEndTime = OPEN_TIME;
        for (var booking : courtBookings) {
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

    public List<Slot> getCityAvailableSlots(Map<Court, List<Booking>> cityBookings, String cityId, Date date) throws MySQLException {
        var slots = new ArrayList<Slot>();
        // for (var court : courts) {
        //     slots.addAll(getAvailableSlots(cityId, court.getSportCenterId(), court.getCourtId(), date));
        // }
        return slots;
    }
}
