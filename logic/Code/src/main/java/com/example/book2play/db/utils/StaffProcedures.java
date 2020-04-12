package com.example.book2play.db.utils;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.types.Staff;

public interface StaffProcedures {

    Staff getStaffInfo(String staffId, String cityId, String sportCenterId) throws MySQLException;

    void createStaff(String staffId, String cityId, String sportCenterId) throws MySQLException;

    void updateStaffId(String newStaffId, String oldStaffId, String cityId, String sportCenterId) throws MySQLException;

    void clearStaff() throws MySQLException;
}
