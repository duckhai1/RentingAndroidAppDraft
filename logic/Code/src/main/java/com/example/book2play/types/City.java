package com.example.book2play.types;

import java.util.Objects;

public class City {
    private String cityId;

    public City(String cityId) {
        this.cityId = cityId;
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


