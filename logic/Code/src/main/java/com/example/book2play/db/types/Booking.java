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
    private String courtId;
    private String sportCenterId;
    private String cityId;

    public Booking(String bookingId, Date bookingDate, Time bookingStartTime, Time bookingEndTime, Timestamp createAt, boolean isPaid) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.bookingStartTime = bookingStartTime;
        this.bookingEndTime = bookingEndTime;
        this.createAt = createAt;
        this.isPaid = isPaid;
    }

    public Booking(String bookingId, Time bookingStartTime, Time bookingEndTime, String courtId, String sportCenterId, String cityId) {
        this.bookingId = bookingId;
        this.bookingStartTime = bookingStartTime;
        this.bookingEndTime = bookingEndTime;
        this.courtId = courtId;
        this.sportCenterId = sportCenterId;
        this.cityId = cityId;
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

    public String getCourtId() {
        return courtId;
    }

    public String getSportCenterId() {
        return sportCenterId;
    }
}


