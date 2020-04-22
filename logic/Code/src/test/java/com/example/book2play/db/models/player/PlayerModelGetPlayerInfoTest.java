package com.example.book2play.db.models.player;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.ModelTestSetup;
import com.example.book2play.types.Player;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PlayerModelGetPlayerInfoTest extends ModelTestSetup {

    @Test
    public void testGetPlayerInfo() throws Exception {
        var players = new ArrayList<Player>();
        players.add(new Player("Alice"));
        players.add(new Player("Bob"));
        players.add(new Player("Charlie"));

        for (var player : players) {
            PLAYER.createPlayer(player.getPlayerId());
        }

        for (var player : players) {
            var output = PLAYER.getPlayerInfo(player.getPlayerId());
            assertEquals(player, output);
        }
    }

    @Test
    public void testGetPlayerInvalidId() throws Exception {
        final int EXPECTED_CODE = 464;
        var players = new ArrayList<Player>();
        players.add(new Player("Alice"));

        var inputs = new ArrayList<Player>();
        inputs.add(new Player("Al!ce"));
        inputs.add(new Player("Alic$"));
        inputs.add(new Player("Al__e"));
        inputs.add(new Player("Bob"));

        for (var player : players) {
            PLAYER.createPlayer(player.getPlayerId());
        }

        for (var player : inputs) {
            try {
                var output = PLAYER.getPlayerInfo(player.getPlayerId());
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
            }
        }
    }
}
