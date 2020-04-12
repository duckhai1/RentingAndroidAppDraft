package com.example.book2play.db.types;

public class Staff {
    private int staffPk;
    private String staffId;
    private int sportCenterPk;

    public Staff(int staffPk, String staffId, int sportCenterPk) {
        this.staffPk = staffPk;
        this.staffId = staffId;
        this.sportCenterPk = sportCenterPk;
    }
}
