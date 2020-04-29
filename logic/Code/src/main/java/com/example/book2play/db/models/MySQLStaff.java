package com.example.book2play.db.models;

import com.example.book2play.db.AppDataSource;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.utils.ResultSetUtils;

import java.sql.*;

/**
 * Implements StaffModel interfaces for working with the stored procedures from MySQL
 * The connection is establish using MySQL DataSource object
 */
public class MySQLStaff extends AbstractModel implements com.example.book2play.db.StaffModel {

    public MySQLStaff(AppDataSource db) {
        super(db);
    }

    /**
     * Create a new connection to the data source and call the stored procedure
     * to create a new staff for a given sport center
     *
     * @param staffId       the unique identifier, in the given sport center, of the new staff
     * @param cityId        the unique identifier of the city the the sport center locates in
     * @param sportCenterId the unique identifier, in the city, of the sport center
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    @Override
    public void createStaff(String staffId, String cityId, String sportCenterId) throws MySQLException {
        LOG.info("Calling createStaff");
        Connection conn = null;
        CallableStatement stm = null;
        try {
            conn = this.db.getConnection();

            stm = conn.prepareCall("{call createStaff(?,?,?,?)}");
            stm.setString(1, staffId);
            stm.setString(2, cityId);
            stm.setString(3, sportCenterId);
            stm.registerOutParameter(4, Types.INTEGER);

            var updateCount = stm.executeUpdate();
            var statusCode = stm.getInt(4);
            LOG.info("Received status code " + statusCode);
            LOG.info("Update count " + updateCount);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
        }
    }

    /**
     * Create a new connection to the data source and call the stored procedure
     * to update the staff unique identifier
     *
     * @param newStaffId    the new unique identifier, in the sport center
     * @param oldStaffId    the current unique identifier of the staff
     * @param cityId        the unique identifier of the city that the sport center locates in
     * @param sportCenterId the unique identifier, in the city, of the sport center
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    @Override
    public void updateStaffId(String newStaffId, String oldStaffId, String cityId, String sportCenterId) throws MySQLException {
        LOG.info("Calling updateStaffId");
        Connection conn = null;
        CallableStatement stm = null;
        try {
            conn = this.db.getConnection();

            stm = conn.prepareCall("{CALL updateStaffId(?, ?, ?, ?, ?)}");
            stm.setString(1, newStaffId);
            stm.setString(2, oldStaffId);
            stm.setString(3, cityId);
            stm.setString(4, sportCenterId);
            stm.registerOutParameter(5, Types.INTEGER);

            var updateCount = stm.executeUpdate();
            var statusCode = stm.getInt(5);
            LOG.info("Received status code " + statusCode);
            LOG.info("Update count " + updateCount);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
        }
    }

}
