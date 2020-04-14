package com.example.book2play.types;

public class Staff {
    private String staffId;
    private String sportCenterId;

    public Staff(String staffId, String sportCenterId) {
        this.staffId = staffId;
        this.sportCenterId = sportCenterId;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getSportCenterId() {
        return sportCenterId;
    }
}
