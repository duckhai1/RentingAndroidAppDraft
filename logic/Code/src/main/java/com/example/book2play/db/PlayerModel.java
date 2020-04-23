package com.example.book2play.db;

import com.example.book2play.db.exceptions.MySQLException;

public interface PlayerModel {

    /**
     * Create a new player with the given player id
     *
     * @param playerId the unique identifier for new the player
     * @throws MySQLException
     */
    void createPlayer(String playerId) throws MySQLException;

    /**
     * Change the unique identifier of a player
     *
     * @param newPlayerId the new unique identifier
     * @param oldPlayerId the current unique identifier for the player
     * @throws MySQLException
     */
    void updatePlayerId(String newPlayerId, String oldPlayerId) throws MySQLException;

    /**
     * Empty the player relation, for testing
     *
     * @throws MySQLException
     */
    void clearPlayer() throws MySQLException;

}
