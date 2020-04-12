package com.example.book2play.db.types;

public class SportCenter {
    private int sportCenterPk;
    private String sportCenterId;

    public SportCenter(int sportCenterPk, String sportCenterId, int cityPK) {
        this.sportCenterPk = sportCenterPk;
        this.sportCenterId = sportCenterId;
        this.cityPK = cityPK;
    }

    private int cityPK;
}
