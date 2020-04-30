package com.example.book2play.db.models;

import com.example.book2play.db.AppDataSource;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.utils.ResultSetUtils;
import com.example.book2play.types.SportCenter;

import java.sql.*;
import java.util.Collection;

/**
 * Implements SportCenterModel interfaces for working with the stored procedures from MySQL
 * The connection is establish using MySQL DataSource object
 */
public class MySQLSportCenter extends AbstractModel implements com.example.book2play.db.SportCenterModel {

    public MySQLSportCenter(AppDataSource db) {
        super(db);
    }

    /**
     * Create a new connection to the data source and call the stored procedure
     * to create a new sport center
     *
     * @param sportCenterId the unique identifier in the city of the new sport center
     * @param cityId        the unique identifier of the city
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    @Override
    public void createCityCenter(String sportCenterId, String cityId) throws MySQLException {
        LOG.info("Calling createCityCenter");
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
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
        }
    }

    /**
     * Create a new connection to the data source and call the stored procedure
     * to update the sport center unique identifier
     *
     * @param newSportCenterId the new unique identifier to be assigned to the sport center
     * @param oldSportCenterId the current unique identifier of the sport center
     * @param cityId           the unique identifier of the city that the sport centers locates in
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    @Override
    public void updateSportCenterId(String newSportCenterId, String oldSportCenterId, String cityId) throws MySQLException {
        LOG.info("Calling updateSportCenterId");
        Connection conn = null;
        CallableStatement stm = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{call updateSportCenterId(?,?,?,?)}");
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
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
        }
    }



    /**
     * Create a new connection to the data source and call the stored procedure
     * to all the sport centers in the given city
     *
     * @param cityId the unique identifier of the city
     * @return collection of sport centers locate in the city
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    @Override
    public Collection<SportCenter> getCitySportCenters(String cityId) throws MySQLException {
        LOG.info("Calling getCitySportCenters");
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;
        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{call getCitySportCenters(?,?)}");
            stm.setString(1, cityId);
            stm.registerOutParameter(2, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(2);
            LOG.info("Received status code " + statusCode);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }

            return ResultSetUtils.sportCentersFromResultSet(rs);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
            ResultSetUtils.quietCloseResultSet(rs);
        }
    }
}
