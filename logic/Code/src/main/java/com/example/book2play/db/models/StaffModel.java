package com.example.book2play.db.models;

import com.example.book2play.db.AppDataSource;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.utils.ResultSetUtils;

import java.sql.*;

/**
 * Implements StaffModel interfaces for working with the stored procedures from MySQL
 * The connection is establish using MySQL DataSource object
 */
public class StaffModel extends AbstractModel implements com.example.book2play.db.StaffModel {

    public StaffModel(AppDataSource db) {
        super(db);
    }

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

    @Override
    public void clearStaff() throws MySQLException {
        LOG.info("Calling clearStaff");
        Connection conn = null;
        Statement stm = null;
        try {
            conn = this.db.getConnection();
            stm = conn.createStatement();

            var updateCount = stm.executeUpdate("DELETE FROM sportCenters");
            LOG.info("Update count " + updateCount);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
        }
    }
}
