package com.example.book2play.types;

import java.util.Objects;

public class Court {
    private String courtId;
    private String cityId;
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
        return Objects.hash(this.cityId, this.sportCenterId, this, cityId);
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
        return this.cityId.equals(other.getCityId())
                && this.sportCenterId.equals(other.getSportCenterId())
                && this.courtId.equals(other.getCourtId());
    }
}
