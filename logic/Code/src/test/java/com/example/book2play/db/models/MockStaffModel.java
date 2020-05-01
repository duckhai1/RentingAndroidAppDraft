package com.example.book2play.db.models;

import com.example.book2play.db.StaffModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Staff;

public class MockStaffModel extends MockModel implements StaffModel {

    private MockModelDataSource ds;

    public MockStaffModel(MockModelDataSource ds) {
        this.ds = ds;
    }

    @Override
    public void createStaff(String staffId, String cityId, String sportCenterId) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }
        var cityModel = new MockCityModel(ds);
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        var sportCenterModel = new MockSportCenterModel(ds);
        ;
        if (!sportCenterModel.isSportCenterExist(sportCenterId, cityId)) {
            throw new MySQLException(461);
        }

        var newStaff = new Staff(staffId, cityId, sportCenterId);
        if (ds.getStaffs().contains(newStaff)) {
            throw new MySQLException(406);
        }

        ds.getStaffs().add(newStaff);
    }

    @Override
    public void updateStaffId(String newStaffId, String oldStaffId, String cityId, String sportCenterId) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }
        var cityModel = new MockCityModel(ds);
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        var sportCenterModel = new MockSportCenterModel(ds);
        ;
        if (!sportCenterModel.isSportCenterExist(sportCenterId, cityId)) {
            throw new MySQLException(461);
        }

        var oldStaff = new Staff(oldStaffId, cityId, sportCenterId);
        if (!ds.getStaffs().contains(oldStaff)) {
            throw new MySQLException(463);
        }

        ds.getStaffs().remove(oldStaff);
        ds.getStaffs().add(new Staff(newStaffId, cityId, sportCenterId));
    }

    @Override
    public boolean isStaffExist(String staffId, String cityId, String sportCenterId) {
        return ds.getStaffs().contains(new Staff(staffId, cityId, sportCenterId));
    }

    public void clearStaff() {
        ds.getStaffs().clear();
    }
}
