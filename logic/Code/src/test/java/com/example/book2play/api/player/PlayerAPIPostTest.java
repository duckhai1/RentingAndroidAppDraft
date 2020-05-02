package com.example.book2play.api.player;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.Player;
import com.example.types.GenericAPIResult;
import org.junit.Assert;
import org.junit.Test;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class PlayerAPIPostTest extends APITestSetup {
    @Test
    public void testPostPlayerSuccess() throws Exception {
        var futures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var s : playerIDs) {
            futures.add(asyncPostJSON(PLAYER_API_PATH, s, null));
        }

        for (var f : futures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.CREATED, response.statusCode());
        }

    }

    @Test
    public void testPostPlayerExpectAllMySqlExceptions() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(405);
        testInputs.add(464);

        for (var code : testInputs) {
            PLAYER.setToBeThrown(code);
            var future = asyncPostJSON(PLAYER_API_PATH, "ArbitraryData", new Player("ArbitraryData"));
            var response = future.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testPostPlayerUnauthorized() throws Exception {
        var responseFuture = asyncPostJSON(PLAYER_API_PATH, null, null);
        var response = responseFuture.get();

        Assert.assertEquals(HTTPStatus.UNAUTHORIZED, response.statusCode());
    }
}
