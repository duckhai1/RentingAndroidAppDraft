package com.example.book2play.db.types;

public class Court {
    private int courtPk;
    private String courtId;
    private int sportcenterPk;

    public Court(int courtPk, String courtId, int sportcenterPk) {
        this.courtPk = courtPk;
        this.courtId = courtId;
        this.sportcenterPk = sportcenterPk;
    }

    public int getCourtPk() {
        return courtPk;
    }

    public String getCourtId() {
        return courtId;
    }

    public int getSportcenterPk() {
        return sportcenterPk;
    }
}
