package com.example.book2play.db.models;

import com.example.book2play.db.MySQLModel;
import com.example.book2play.db.MySQLServer;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.types.Staff;
import com.example.book2play.db.utils.DBUtils;
import com.example.book2play.db.utils.StaffProcedures;

import java.sql.*;

public class StaffModel extends MySQLModel implements StaffProcedures {

    public StaffModel(MySQLServer db) {
        super(db);
    }

    @Override
    public Staff getStaffInfo(String staffId, String cityId, String sportCenterId) throws MySQLException {
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{call getStaffInfo(?,?,?,?)}");
            stm.setString(1, staffId);
            stm.setString(2, cityId);
            stm.setString(3, sportCenterId);
            stm.registerOutParameter(4, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(4);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }

            return new Staff(
                    rs.getString("staffId"),
                    rs.getString("sportCenterId")
            );
        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
            DBUtils.quietCloseResultSet(rs);
        }
    }

    @Override
    public void createStaff(String staffId, String cityId, String sportCenterId) throws MySQLException {
        Connection conn = null;
        CallableStatement stm = null;
        try {
            conn = this.db.getConnection();

            stm = conn.prepareCall("{call createStaff(?,?,?,?)}");
            stm.setString(1, staffId);
            stm.setString(2, cityId);
            stm.setString(3, sportCenterId);
            stm.registerOutParameter(4, Types.INTEGER);

            stm.executeUpdate();
            var statusCode = stm.getInt(4);

            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
        }
    }

    @Override
    public void updateStaffId(String newStaffId, String oldStaffId, String cityId, String sportCenterId) throws MySQLException {
        Connection conn = null;
        CallableStatement stm = null;
        try {
            conn = this.db.getConnection();

            stm = conn.prepareCall("{call supdateStaffId(?,?,?,?,?)}");
            stm.setString(1, newStaffId);
            stm.setString(2, oldStaffId);
            stm.setString(3, cityId);
            stm.setString(4, sportCenterId);
            stm.registerOutParameter(5, Types.INTEGER);

            stm.executeUpdate();
            var statusCode = stm.getInt(5);

            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MySQLException("Unexpected Exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
        }
    }

    @Override
    public void clearStaff() throws MySQLException {
        Connection conn = null;
        Statement stm = null;
        try {
            conn = this.db.getConnection();
            stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM sportCenters");
        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
        }
    }
}
