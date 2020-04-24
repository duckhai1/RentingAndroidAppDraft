package com.example.book2play.types;

import com.google.gson.annotations.Expose;

import java.util.Objects;

/**
 * Simple class encapsulates the attributes for a court
 */
public class Court {
    @Expose
    private String courtId;
    @Expose
    private String cityId;
    @Expose
    private String sportCenterId;

    public Court(String courtId, String cityId, String sportCenterId) {
        this.courtId = courtId;
        this.cityId = cityId;
        this.sportCenterId = sportCenterId;
    }

    public String getCourtId() {
        return courtId;
    }

    public String getCityId() {
        return cityId;
    }

    public String getSportCenterId() {
        return sportCenterId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId, sportCenterId, courtId);
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

        var other = (Court) obj;
        return cityId.equals(other.getCityId())
                && sportCenterId.equals(other.getSportCenterId())
                && courtId.equals(other.getCourtId());
    }
}
