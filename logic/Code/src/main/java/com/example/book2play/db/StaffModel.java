package com.example.book2play.db;

import com.example.book2play.db.exceptions.MySQLException;

public interface StaffModel {

    /**
     * Create a staff the works for the given sport center
     *
     * @param staffId       the unique identifier, in the given sport center, of the new staff
     * @param cityId        the unique identifier of the city the the sport center locates in
     * @param sportCenterId the unique identifier, in the city, of the sport center
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    void createStaff(String staffId, String cityId, String sportCenterId) throws MySQLException;

    /**
     * Change the unique identifier of the staff to a new one
     *
     * @param newStaffId    the new unique identifier, in the sport center
     * @param oldStaffId    the current unique identifier of the staff
     * @param cityId        the unique identifier of the city that the sport center locates in
     * @param sportCenterId the unique identifier, in the city, of the sport center
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    void updateStaffId(String newStaffId, String oldStaffId, String cityId, String sportCenterId) throws MySQLException;

}
