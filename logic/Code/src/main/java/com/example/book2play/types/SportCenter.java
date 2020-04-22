package com.example.book2play.types;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class SportCenter {
    @Expose
    private String sportCenterId;
    @Expose
    private String cityId;

    public SportCenter(String sportCenterId, String cityId) {
        this.sportCenterId = sportCenterId;
        this.cityId = cityId;
    }

    public String getSportCenterId() {
        return sportCenterId;
    }

    public String getCityId() {
        return cityId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId, sportCenterId);
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

        var other = (SportCenter) obj;
        return cityId.equals(other.getCityId())
                && sportCenterId.equals(other.getSportCenterId());
    }
}
