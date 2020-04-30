package com.example.book2play.types;

import com.google.gson.annotations.Expose;

import java.util.Objects;

/**
 * Simple class encapsulates the attributes for a sport center
 */
public class SportCenter {
    @Expose
    private String sportCenterId;
    @Expose
    private String cityId;
    @Expose(deserialize = false)
    private double Latitude;
    @Expose(deserialize = false)
    private double Longitude;
    @Expose(deserialize = false)
    private String sportCenterAddress;

    public SportCenter(String sportCenterId, String cityId, double Latitude, double Longitude, String sportCenterAddress){
        this.sportCenterId = sportCenterId;
        this.cityId = cityId;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.sportCenterAddress = sportCenterAddress;
    }

    public SportCenter(String sportCenterId, String cityId) {
        this(sportCenterId, cityId, 0.0, 0.0, "Unknown");
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
