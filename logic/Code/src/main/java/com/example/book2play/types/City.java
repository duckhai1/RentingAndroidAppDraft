package com.example.book2play.types;

import com.google.gson.annotations.Expose;

import java.util.Objects;

/**
 * Simple class encapsulates the attributes for a city
 */
public class City {
    @Expose
    private String cityId;
    @Expose(deserialize = false)
    private double Latitude;
    @Expose(deserialize = false)
    private double Longitude;

    public City(String cityId, double Latitude, double Longitude){
        this.cityId = cityId;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }

    public City(String cityId) {
        this(cityId, 0.0, 0.0);
    }

    public String getCityId() {
        return cityId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId);
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

        var other = (City) obj;
        return cityId.equals(other.getCityId());
    }
}


