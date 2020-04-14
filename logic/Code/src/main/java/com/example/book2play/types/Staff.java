package com.example.book2play.types;

import java.util.Objects;

public class Staff {
    private String staffId;
    private SportCenter sportCenter;

    public Staff(String staffId, SportCenter sportCenter) {
        this.staffId = staffId;
        this.sportCenter = sportCenter;
    }

    public String getStaffId() {
        return staffId;
    }

    public SportCenter getSportCenter() {
        return sportCenter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sportCenter, staffId);
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

        var other = (Staff) obj;
        return sportCenter.equals(other.getSportCenter())
                && staffId.equals(other.getStaffId());
    }
}
