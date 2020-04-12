package com.example.book2play.db.models;

import com.example.book2play.db.MySQLModel;
import com.example.book2play.db.MySQLServer;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.types.Court;
import com.example.book2play.db.utils.CourtProcedures;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class CourtModel extends MySQLModel implements CourtProcedures {

    public CourtModel(MySQLServer db) { super(db);}

    final static Logger LOG = Logger.getAnonymousLogger();

    @Override
    public Court getCourtInfo(String courtId, String cityId, String sportCenterId) throws MySQLException {
        Connection conn;
        CallableStatement stm;
        ResultSet rs;
        try{
            conn = this.db.getConnection();

            stm = conn.prepareCall("{call getCourtInfo(?,?,?,?)}");
            stm.setString(1, courtId);
            stm.setString(2, cityId);
            stm.setString(3, sportCenterId);
            stm.registerOutParameter(4, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(4);

            LOG.info("Received status code " + statusCode);

            if(statusCode>500){
                throw new MySQLException(statusCode);
            }

            return new Court(
                rs.getInt("courtPk"),
                rs.getString("courtId"),
                rs.getInt("sportcenterPk")
            );
        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception" + e.getMessage(), e);
        }
    }

    @Override
    public void createCityCenterCourt(String courtId, String cityId, String sportCenterId) throws MySQLException {
        Connection conn;
        CallableStatement stm;
        try{
            conn = this.db.getConnection();

            stm = conn.prepareCall("{call createCityCenterCourt(?,?,?,?)}");
            stm.setString(1, courtId);
            stm.setString(2, cityId);
            stm.setString(3, sportCenterId);
            stm.registerOutParameter(4, Types.INTEGER);

            stm.executeUpdate();
            var statusCode = stm.getInt(4);

            LOG.info("Received status code " + statusCode);

            if(statusCode>500){
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception" + e.getMessage(), e);
        }
    }

    @Override
    public void updateCourtId(String newCourtId, String oldCourtId, String cityId, String sportCenterId) throws MySQLException {
        Connection conn;
        CallableStatement stm;
        try{
            conn = this.db.getConnection();

            stm = conn.prepareCall("{call updateCourtId(?,?,?,?,?)}");
            stm.setString(1, newCourtId);
            stm.setString(2, oldCourtId);
            stm.setString(3, cityId);
            stm.setString(4, sportCenterId);
            stm.registerOutParameter(5, Types.INTEGER);

            stm.executeUpdate();
            var statusCode = stm.getInt(5);

            LOG.info("Received status code " + statusCode);

            if(statusCode>500){
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception" + e.getMessage(), e);
        }
    }

    @Override
    public void clearCourt() throws MySQLException {
        Connection conn;
        PreparedStatement stm;
        try{
            conn = this.db.getConnection();

            stm = conn.prepareStatement("DELETE FROM courts");

            stm.executeUpdate();
        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception" + e.getMessage(), e);
        }
    }

    public Collection<Court> getCourtsInCities(String cityId) throws MySQLException {
        ArrayList<Court> courts = new ArrayList<>();
        Connection conn;
        CallableStatement stm;
        ResultSet rs;
        try{
            conn = this.db.getConnection();

            stm = conn.prepareCall("{call getCourtInCities(?,?)}");
            stm.setString(1, cityId);
            stm.registerOutParameter(2, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(2);
            while(rs.next()){
                courts.add(new Court(
                    rs.getInt("bookingPk"),
                    rs.getString("boookingId"),
                    rs.getInt("sportcenterPk")
                ));
            }
            return courts;
        } catch (SQLException e) {
            throw new MySQLException("Unexpected Exception" + e.getMessage(), e);
        }
    }
}
