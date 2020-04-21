package com.example.book2play.types;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class Booking {
    private String bookingId;
    private transient Timestamp createdAt;
    private Date bookingDate;
    private Time bookingStartTime;
    private Time bookingEndTime;
    private boolean isPaid;
    private String cityId;
    private String sportCenterId;
    private String courtId;
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

    public String getCityId() {
        return cityId;
    }

    public String getSportCenterId() {
        return sportCenterId;
    }

    public String getCourtId() {
        return courtId;
    }

    public String getPlayerId() {
        return playerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        var other = (Booking) obj;
        return bookingId.equals(other.getBookingId());
    }
}


