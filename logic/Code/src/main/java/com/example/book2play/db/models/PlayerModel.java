package com.example.book2play.db.models;

import com.example.book2play.db.MySQLModel;
import com.example.book2play.db.MySQLServer;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.types.Player;
import com.example.book2play.db.utils.PlayerProcedures;

import java.sql.*;
import java.util.logging.Logger;

public class PlayerModel extends MySQLModel implements PlayerProcedures {

    final static Logger LOG = Logger.getAnonymousLogger();

    public PlayerModel(MySQLServer db) {super(db);}

    @Override
    public Player getPlayerInfo(String playerId) throws MySQLException {
        Connection conn;
        CallableStatement stm;
        ResultSet rs;
        try{
            conn = this.db.getConnection();

            stm = conn.prepareCall("{call getPlayerInfo(?,?)}");
            stm.setString(1, playerId);
            stm.registerOutParameter(2, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(2);

            LOG.info("Received status code " + statusCode);

            if(statusCode>500){
                throw new MySQLException(statusCode);
            }
            return new Player(
                rs.getInt("playerPk"),
                rs.getString("playerId")
            );
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception"+ e.getMessage(), e);
        }
    }

    @Override
    public void createPlayer(String playerId) throws MySQLException {
        Connection conn;
        CallableStatement stm;
        try{
            conn = this.db.getConnection();

            stm = conn.prepareCall("{call createPlayer(?,?)}");
            stm.setString(1, playerId);
            stm.registerOutParameter(2, Types.INTEGER);

            stm.executeUpdate();
            var statusCode = stm.getInt(2);

            LOG.info("Received status code " + statusCode);

            if(statusCode>500){
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception"+ e.getMessage(), e);
        }
    }

    @Override
    public void updatePlayerId(String newPlayerId, String oldPlayerId) throws MySQLException {
        Connection conn;
        CallableStatement stm;
        try{
            conn = this.db.getConnection();

            stm = conn.prepareCall("{call updatePlayerId(?,?,?)}");
            stm.setString(1, newPlayerId);
            stm.setString(2, oldPlayerId);
            stm.registerOutParameter(3, Types.INTEGER);

            stm.executeUpdate();
            var statusCode = stm.getInt(3);

            LOG.info("Received status code " + statusCode);

            if(statusCode>500){
                throw new MySQLException(statusCode);
            }
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception"+ e.getMessage(), e);
        }
    }

    @Override
    public void clearPlayer() throws MySQLException {
        Connection conn;
        PreparedStatement stm;
        try{
            conn = this.db.getConnection();

            stm = conn.prepareStatement("DELETE FROM players");
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception"+ e.getMessage(), e);
        }
    }
}
