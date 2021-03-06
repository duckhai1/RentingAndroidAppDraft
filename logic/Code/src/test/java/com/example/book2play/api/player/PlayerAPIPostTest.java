package com.example.book2play.api.player;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.Player;
import com.example.types.GenericAPIResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class PlayerAPIPostTest extends APITestSetup {
    @Test
    public void testPostPlayerSuccess() throws Exception {
        for (var s : playerIDs) {
            var response = postJSON(PLAYER_API_PATH, s, null);
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
            var response = postJSON(PLAYER_API_PATH, "ArbitraryData", new Player("ArbitraryData"));
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testPostPlayerNoToken() throws Exception {
        var response = postJSON(PLAYER_API_PATH, null, null);
        Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
    }
}
