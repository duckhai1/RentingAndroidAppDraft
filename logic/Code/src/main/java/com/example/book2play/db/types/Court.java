package com.example.book2play.db.types;

public class Court {
    private String courtId;
    private String cityId;
    private String sportcenterId;

    public Court( String courtId, String cityId, String sportcenterId) {
        this.courtId = courtId;
        this.cityId = cityId;
        this.sportcenterId = sportcenterId;
    }

    public String getCourtId() {
        return courtId;
    }

    public String getCityId() {
        return cityId;
    }

    public String getSportcenterId() {
        return sportcenterId;
    }
}
