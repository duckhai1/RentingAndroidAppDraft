package com.example.book2play.api.player;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.types.GenericAPIResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerAPIPutTest extends APITestSetup {
    @Test
    public void testPutPlayerSuccess() throws Exception {
        for (var playerId : playerIDs) {
            PLAYER.createPlayer(playerId);
        }

        for (var playerId : playerIDs) {
            var query = new HashMap<String, List<String>>();
            query.put("oldPlayerId", new ArrayList<>());
            query.get("oldPlayerId").add(playerId);
            query.put("newPlayerId", new ArrayList<>());
            query.get("newPlayerId").add("New" + playerId);
            var response = put(PLAYER_API_PATH, playerId, query);
            Assert.assertEquals(HTTPStatus.ACCEPTED, response.statusCode());
        }
    }

    @Test
    public void testPutPlayerExpectAllMySqlExceptions() throws Exception {
        var testInputs = new ArrayList<Integer>();
        PLAYER.createPlayer(playerIDs.get(0));
        testInputs.add(405);
        testInputs.add(464);

        for (var code : testInputs) {
            PLAYER.setToBeThrown(code);
            var query = new HashMap<String, List<String>>();
            query.put("oldPlayerId", new ArrayList<>());
            query.get("oldPlayerId").add("ArbitraryData");
            query.put("newPlayerId", new ArrayList<>());
            query.get("newPlayerId").add("ArbitraryData");

            var response = put(PLAYER_API_PATH, playerIDs.get(0), query);
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testPutPlayerInvalidURLEncodedData() throws Exception {
        PLAYER.createPlayer(playerIDs.get(0));

        var oldPlayerQueries = new ArrayList<List<String>>();
        oldPlayerQueries.add(new ArrayList<>());
        oldPlayerQueries.add(new ArrayList<>());
        oldPlayerQueries.get(1).add(playerIDs.get(0));
        oldPlayerQueries.add(new ArrayList<>());
        oldPlayerQueries.get(2).addAll(playerIDs);

        var newPlayerQueries = new ArrayList<List<String>>();
        newPlayerQueries.add(new ArrayList<>());
        newPlayerQueries.add(new ArrayList<>());
        newPlayerQueries.get(1).add(playerIDs.get(0));
        newPlayerQueries.add(new ArrayList<>());
        newPlayerQueries.get(2).addAll(playerIDs);

        for (var oldPlayerQuery : oldPlayerQueries) {
            for (var newPlayerQuery : newPlayerQueries) {
                if (newPlayerQuery.size() == 1 && oldPlayerQuery.size() == 1) {
                    continue;
                }

                var query = new HashMap<String, List<String>>();
                query.put("oldPlayerId", oldPlayerQuery);
                query.put("newPlayerId", newPlayerQuery);

                var response = put(PLAYER_API_PATH, playerIDs.get(0), query);
                Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
            }
        }
    }
}
