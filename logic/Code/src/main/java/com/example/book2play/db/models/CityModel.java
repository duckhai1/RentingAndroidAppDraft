package com.example.book2play.db.models;

import com.example.book2play.db.MySQLModel;
import com.example.book2play.db.MySQLServer;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.types.City;
import com.example.book2play.db.utils.CityProcedures;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;

public class CityModel extends MySQLModel implements CityProcedures {

    public CityModel(MySQLServer db) {
        super(db);
    }

    public Collection<City> getCities() {
        return null;
    }

    public void createCity(String cityId) throws MySQLException {
        Connection conn;
        try {
            conn  = this.db.getConnection();

            var cs = conn.prepareCall(("{CALL createCity(?, ?)}"));
            cs.setString(1, cityId);
            cs.registerOutParameter(2, Types.INTEGER);

            cs.executeUpdate();
            var statusCode = cs.getInt(2);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception" + e.getMessage(), e);
        }
    }

    @Override
    public void clearCity() throws MySQLException {

    }
}
