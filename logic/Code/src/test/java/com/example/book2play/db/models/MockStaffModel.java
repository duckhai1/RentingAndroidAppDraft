package com.example.book2play.db.models;

import com.example.book2play.db.StaffModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Staff;

import java.util.HashSet;
import java.util.Set;

public class MockStaffModel implements StaffModel {

    private Set<Staff> staffs;

    private static MockStaffModel SINGLETON = null;

    private MockStaffModel() {
        staffs = new HashSet<>();
    }

    public static MockStaffModel getInstance() {
        if (SINGLETON == null) {
            SINGLETON = new MockStaffModel();
        }

        return SINGLETON;
    }


    @Override
    public void createStaff(String staffId, String cityId, String sportCenterId) throws MySQLException {
        var cityModel = MockCityModel.getInstance();
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        var sportCenterModel = MockSportCenterModel.getInstance();
        if (!sportCenterModel.isSportCenterExist(sportCenterId, cityId)) {
            throw new MySQLException(461);
        }

        var newStaff = new Staff(staffId, cityId, sportCenterId);
        if (staffs.contains(newStaff)) {
            throw new MySQLException(406);
        }

        staffs.add(newStaff);
    }

    @Override
    public void updateStaffId(String newStaffId, String oldStaffId, String cityId, String sportCenterId) throws MySQLException {
        var cityModel = MockCityModel.getInstance();
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        var sportCenterModel = MockSportCenterModel.getInstance();
        if (!sportCenterModel.isSportCenterExist(sportCenterId, cityId)) {
            throw new MySQLException(461);
        }

        var oldStaff = new Staff(oldStaffId, cityId, sportCenterId);
        if (!staffs.contains(oldStaff)) {
            throw new MySQLException(463);
        }

        staffs.remove(oldStaff);
        staffs.add(new Staff(newStaffId, cityId, sportCenterId));
    }

    public boolean isStaffExist(String staffId, String cityId, String sportCenterId) {
        return staffs.contains(new Staff(staffId, cityId, sportCenterId));
    }

    public void clearStaff() {
        staffs.clear();
    }
}
