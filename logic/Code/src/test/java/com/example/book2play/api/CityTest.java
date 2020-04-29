package com.example.book2play.api;

import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.City;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class CityTest extends SetupAPITest {
    private final static URI API_PATH = URI.create("http://" + HOST + ':' + PORT + Server.CITY_BASE_URL);

    @Test
    public void testPostCity() throws Exception {
        var testInputs = new ArrayList<String>();
        testInputs.add("HCM");
        testInputs.add("HaNoi");
        testInputs.add("DaNang");
        testInputs.add("Hue");

        var futures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var s : testInputs) {
            futures.add(postJSON(API_PATH, new City(s)));
        }

        for (var f : futures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.CREATED, response.statusCode());
        }
    }

    @Test
    public void testPostCityDuplicate() throws Exception {
        final int EXPECTED_CODE = 402;
        var testInputs = new ArrayList<String>();
        testInputs.add("HCM");
        testInputs.add("HaNoi");
        testInputs.add("DaNang");
        testInputs.add("Hue");

        var setupFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var s : testInputs) {
            setupFutures.add(postJSON(API_PATH, new City(s)));
        }

        for (var f : setupFutures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.CREATED, response.statusCode());
        }

        var testFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var s : testInputs) {
            testFutures.add(postJSON(API_PATH, new City(s)));
        }

        for (var f : testFutures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(EXPECTED_CODE, apiRes.getStatusCode());
        }
    }

    @Test
    public void testPostCityInvalidId() throws Exception {
        final int EXPECTED_CODE = 460;
        var testInputs = new ArrayList<String>();
        testInputs.add("Sai Gon");
        testInputs.add("@SaiGon");
        testInputs.add("SaiGon@");
        testInputs.add("!#(lSaiGon@");

        var testFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var s : testInputs) {
            testFutures.add(postJSON(API_PATH, new City(s)));
        }

        for (var f : testFutures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(EXPECTED_CODE, apiRes.getStatusCode());
        }
    }


}
