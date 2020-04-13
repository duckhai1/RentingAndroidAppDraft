package com.example.book2play.db.models;

import com.example.book2play.db.MySQLModel;
import com.example.book2play.db.MySQLServer;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.types.Booking;
import com.example.book2play.db.types.Court;
import com.example.book2play.db.types.Slot;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class SlotModel extends MySQLModel {

    public SlotModel(MySQLServer db) {
        super(db);
    }

    public ArrayList<ArrayList<Slot>> getAvailableSot(String cityId, Date date) throws MySQLException {
        ArrayList<ArrayList<Slot>> slots = new ArrayList<ArrayList<Slot>>();
        CourtModel courtModel = new CourtModel(db);
        ArrayList<Court> courts = (ArrayList<Court>) courtModel.getCourtsInCity(cityId);
        BookingModel bookingModel = new BookingModel(db);

        Time openTime = Time.valueOf("07:00:00");
        Time closeTime = Time.valueOf("21:00:00");
        long range = 45 * 60;

        for (int i = 0; i < courts.size(); i++) {
            Time availableTime = openTime;
            String sportCenterId = courts.get(i).getSportCenterId();
            String courtId = courts.get(i).getCourtId();
            ArrayList<Booking> bookings = (ArrayList<Booking>) bookingModel.getBookingsInCourt(cityId, sportCenterId, courtId, date);

            if (bookings.size() == 0) {
                slots.get(i).add(new Slot(courtId, cityId, sportCenterId, openTime, closeTime));
            } else {
                for (Booking booking : bookings) {
                    Time bookingStartTime = booking.getBookingStartTime();
                    Time bookingEndTime = booking.getBookingEndTime();

                    if (bookingStartTime.getTime() - availableTime.getTime() >= range) {
                        slots.get(i).add(new Slot(courtId, cityId, sportCenterId, availableTime, bookingStartTime));
                        availableTime = bookingEndTime;
                    }
                    if (closeTime.getTime() - availableTime.getTime() >= range) {
                        slots.get(i).add(new Slot(courtId, cityId, sportCenterId, availableTime, closeTime));
                    }
                }
            }

        }
        return slots;
    }
}
