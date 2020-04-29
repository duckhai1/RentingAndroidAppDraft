package com.example.book2play.db.models;

import com.example.book2play.db.StaffModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Staff;

import java.util.HashSet;
import java.util.Set;

public class MockStaffModel implements StaffModel {

    private Set<Staff> staffs;

    public MockStaffModel() {
        staffs = new HashSet<>();
    }

    @Override
    public void createStaff(String staffId, String cityId, String sportCenterId) throws MySQLException {
        staffs.add(new Staff(staffId, cityId, sportCenterId));
    }

    @Override
    public void updateStaffId(String newStaffId, String oldStaffId, String cityId, String sportCenterId) throws MySQLException {
        Staff updatedStaff = null;
        for (var s : staffs) {
            if (s.getStaffId().equals(oldStaffId) && s.getCityId().equals(cityId) && s.getSportCenterId().equals(sportCenterId)) {
                updatedStaff = s;
                break;
            }
        }

        if (updatedStaff != null) {
            staffs.remove(updatedStaff);
            staffs.add(new Staff(newStaffId, cityId, sportCenterId));
        }
    }

    public boolean staffExists(String staffId, String cityId, String sportCenterId) {
        for (var s : staffs) {
            if (s.getStaffId().equals(staffId) && s.getCityId().equals(cityId) && s.getSportCenterId().equals(sportCenterId)) {
                return true;
            }
        }

        return false;
    }
}
