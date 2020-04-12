package com.example.book2play.db.types;

public class SportCenter {
    private String sportCenterId;
    private String cityId;

    public SportCenter(String sportCenterId, String cityId) {
        this.sportCenterId = sportCenterId;
        this.cityId = cityId;
    }

    public String getSportCenterId() {
        return sportCenterId;
    }

    public String getCityId() {
        return cityId;
    }
}
