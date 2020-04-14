package com.example.book2play.types;

import java.util.Objects;

public class Court {
    private String courtId;
    private SportCenter sportCenter;

    public Court(String courtId, SportCenter sportCenter) {
        this.courtId = courtId;
        this.sportCenter = sportCenter;
    }

    public String getCourtId() {
        return courtId;
    }

    public SportCenter getSportCenter() {
        return sportCenter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courtId, sportCenter);
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
        return sportCenter.equals(other.getSportCenter())
                && this.courtId.equals(other.getCourtId());
    }
}
