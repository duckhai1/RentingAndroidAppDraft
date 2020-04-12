package com.example.book2play.db.types;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Booking {
    private String bookingId;
    private Date bookingDate;
    private Time bookingStartTime;
    private Time bookingEndTime;
    private Timestamp createAt;
    private boolean isPaid;

    public Booking(String bookingId, Date bookingDate, Time bookingStartTime, Time bookingEndTime, Timestamp createAt, boolean isPaid) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.bookingStartTime = bookingStartTime;
        this.bookingEndTime = bookingEndTime;
        this.createAt = createAt;
        this.isPaid = isPaid;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public Time getBookingStartTime() {
        return bookingStartTime;
    }

    public Time getBookingEndTime() {
        return bookingEndTime;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public boolean isPaid() {
        return isPaid;
    }
}


