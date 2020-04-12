package com.example.book2play.db.types;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Booking {
    private int bookingPK;
    private String bookingId;
    private Date bookingDate;
    private Time bookingStartTime;
    private Time bookingEndTime;
    private Timestamp createAt;
    private boolean isPaid;
    private int playerPk;
    private int courtPK;

    public Booking(int bookingPK, String bookingId, Date bookingDate, Time bookingStartTime, Time bookingEndTime, Timestamp createAt, boolean isPaid, int playerPk, int courtPK) {
        this.bookingPK = bookingPK;
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.bookingStartTime = bookingStartTime;
        this.bookingEndTime = bookingEndTime;
        this.createAt = createAt;
        this.isPaid = isPaid;
        this.playerPk = playerPk;
        this.courtPK = courtPK;
    }
}


