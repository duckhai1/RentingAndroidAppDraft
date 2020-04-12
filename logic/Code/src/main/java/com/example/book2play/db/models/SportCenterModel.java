package com.example.book2play.db.models;

import com.example.book2play.db.MySQLModel;
import com.example.book2play.db.MySQLServer;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.types.Booking;
import com.example.book2play.db.types.SportCenter;
import com.example.book2play.db.utils.SportCenterProcedures;
import com.mysql.cj.log.Log;

import java.sql.*;
import java.util.logging.Logger;

public class SportCenterModel extends MySQLModel implements SportCenterProcedures {
    public SportCenterModel(MySQLServer db) { super(db);}
    final static Logger LOG = Logger.getAnonymousLogger();

    @Override
    public SportCenter getSportCenterInfo(String sportCenterId, String cityId) throws MySQLException {
        SportCenter sportCenter;
        Connection conn;
        CallableStatement stm;
        ResultSet rs;

        try{
            conn = this.db.getConnection();

            stm = conn.prepareCall("{call getSportcenterInfo(?,?,?)");
            stm.setString(1, sportCenterId);
            stm.setString(2, cityId);
            stm.registerOutParameter(3, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(3);

            LOG.info("Received status code " + statusCode);

            if(statusCode>500){
                throw new MySQLException(statusCode);
            }
            return new SportCenter(
                    rs.getInt("sportcenterPk"),
                    rs.getString("sportcenterId"),
                    rs.getInt("cityPk")
            );
        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception" + e.getMessage(), e);
        }
    }

    @Override
    public void createCityCenter(String sportCenterId, String cityId) throws MySQLException {
        Connection conn;
        CallableStatement stm;

        try{
            conn = this.db.getConnection();

            stm = conn.prepareCall("{call createCityCenter(?,?,?)}");
            stm.setString(1, sportCenterId);
            stm.setString(2, cityId);
            stm.registerOutParameter(3, Types.INTEGER);

            stm.executeUpdate();
            var statusCode = stm.getInt(3);

            LOG.info("Received status code " + statusCode);

            if(statusCode>500){
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception" + e.getMessage(), e);
        }
    }

    @Override
    public void updateSportCenterId(String newSportCenterId, String oldSportCenterId, String cityId) throws MySQLException {
        Connection conn;
        CallableStatement stm;
        try{
            conn = this.db.getConnection();

            stm = conn.prepareCall("{call updateSprtCenterId(?,?,?,?)}");
            stm.setString(1, newSportCenterId);
            stm.setString(2, oldSportCenterId);
            stm.setString(3, cityId);
            stm.registerOutParameter(4, Types.INTEGER);

            stm.executeUpdate();
            var statusCode = stm.getInt(4);

            LOG.info("Received status code " + statusCode);

            if(statusCode>500){
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception" + e.getMessage(), e);
        }
    }

    @Override
    public void clearSportCenter() throws MySQLException {
        Connection conn;
        PreparedStatement stm;
        try{
            conn = this.db.getConnection();

            stm = conn.prepareStatement("DELETE * FROM sportcenters");

            stm.executeUpdate();
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception" + e.getMessage(), e);
        }
    }
}
