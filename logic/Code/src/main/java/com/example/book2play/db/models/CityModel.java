package com.example.book2play.db.models;

import com.example.book2play.db.MySQLModel;
import com.example.book2play.db.MySQLServer;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.types.City;
import com.example.book2play.db.utils.CityProcedures;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class CityModel extends MySQLModel implements CityProcedures {

    final static Logger LOG = Logger.getAnonymousLogger();

    public CityModel(MySQLServer db) {
        super(db);
    }

    public Collection<City> getCities() throws MySQLException {
        ArrayList<City> cities = new ArrayList<>();
        Connection conn;
        PreparedStatement stm;
        ResultSet rs;
        try{
            conn = this.db.getConnection();

            stm = conn.prepareStatement("SELECT * FROM Cities");

            rs = stm.executeQuery();

            while(rs.next()){
                cities.add(new City(
                    rs.getInt(1),
                    rs.getString(2)
                ));
            }

            return cities;
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception"+ e.getMessage(), e);
        }
    }

    public void createCity(String cityId) throws MySQLException {
        Connection conn;
        try {
            conn = this.db.getConnection();

            var cs = conn.prepareCall(("{CALL createCity(?, ?)}"));
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
        }
    }

    @Override
    public void clearCity() throws MySQLException {
        Connection conn;
        PreparedStatement stm;
        try{
            conn = this.db.getConnection();

            stm = conn.prepareStatement("DELETE * FROM City");

            stm.executeUpdate();
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception" + e.getMessage(), e);
        }
    }
}
