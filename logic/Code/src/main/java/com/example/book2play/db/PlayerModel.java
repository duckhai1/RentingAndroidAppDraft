package com.example.book2play.db;

import com.example.book2play.db.exceptions.MySQLException;

public interface PlayerModel {

    /**
     * Create a new player with the given player id
     *
     * @param playerId the unique identifier for new the player
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    void createPlayer(String playerId) throws MySQLException;

    /**
     * Change the unique identifier of a player
     *
     * @param newPlayerId the new unique identifier
     * @param oldPlayerId the current unique identifier for the player
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    void updatePlayerId(String newPlayerId, String oldPlayerId) throws MySQLException;

    /**
     * Confirm the given id is a playerId
     *
     * @param playerId the unique identifier for new the player
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    String isPlayer(String playerId) throws MySQLException;
    /**
     * Empty the player relation, for testing
     *
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     * @deprecated will be moved to test only
     */
    void clearPlayer() throws MySQLException;

}
