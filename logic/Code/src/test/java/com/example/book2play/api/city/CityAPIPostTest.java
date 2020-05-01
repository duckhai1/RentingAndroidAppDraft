package com.example.book2play.api.city;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.Server;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.City;
import com.example.types.GenericAPIResult;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CityAPIPostTest extends APITestSetup {
    private final static URI CITY_API_PATH = URI.create("http://" + HOST + ':' + PORT + Server.CITY_BASE_URL);

    @Test
    public void testPostCitySuccess() throws Exception {
        var futures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var s : cityIDs) {
            futures.add(asyncPostJSON(CITY_API_PATH, new City(s)));
        }

        for (var f : futures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.CREATED, response.statusCode());
        }
    }

    @Test
    public void testPostCityExpectAllMySqlExceptions() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(402);
        testInputs.add(460);

        for (var code : testInputs) {
            CITY.setToBeThrown(code);
            var future = asyncPostJSON(CITY_API_PATH, new City("ArbitraryData"));
            var response = future.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testPostCityInvalidJSONFormat() throws Exception {
        var testInputs = new ArrayList<Map<String, String>>();
        for (var id : cityIDs) {
            var data = new HashMap<String, String>();
            data.put("cityName", id);
            testInputs.add(data);
        }

        var testFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var data : testInputs) {
            testFutures.add(asyncPostJSON(CITY_API_PATH, data));
        }

        for (var f : testFutures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
        }
    }
}
