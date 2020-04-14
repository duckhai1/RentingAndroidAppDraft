package com.example.book2play.types;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class Booking {
    private String bookingId;
    private Timestamp createdAt;
    private Date bookingDate;
    private Time bookingStartTime;
    private Time bookingEndTime;
    private boolean isPaid;
    private Court court;
    private Player player;

    public Booking(String bookingId,
                   Timestamp createdAt,
                   Date bookingDate,
                   Time bookingStartTime,
                   Time bookingEndTime,
                   boolean isPaid,
                   Court court,
                   Player player
    ) {
        this.bookingId = bookingId;
        this.createdAt = createdAt;
        this.bookingDate = bookingDate;
        this.bookingStartTime = bookingStartTime;
        this.bookingEndTime = bookingEndTime;
        this.isPaid = isPaid;
        this.court = court;
        this.player = player;
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

    public Court getCourt() {
        return court;
    }

    public Player getPlayer() {
        return player;
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


