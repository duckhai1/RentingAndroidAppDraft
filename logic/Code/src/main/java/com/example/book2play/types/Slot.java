package com.example.book2play.types;

import java.sql.Time;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "Slot(" +
                courtId + ", " + sportCenterId + ", " + cityId + ", " + startTime + ", " + endTime +
                ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(courtId, sportCenterId, cityId, startTime, endTime);
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
        return cityId.equals(other.getCityId())
                && sportCenterId.equals(other.getSportCenterId())
                && courtId.equals(other.getCourtId())
                && startTime.equals(other.getStartTime())
                && endTime.equals(other.getEndTime());
    }
}
