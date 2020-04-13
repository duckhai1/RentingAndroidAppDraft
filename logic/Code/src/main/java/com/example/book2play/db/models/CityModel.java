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

public class CityModel extends MySQLModel implements CityProcedures {

    public CityModel(MySQLServer db) {
        super(db);
    }

    public Collection<City> getCities() throws MySQLException {
        ArrayList<City> cities = new ArrayList<>();
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL getCities(?)}");
            stm.registerOutParameter(1, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(1);

            if (statusCode >= 400 && statusCode < 500) {
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
        CallableStatement stm = null;
        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL createCity(?, ?)}");
            stm.setString(1, cityId);
            stm.registerOutParameter(2, Types.INTEGER);
            stm.executeUpdate();

            var statusCode = stm.getInt(2);
            LOG.info("Received status code " + statusCode);
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
