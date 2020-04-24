package com.example.book2play.db.models.player;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.ModelTestSetup;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PlayerModelCreatePlayerTest extends ModelTestSetup {

    @Test
    public void testCreatePlayerOnEmptyDatabase() throws Exception {
        var playerId = "HoChiMinh";
        PLAYER.createPlayer(playerId);
    }

    @Test
    public void testCreatePlayerOnEmptyDataMultipleIds() throws Exception {
        var players = new ArrayList<String>();
        players.add("Alice");
        players.add("Bob");
        players.add("Charlie");

        for (var player : players) {
            PLAYER.createPlayer(player);
        }
    }

    @Test
    public void testCreatePlayerWithDuplicateId() throws Exception {
        final int EXPECTED_CODE = 405;
        var playerId = "HoChiMinh";

        PLAYER.createPlayer(playerId);
        try {
            PLAYER.createPlayer(playerId);
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreatePlayerWithInvalidIdFormat() {
        final int EXPECTED_CODE = 464;

        var players = new ArrayList<String>();
        players.add("(harLi3");
        players.add("Alic$");
        players.add("B__B");

        for (var player : players) {
            try {
                PLAYER.createPlayer(player);
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
            }
        }
    }
}
