package com.example.book2play.db.utils;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.types.Player;

public interface PlayerProcedures {

    Player getPlayerInfo(String playerId) throws MySQLException;

    void createPlayer(String playerId) throws MySQLException;

    void updatePlayerId(String newPlayerId, String oldPlayerId) throws MySQLException;

    void clearPlayer() throws MySQLException;
}
