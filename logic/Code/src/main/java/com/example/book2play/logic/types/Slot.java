package com.example.book2play.logic.types;

import java.sql.Time;

public class Slot {
    private String courtId;
    private String sportCenterId;
    private String cityId;
    private Time startTime;
    private Time endTime;

    public Slot(String courtId, String sportCenterId, String cityId, Time startTime, Time endTime) {
        this.courtId = courtId;
        this.sportCenterId = sportCenterId;
        this.cityId = cityId;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }
}
