package com.example.book2play.db.models;

import com.example.book2play.db.AppDataSource;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.utils.ResultSetUtils;
import com.example.book2play.types.City;

import java.sql.*;
import java.util.Collection;

/**
 * Implements CityModel interfaces for working with the stored procedures from MySQL
 * The connection is establish using MySQL DataSource object
 */
public class MySQLCity extends AbstractModel implements com.example.book2play.db.CityModel {

    public MySQLCity(AppDataSource db) {
        super(db);
    }

    /**
     * Create a new connection to the data source and call the stored procedure
     * to get all the cities known by the data source
     *
     * @return collection of cities
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    public Collection<City> getCities() throws MySQLException {
        LOG.info("Calling getCities");
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL getCities(?)}");
            stm.registerOutParameter(1, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(1);
            LOG.info("Received status code " + statusCode);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }

            return ResultSetUtils.citiesFromResultSet(rs);
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
     * to create a new city with the given unique identifier to the data source
     *
     * @param cityId the unique identifier of the city
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    public void createCity(String cityId) throws MySQLException {
        LOG.info("Calling createCity");
        Connection conn = null;
        CallableStatement stm = null;
        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL createCity(?, ?)}");
            stm.setString(1, cityId);
            stm.registerOutParameter(2, Types.INTEGER);

            var updateCount = stm.executeUpdate();
            var statusCode = stm.getInt(2);
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
