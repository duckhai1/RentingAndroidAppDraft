package com.example.book2play.logic;

import com.example.book2play.types.Booking;
import com.example.book2play.types.Court;
import com.example.book2play.types.Slot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Set of methods for generating available slots from a given list of bookings
 */
public class SlotService {

    private final Time openTime;
    private final Time closeTime;
    private final long minDuration; // milliseconds

    public SlotService(String openTime, String closeTime, long minDurationInMinutes) {
        this.openTime = Time.valueOf(openTime);
        this.closeTime = Time.valueOf(closeTime);
        this.minDuration = minDurationInMinutes * 60 * 1000; // minutes to milliseconds
    }

    /**
     * @param courtBookings list of bookings of a given court
     * @param cityId        the city that the court is belonged to
     * @param sportCenterId the sport center that the court is belonged to
     * @param courtId       the identifier of the court
     * @return list of available slots based on the given list of bookings
     */
    public List<Slot> getAvailableSlots(List<Booking> courtBookings, String cityId, String sportCenterId, String courtId) {
        var slots = new ArrayList<com.example.book2play.types.Slot>();
        var prevEndTime = openTime;
        for (var booking : courtBookings) {
            var currStartTime = booking.getBookingStartTime();
            if (currStartTime.getTime() - prevEndTime.getTime() >= minDuration) {
                slots.add(new Slot(
                        prevEndTime,
                        currStartTime,
                        cityId,
                        sportCenterId,
                        courtId
                ));
            }
            prevEndTime = booking.getBookingEndTime();
        }

        if (closeTime.getTime() - prevEndTime.getTime() >= minDuration) {
            slots.add(new Slot(
                    prevEndTime,
                    closeTime,
                    cityId,
                    sportCenterId,
                    courtId
            ));
        }
        return slots;
    }

    public List<Slot> getCityAvailableSlots(Map<Court, List<Booking>> cityBookings) {
        var slots = new ArrayList<com.example.book2play.types.Slot>();
        for (var court : cityBookings.keySet()) {
            slots.addAll(getAvailableSlots(
                    cityBookings.get(court),
                    court.getCityId(),
                    court.getSportCenterId(),
                    court.getCourtId())
            );
        }
        return slots;
    }
}
