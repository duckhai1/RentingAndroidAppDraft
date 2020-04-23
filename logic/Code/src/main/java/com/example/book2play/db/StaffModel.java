package com.example.book2play.db;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Staff;

public interface StaffModel {

    void createStaff(String staffId, String cityId, String sportCenterId) throws MySQLException;

    void updateStaffId(String newStaffId, String oldStaffId, String cityId, String sportCenterId) throws MySQLException;

    void clearStaff() throws MySQLException;

}
