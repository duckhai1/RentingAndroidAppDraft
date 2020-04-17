package com.example.book2play.presentation;


import com.example.book2play.types.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MockDatabase {
    private static MockDatabase instance = new MockDatabase();
    public static MockDatabase getInstance() {
        return instance;
    }
    private Map<String, Booking> bookingList = new HashMap<String, Booking>();
    private final static Timestamp timestamp = Timestamp.valueOf("2007-09-23 10:10:10.0");
    private final static Date bookingDate = new Date((Calendar.getInstance().getTime()).getTime());;
    private final static Court court = new Court("Court1", new SportCenter("Q1", new City("HCM")));
    private final static Player player = new Player("Alice");

    private Booking createMockBooking(String bookingId) {
        return new Booking(bookingId, timestamp, bookingDate,
                Time.valueOf("07:00:00"), Time.valueOf("08:00:00"),
                false, court, player
        );
    }
    private MockDatabase(){
        bookingList.put("booking1", createMockBooking("booking1"));
        bookingList.put("booking2", createMockBooking("booking2"));
        bookingList.put("booking3", createMockBooking("booking3"));
    }

    public Booking getBooking(String name) {
        if (bookingList.containsKey(name)){
            return bookingList.get(name);
        } else {
            return null;
        }

    }

    public void putBooking(Booking booking) {
        bookingList.put(booking.getBookingId(), booking);
    }

}

