package com.example.book2play.db.models;

import com.example.book2play.db.PlayerModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Player;

import java.util.HashSet;
import java.util.Set;

public class MockPlayerModel implements PlayerModel {

    private Set<Player> players;

    private static MockPlayerModel SINGLETON = null;

    private MockPlayerModel() {
        players = new HashSet<>();
    }

    public static MockPlayerModel getInstance() {
        if (SINGLETON == null) {
            SINGLETON = new MockPlayerModel();
        }

        return SINGLETON;
    }


    @Override
    public void createPlayer(String playerId) throws MySQLException {
        var newPlayer = new Player(playerId);
        if (players.contains(newPlayer)) {
            throw new MySQLException(405);
        }
        players.add(newPlayer);
    }

    @Override
    public void updatePlayerId(String newPlayerId, String oldPlayerId) throws MySQLException {
        var oldPlayer = new Player(oldPlayerId);
        if (!players.contains(oldPlayer)) {
            throw new MySQLException(464);
        }

        players.remove(oldPlayer);
        players.add(new Player(newPlayerId));
    }

    public boolean isPlayerExist(String playerId) {
        return players.contains(new Player(playerId));
    }

    public void clearPlayers() {
        players.clear();
    }
}
