package com.example.book2play.types;

import java.util.Objects;

public class SportCenter {
    private String sportCenterId;
    private City city;

    public SportCenter(String sportCenterId, City city) {
        this.sportCenterId = sportCenterId;
        this.city = city;
    }

    public String getSportCenterId() {
        return sportCenterId;
    }

    public City getCity() {
        return city;
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, sportCenterId);
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
        return city.equals(other.getCity())
                && sportCenterId.equals(other.getSportCenterId());
    }
}
