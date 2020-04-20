package com.example.book2play.types;

import java.util.Objects;

public class Staff {
    private String staffId;
    private String cityId;
    private String sportCenterId;

    public Staff(String staffId, String cityId, String sportCenterId) {
        this.staffId = staffId;
        this.cityId = cityId;
        this.sportCenterId = sportCenterId;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getCityId() {
        return cityId;
    }

    public String getSportCenterId() {
        return sportCenterId;
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
