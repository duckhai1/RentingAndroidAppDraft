package com.example.book2play.db.models;

import com.example.book2play.db.AppDataSource;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.utils.ResultSetUtils;

import java.sql.*;

/**
 * Implements PlayerModel interfaces for working with the stored procedures from MySQL
 * The connection is establish using MySQL DataSource object
 */
public class PlayerModel extends AbstractModel implements com.example.book2play.db.PlayerModel {

    public PlayerModel(AppDataSource db) {
        super(db);
    }

    /**
     * Create a new connection to the data source and call the stored procedure
     * to create a new player with the given unique identifier
     *
     * @param playerId the unique identifier for new the player
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    @Override
    public void createPlayer(String playerId) throws MySQLException {
        LOG.info("Calling createPlayer");
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

    /**
     * Create a new connection to the data source and call the stored procedure
     * to update the player unique identifier
     *
     * @param newPlayerId the new unique identifier
     * @param oldPlayerId the current unique identifier for the player
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    @Override
    public void updatePlayerId(String newPlayerId, String oldPlayerId) throws MySQLException {
        LOG.info("Calling updatePlayerId");
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

    /**
     * Create a new connection to the data source and clear the relation
     *
     * @throws MySQLException
     * @deprecated will be moved to test only
     */
    @Override
    public void clearPlayer() throws MySQLException {
        LOG.info("Calling clearPlayer");
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
