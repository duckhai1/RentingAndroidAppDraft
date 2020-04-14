package com.example.book2play.db.models;

import com.example.book2play.db.AppDataSource;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.utils.DBUtils;
import com.example.book2play.types.City;
import com.example.book2play.types.SportCenter;

import java.sql.*;

public class SportCenterModel extends MySQLModel implements com.example.book2play.db.SportCenterModel {

    public SportCenterModel(AppDataSource db) {
        super(db);
    }

    @Override
    public SportCenter getSportCenterInfo(String sportCenterId, String cityId) throws MySQLException {
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{call getSportCenterInfo(?,?,?)");
            stm.setString(1, sportCenterId);
            stm.setString(2, cityId);
            stm.registerOutParameter(3, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(3);
            LOG.info("Received status code " + statusCode);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }

            return new SportCenter(
                    rs.getString("sportCenterId"),
                    new City(rs.getString("cityId"))
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
    public void createCityCenter(String sportCenterId, String cityId) throws MySQLException {
        Connection conn = null;
        CallableStatement stm = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{call createCityCenter(?,?,?)}");
            stm.setString(1, sportCenterId);
            stm.setString(2, cityId);
            stm.registerOutParameter(3, Types.INTEGER);

            var updateCount = stm.executeUpdate();
            var statusCode = stm.getInt(3);
            LOG.info("Received status code " + statusCode);
            LOG.info("Update count " + updateCount);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
        }
    }

    @Override
    public void updateSportCenterId(String newSportCenterId, String oldSportCenterId, String cityId) throws MySQLException {
        Connection conn = null;
        CallableStatement stm = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{call updateSprtCenterId(?,?,?,?)}");
            stm.setString(1, newSportCenterId);
            stm.setString(2, oldSportCenterId);
            stm.setString(3, cityId);
            stm.registerOutParameter(4, Types.INTEGER);

            var updateCount = stm.executeUpdate();
            var statusCode = stm.getInt(4);
            LOG.info("Received status code " + statusCode);
            LOG.info("Update count " + updateCount);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
        }
    }

    @Override
    public void clearSportCenter() throws MySQLException {
        Connection conn = null;
        Statement stm = null;
        try {
            conn = this.db.getConnection();
            stm = conn.createStatement();

            var updateCount = stm.executeUpdate("DELETE FROM sportCenters");
            LOG.info("Update count " + updateCount);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
        }
    }
}
