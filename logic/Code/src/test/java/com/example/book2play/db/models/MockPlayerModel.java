package com.example.book2play.db.models;

import com.example.book2play.db.PlayerModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Player;

public class MockPlayerModel extends MockModel implements PlayerModel {

    private MockModelDataSource ds;

    public MockPlayerModel(MockModelDataSource ds) {
        this.ds = ds;
    }

    @Override
    public void createPlayer(String playerId) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }
        var newPlayer = new Player(playerId);
        if (ds.getPlayers().contains(newPlayer)) {
            throw new MySQLException(405);
        }
        ds.getPlayers().add(newPlayer);
    }

    @Override
    public void updatePlayerId(String newPlayerId, String oldPlayerId) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }
        var oldPlayer = new Player(oldPlayerId);
        if (!ds.getPlayers().contains(oldPlayer)) {
            throw new MySQLException(464);
        }

        ds.getPlayers().remove(oldPlayer);
        ds.getPlayers().add(new Player(newPlayerId));
    }

    @Override
    public boolean isPlayerExist(String playerId) {
        return ds.getPlayers().contains(new Player(playerId));
    }

    public void clearPlayers() {
        ds.getPlayers().clear();
    }
}
