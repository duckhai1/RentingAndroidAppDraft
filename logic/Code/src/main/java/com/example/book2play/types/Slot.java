package com.example.book2play.types;

import java.sql.Time;
import java.util.Objects;

public class Slot {
    private Time startTime;
    private Time endTime;
    private String cityId;
    private String sportCenterId;
    private String courtId;

    public Slot(Time startTime, Time endTime, String cityId, String sportCenterId, String courtId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.cityId = cityId;
        this.sportCenterId = sportCenterId;
        this.courtId = courtId;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
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

    @Override
    public String toString() {
        return String.format("Slot(%s, %s, %s, %s, %sk)", startTime, endTime, cityId, sportCenterId, courtId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, cityId, sportCenterId, courtId);
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

        var other = (Slot) obj;
        return startTime.equals(other.getStartTime())
                && endTime.equals(other.getEndTime())
                && cityId.equals(other.getCityId())
                && sportCenterId.equals(other.getSportCenterId())
                && courtId.equals(other.getCourtId());
    }
}
