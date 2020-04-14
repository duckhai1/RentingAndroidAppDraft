package com.example.book2play.db.types;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Booking {
    private String bookingId;
    private Timestamp createdAt;
    private Date bookingDate;
    private Time bookingStartTime;
    private Time bookingEndTime;
    private boolean isPaid;
    private String courtId;
    private String sportCenterId;
    private String cityId;
    private String playerId;

    public Booking(String bookingId,
                   Timestamp createdAt,
                   Date bookingDate,
                   Time bookingStartTime,
                   Time bookingEndTime,
                   boolean isPaid,
                   String cityId,
                   String sportCenterId,
                   String courtId,
                   String playerId
    ) {
        this.bookingId = bookingId;
        this.createdAt = createdAt;
        this.bookingDate = bookingDate;
        this.bookingStartTime = bookingStartTime;
        this.bookingEndTime = bookingEndTime;
        this.isPaid = isPaid;
        this.cityId = cityId;
        this.sportCenterId = sportCenterId;
        this.courtId = courtId;
        this.playerId = playerId;
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

    public Timestamp getCreatedAt() {
        return createdAt;
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

    public String getCityId() {
        return cityId;
    }

    public String getPlayerId() {
        return playerId;
    }
}


