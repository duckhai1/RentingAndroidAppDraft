package com.example.book2play.types;

import java.util.Objects;

public class Staff {
    private String staffId;
    private String sportCenterId;
    private String cityId;

    public Staff(String staffId, String sportCenterId, String cityId) {
        this.staffId = staffId;
        this.sportCenterId = sportCenterId;
        this.cityId = cityId;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getSportCenterId() {
        return sportCenterId;
    }

    public String getCityId() {
        return cityId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId, sportCenterId, staffId);
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
        return cityId.equals(other.getCityId())
                && sportCenterId.equals(other.getSportCenterId())
                && staffId.equals(other.getStaffId());
    }
}
