package com.example.book2play.db.models;

import com.example.book2play.db.exceptions.MySQLException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PlayerModelTest extends ModelTestSetup {

    @Test
    public void createPlayerOnEmptyDatabase() throws Exception {
        var playerId = "Phat";

        PLAYER.createPlayer(playerId);
    }

    @Test
    public void createPlayerWithInvalidPlayerId() throws Exception {
        var expectedCode = 464;
        var playerId = "Phat@@";

        try {
            PLAYER.createPlayer(playerId);
            fail("Expecting MySQLException with statusCode " + expectedCode);
        } catch (MySQLException e) {
            assertEquals(expectedCode, e.getStatusCode());
        }
    }

    @Test
    public void createPlayerWithDuplicate() throws Exception {
        var expectedCode = 405;
        var plauerId = "Phat";

        PLAYER.createPlayer(plauerId);
        try {
            PLAYER.createPlayer(plauerId);
            fail("Expecting MySQLException with statusCode " + expectedCode);
        } catch (MySQLException e) {
            assertEquals(expectedCode, e.getStatusCode());
        }
    }

    @Test
    public void getPlayerInfo() throws Exception {
        var playerId = "Phat";

        //screnario
        PLAYER.createPlayer(playerId);

        //test
        PLAYER.getPlayerInfo(playerId);
    }

    @Test
    public void getPlayerInfoWithInvalidPlayerId() throws Exception {
        var playerId = "Phat";
        var playerIdInvalid = "Ph@t";
        var expectedCode = 464;

        //screnario
        PLAYER.createPlayer(playerId);

        //test
        try {
            PLAYER.getPlayerInfo(playerIdInvalid);
            fail("Error code: " + expectedCode);
        } catch (MySQLException e) {
            assertEquals(expectedCode, e.getStatusCode());
        }
    }

    @Test
    public void updateNewPlayerId() throws Exception {
        var oldPlayerId = "Phat";
        var newPlayerId = "Tung";

        PLAYER.createPlayer(oldPlayerId);

        PLAYER.updatePlayerId(newPlayerId, oldPlayerId);
    }

    @Test
    public void updatePlayerIdWithInvalidPlayerId() throws Exception {
        var oldPlayerId = "Phat";
        var  newPlayerId = "Tung@";
        var expectedCode = 464;

        PLAYER.createPlayer(oldPlayerId);

        try {
            PLAYER.updatePlayerId(newPlayerId, oldPlayerId);
            fail("Expected error code: " + expectedCode);
        }catch (MySQLException e){
            assertEquals(expectedCode, e.getStatusCode());
        }

    }

    @Test
    public void updatePlayerIdWithNotExistPlayerId() throws Exception {
        var oldPlayerId = "Phat";
        var oldInvalidPlayerId = "Phat@";
        var newPlayerId = "Tung@";
        var expectedCode = 464;

        PLAYER.createPlayer(oldPlayerId);

        try {
            PLAYER.updatePlayerId(newPlayerId, oldInvalidPlayerId);
            fail("Expected error code: " + expectedCode);
        } catch (MySQLException e) {
            assertEquals(expectedCode, e.getStatusCode());
        }
    }
}
