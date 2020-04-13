package com.example.book2play.db.models;

import com.example.book2play.db.exceptions.MySQLException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PlayerModelTest extends ModelTestSetup {
    @Test
    public void createPlayerOnEmptyDatabase() throws Exception{
        var playerId = "Phat";
        PLAYER.createPlayer(playerId);
    }

    @Test
    public void createPlayerWithInvalidPlayerId() throws Exception{
        var expectedCode = 464;
        var playerId = "Phat @@";

        try{
            PLAYER.createPlayer(playerId);
            fail("Expecting MySQLException with statusCode " + expectedCode);
        }catch( MySQLException e){
            assertEquals(expectedCode, e.getStatusCode());
        }
    }

    @Test
    public void createPlayerWithDuplicate() throws Exception{
        var expectedCode = 405;
        var plauerId = "Phat";
        PLAYER.createPlayer(plauerId);
        try{
            PLAYER.createPlayer(plauerId);
            fail("Expecting MySQLException with statusCode " + expectedCode);
        }catch (MySQLException e){
            assertEquals(expectedCode, e.getStatusCode());
        }
    }
}
