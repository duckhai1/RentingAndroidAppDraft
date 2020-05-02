package com.example.book2play.api.player;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.types.GenericAPIResult;
import org.junit.Assert;
import org.junit.Test;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PlayerAPIPutTest extends APITestSetup {
    @Test
    public void testPutPlayerSuccess() throws Exception {
        for (var playerId : playerIDs) {
            PLAYER.createPlayer(playerId);
        }

        var testFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var playerId : playerIDs) {
            var query = new HashMap<String, List<String>>();
            query.put("oldPlayerId", new ArrayList<>());
            query.get("oldPlayerId").add(playerId);
            query.put("newPlayerId", new ArrayList<>());
            query.get("newPlayerId").add("New" + playerId);
            testFutures.add(asyncPut(PLAYER_API_PATH, playerId, query));
        }

        for (var f : testFutures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.ACCEPTED, response.statusCode());
        }
    }

    @Test
    public void testPutPlayerExpectAllMySqlExceptions() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(405);
        testInputs.add(464);

        for (var code : testInputs) {
            PLAYER.setToBeThrown(code);
            var query = new HashMap<String, List<String>>();
            query.put("oldPlayerId", new ArrayList<>());
            query.get("oldPlayerId").add("ArbitraryData");
            query.put("newPlayerId", new ArrayList<>());
            query.get("newPlayerId").add("ArbitraryData");

            var future = asyncPut(PLAYER_API_PATH, "ArbitraryData", query);
            var response = future.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testPutPlayerInvalidURLEncodedData() throws Exception {
        var oldPlayerQueries = new ArrayList<List<String>>();
        oldPlayerQueries.add(null);
        oldPlayerQueries.add(new ArrayList<>());
        oldPlayerQueries.add(new ArrayList<>());
        oldPlayerQueries.get(2).add(playerIDs.get(0));
        oldPlayerQueries.add(new ArrayList<>());
        oldPlayerQueries.get(3).addAll(playerIDs);

        var newPlayerQueries = new ArrayList<List<String>>();
        newPlayerQueries.add(null);
        newPlayerQueries.add(new ArrayList<>());
        newPlayerQueries.add(new ArrayList<>());
        newPlayerQueries.get(2).add(playerIDs.get(0));
        newPlayerQueries.add(new ArrayList<>());
        newPlayerQueries.get(3).addAll(playerIDs);

        var testFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var oldPlayerQuery : oldPlayerQueries) {
            for (var newPlayerQuery : newPlayerQueries) {
                if (newPlayerQuery != null && newPlayerQuery.size() == 1
                        && oldPlayerQuery != null && oldPlayerQuery.size() == 1) {
                    continue;
                }

                var query = new HashMap<String, List<String>>();
                if (oldPlayerQuery != null) {
                    query.put("oldPlayerId", oldPlayerQuery);
                }
                if (newPlayerQuery != null) {
                    query.put("newPlayerId", newPlayerQuery);
                }
                testFutures.add(asyncPut(PLAYER_API_PATH, "ArbitraryData", query));
            }
        }

        for (var f : testFutures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
        }
    }
}
