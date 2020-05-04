package com.example.book2play.db.models.player;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.DBModelsTestSetup;
import com.example.types.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.fail;

public class PlayerModelUpdatePlayerIdTest extends DBModelsTestSetup {

    @Test
    public void testUpdatePlayerIdSuccessfully() throws Exception {
        var before = new HashSet<String>();
        before.add("Alice");

        var fns = new HashSet<Pair<String, String>>();
        fns.add(new Pair<>("Alice", "Bob"));

        for (var player : before) {
            PLAYER.createPlayer(player);
        }

        for (var fn : fns) {
            PLAYER.updatePlayerId(fn.getY(), fn.getX());
        }
    }

    @Test
    public void testUpdatePlayerIdExisted() throws Exception {
        final int EXPECTED_CODE = 405;
        var before = new HashSet<String>();
        before.add("Alice");
        before.add("Bob");

        var fns = new HashSet<Pair<String, String>>();
        fns.add(new Pair<>("Alice", "Bob"));
        fns.add(new Pair<>("Bob", "Alice"));

        for (var player : before) {
            PLAYER.createPlayer(player);
        }

        for (var fn : fns) {
            try {
                PLAYER.updatePlayerId(fn.getY(), fn.getX());
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void testUpdatePlayerIdInvalidFormat() throws Exception {
        final int EXPECTED_CODE = 464;
        var before = new HashSet<String>();
        before.add("Alice");

        var fns = new HashSet<Pair<String, String>>();
        fns.add(new Pair<>("Alice", "Bo$"));
        fns.add(new Pair<>("Alice", "$oB"));
        fns.add(new Pair<>("Alice", "B__b"));

        for (var player : before) {
            PLAYER.createPlayer(player);
        }

        for (var fn : fns) {
            try {
                PLAYER.updatePlayerId(fn.getY(), fn.getX());
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void testUpdatePlayerIdInvalidInputId() throws Exception {
        final int EXPECTED_CODE = 464;
        var before = new HashSet<String>();
        before.add("Alice");

        var fns = new HashSet<Pair<String, String>>();
        fns.add(new Pair<>("Bob", "Charlie"));

        for (var player : before) {
            PLAYER.createPlayer(player);
        }

        for (var fn : fns) {
            try {
                PLAYER.updatePlayerId(fn.getY(), fn.getX());
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }
}
