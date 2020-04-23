package com.example.book2play.db;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Player;

public interface PlayerModel {

    void createPlayer(String playerId) throws MySQLException;

    void updatePlayerId(String newPlayerId, String oldPlayerId) throws MySQLException;

    void clearPlayer() throws MySQLException;

}
