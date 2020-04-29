package com.example.book2play.db.models;

import com.example.book2play.db.AppDataSource;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.utils.ResultSetUtils;
import com.example.book2play.types.Court;

import java.sql.*;
import java.util.Collection;

/**
 * Implements CourtModel interfaces for working with the stored procedures from MySQL
 * The connection is establish using MySQL DataSource object
 */
public class MySQLCourt extends AbstractModel implements com.example.book2play.db.CourtModel {

    public MySQLCourt(AppDataSource db) {
        super(db);
    }

    /**
     * Create a new connection to the data source and call the stored procedure
     * to create a new court in the given sport center and city
     *
     * @param courtId       the unique identifier of the court in the given sport center
     * @param cityId        the unique identifier of the city
     * @param sportCenterId the unique identifier if the sport center in the given city
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    @Override
    public void createCityCenterCourt(String courtId, String cityId, String sportCenterId) throws MySQLException {
        LOG.info("Calling createCityCenterCourt");
        Connection conn = null;
        CallableStatement stm = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL createCityCenterCourt(?, ?, ?, ?)}");
            stm.setString(1, courtId);
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
     * to update the court id
     *
     * @param newCourtId    the new unique identifier in the given sport center to be given to the court
     * @param oldCourtId    the current unique identifier of the court in the given sport center
     * @param cityId        the unique identifier of the city
     * @param sportCenterId the unique identifier of the sport center in the given city
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    @Override
    public void updateCourtId(String newCourtId, String oldCourtId, String cityId, String sportCenterId) throws MySQLException {
        LOG.info("Calling updateCourtId");
        Connection conn = null;
        CallableStatement stm = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{call updateCourtId(?,?,?,?,?)}");
            stm.setString(1, newCourtId);
            stm.setString(2, oldCourtId);
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


    /**
     * Create a new connection to the data source and clear the relation
     *
     * @throws MySQLException
     * @deprecated will be moved to test only
     */
    @Override
    public void clearCourt() throws MySQLException {
        LOG.info("Calling clearCourt");
        Connection conn = null;
        Statement stm = null;

        try {
            conn = this.db.getConnection();
            stm = conn.createStatement();

            var updateCount = stm.executeUpdate("DELETE FROM courts");
            LOG.info("Update count " + updateCount);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
        }
    }

    /**
     * Create a new connection to the data source and call the stored procedure
     * to get the all the courts in the given city
     *
     * @param cityId the unique identifier of the city
     * @return collection of courts in the city
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    @Override
    public Collection<Court> getCityCourts(String cityId) throws MySQLException {
        LOG.info("Calling getCityCourts");
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL getCityCourts(?, ?)}");
            stm.setString(1, cityId);
            stm.registerOutParameter(2, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(2);
            LOG.info("Received status code " + statusCode);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }

            return ResultSetUtils.courtsFromResultSet(rs);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
            ResultSetUtils.quietCloseResultSet(rs);
        }
    }

    /**
     * Create a new connection to the data source and call the stored procedure
     * to get the all the courts in the given sport center
     *
     * @param sportCenterId the unique identifier for the sport center in the given city
     * @param cityId        the unique identifier of the city
     * @return collection if courts in the sport centers
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    public Collection<Court> getSportCenterCourts(String sportCenterId, String cityId) throws MySQLException {
        LOG.info("Calling getSportCenterCourts");
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{call getSportCenterCourts(?, ?, ?)}");
            stm.setString(1, sportCenterId);
            stm.setString(2, cityId);
            stm.registerOutParameter(3, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(3);
            LOG.info("Received status code " + statusCode);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }

            return ResultSetUtils.courtsFromResultSet(rs);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
            ResultSetUtils.quietCloseResultSet(rs);
        }
    }
}
