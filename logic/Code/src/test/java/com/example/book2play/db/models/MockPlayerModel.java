package com.example.book2play.db.models;

import com.example.book2play.db.PlayerModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Player;

import java.util.HashSet;
import java.util.Set;

public class MockPlayerModel implements PlayerModel {

    private Set<Player> players;

    public MockPlayerModel() {
        players = new HashSet<>();
    }

    @Override
    public void createPlayer(String playerId) throws MySQLException {
        players.add(new Player(playerId));
    }

    @Override
    public void updatePlayerId(String newPlayerId, String oldPlayerId) throws MySQLException {
        Player updatedPlayer = null;
        for (var p : players) {
            if (p.getPlayerId().equals(oldPlayerId)) {
                updatedPlayer = p;
                break;
            }
        }

        if (updatedPlayer != null) {
            players.remove(updatedPlayer);
            players.add(new Player(newPlayerId));
        }
    }

    public boolean playerExists(String playerId) {
        for (var p : players) {
            if (p.getPlayerId().equals(playerId)) {
                return true;
            }
        }

        return false;
    }
}
