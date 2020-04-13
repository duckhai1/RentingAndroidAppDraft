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
        var expectedCode = 464
        var playerId = "Ph@t";

        try{
            PLAYER.createPlayer(playerId);
        }
    }
}
