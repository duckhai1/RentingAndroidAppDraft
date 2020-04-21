package com.example.book2play.db.models;

import com.example.book2play.db.AppDataSource;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.utils.ResultSetUtils;
import com.example.book2play.types.Player;

import java.sql.*;

public class PlayerModel extends AbstractModel implements com.example.book2play.db.PlayerModel {

    public PlayerModel(AppDataSource db) {
        super(db);
    }

    @Override
    public Player getPlayerInfo(String playerId) throws MySQLException {
        Connection conn = null;
        CallableStatement stm = null;
        ResultSet rs = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{CALL getPlayerInfo(?, ?)}");
            stm.setString(1, playerId);
            stm.registerOutParameter(2, Types.INTEGER);

            rs = stm.executeQuery();
            var statusCode = stm.getInt(2);
            LOG.info("Received status code " + statusCode);
            if (statusCode >= 400 && statusCode < 500) {
                throw new MySQLException(statusCode);
            }

            if (!rs.next()) {
                throw new MySQLException("Data not found");
            }

            return ResultSetUtils.singlePlayerFromResultSet(rs);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
            ResultSetUtils.quietCloseResultSet(rs);
        }
    }

    @Override
    public void createPlayer(String playerId) throws MySQLException {
        Connection conn = null;
        CallableStatement stm = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{call createPlayer(?,?)}");
            stm.setString(1, playerId);
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

    @Override
    public void updatePlayerId(String newPlayerId, String oldPlayerId) throws MySQLException {
        Connection conn = null;
        CallableStatement stm = null;

        try {
            conn = this.db.getConnection();
            stm = conn.prepareCall("{call updatePlayerId(?,?,?)}");
            stm.setString(1, newPlayerId);
            stm.setString(2, oldPlayerId);
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

    @Override
    public void clearPlayer() throws MySQLException {
        Connection conn = null;
        Statement stm = null;
        try {
            conn = this.db.getConnection();
            stm = conn.createStatement();

            var updateCount = stm.executeUpdate("DELETE FROM players");
            LOG.info("Update count " + updateCount);
        } catch (SQLException e) {
            throw new MySQLException("Unexpected exception " + e.getMessage(), e);
        } finally {
            ResultSetUtils.quietCloseConnection(conn);
            ResultSetUtils.quietCloseStatement(stm);
        }
    }
}
