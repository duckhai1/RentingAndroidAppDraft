package com.example.book2play.types;

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
}
