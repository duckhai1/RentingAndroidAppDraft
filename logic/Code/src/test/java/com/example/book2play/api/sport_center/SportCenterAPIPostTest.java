package com.example.book2play.api.sport_center;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.SportCenter;
import com.example.types.GenericAPIResult;
import org.junit.Assert;
import org.junit.Test;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SportCenterAPIPostTest extends APITestSetup {
    @Test
    public void testPostSportCenterSuccess() throws Exception {
        var futures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var cityId : cityIDs) {
            CITY.createCity(cityId);
            for (var sportCenterId : sportCenterIDs) {
                futures.add(asyncPostJSON(SPORT_CENTER_API_PATH, new SportCenter(sportCenterId, cityId)));
            }
        }

        for (var f : futures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.CREATED, response.statusCode());
        }
    }

    @Test
    public void testGetCitySportCentersExpectAllMySqlException() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(403);
        testInputs.add(460);
        testInputs.add(461);

        for (var code : testInputs) {
            SPORT_CENTER.setToBeThrown(code);
            var future = asyncPostJSON(SPORT_CENTER_API_PATH, new SportCenter("ArbitraryData", "ArbitraryData"));
            var response = future.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testGetCitySportCentersInvalidJSONFormat() throws Exception {
        var testInputs = new ArrayList<Map<String, String>>();
        for (var cityId : cityIDs) {
            for (var sportCenterId : sportCenterIDs) {
                var data = new HashMap<String, String>();
                data.put("cityName", cityId);
                data.put("sportCenterName", sportCenterId);
                testInputs.add(data);
            }
        }

        var testFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var data : testInputs) {
            testFutures.add(asyncPostJSON(SPORT_CENTER_API_PATH, data));
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
