package com.example.book2play.db.utils;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.types.Staff;

public interface StaffProcedures {

    Staff getStaffInfo(String staffID, String sportCenterId, String cityId) throws MySQLException;

    void createStaff(String staffId, String sportCenterId, String cityId) throws MySQLException;

    void updateStaffId(String newStaffId, String oldStaffId, String sportCenterId, String cityId) throws MySQLException;

    void clearStaff() throws MySQLException;
}
