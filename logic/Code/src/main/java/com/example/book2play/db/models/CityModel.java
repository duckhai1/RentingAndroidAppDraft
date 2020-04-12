package com.example.book2play.db.models;

import com.example.book2play.db.MySQLModel;
import com.example.book2play.db.MySQLServer;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.types.City;
import com.example.book2play.db.utils.CityProcedures;
import com.example.book2play.db.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class CityModel extends MySQLModel implements CityProcedures {

    public CityModel(MySQLServer db) {
        super(db);
    }


    final static Logger LOG = Logger.getAnonymousLogger();
    // TODO: call stored procedures
    public Collection<City> getCities() throws MySQLException {
        ArrayList<City> cities = new ArrayList<>();
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;
        try {
            conn = this.db.getConnection();

            stm = conn.prepareCall("{call getCities(?)}");
            stm.registerOutParameter(1, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(1);

            if(statusCode >= 400 && statusCode < 500){
                throw new MySQLException(statusCode);
            }
            while (rs.next()) {
                cities.add(new City(
                        rs.getString(1)
                ));
            }

            return cities;
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
            DBUtils.quietCloseResultSet(rs);
        }
    }

    public void createCity(String cityId) throws MySQLException {
        Connection conn = null;
        try {
            conn = this.db.getConnection();

            var cs = conn.prepareCall("{CALL createCity(?, ?)}");
            cs.setString(1, cityId);
            cs.registerOutParameter(2, Types.INTEGER);

            cs.executeUpdate();
            var statusCode = cs.getInt(2);

            LOG.info("Received status code " + statusCode);

            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
        }
    }

    @Override
    public void clearCity() throws MySQLException {
        Connection conn = null;
        Statement stm = null;
        try {
            conn = this.db.getConnection();
            stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM cities");
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception" + e.getMessage(), e);
        } finally {
            DBUtils.quietCloseConnection(conn);
            DBUtils.quietCloseStatement(stm);
        }
    }
}
