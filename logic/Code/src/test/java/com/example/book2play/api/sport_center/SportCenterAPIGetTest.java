package com.example.book2play.api.sport_center;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.City;
import com.example.book2play.types.SportCenter;
import com.example.types.GenericAPIResult;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;

import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SportCenterAPIGetTest extends APITestSetup {
    @Test
    public void testGetCitySportCentersSuccess() throws Exception {
        var expected = new HashMap<City, HashSet<SportCenter>>();
        for (var cityId : cityIDs) {
            var city = new City(cityId);
            CITY.createCity(cityId);
            expected.put(city, new HashSet<>());
            for (var sportCenterId : sportCenterIDs) {
                SPORT_CENTER.createCityCenter(sportCenterId, cityId);
                expected.get(city).add(new SportCenter(sportCenterId, cityId));
            }
        }
        PLAYER.createPlayer(playerIDs.get(0));

        for (var city : expected.keySet()) {
            var query = new HashMap<String, List<String>>();
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add(city.getCityId());

            var responseFuture = asyncGetJSON(SPORT_CENTER_API_PATH, playerIDs.get(0), query);
            var response = responseFuture.get();
            Assert.assertEquals(HTTPStatus.OK, response.statusCode());

            Set<SportCenter> outSportCenters = GSON.fromJson(
                    response.body(),
                    new TypeToken<HashSet<SportCenter>>() {
                    }.getType()
            );
            Assert.assertEquals(expected.get(city), outSportCenters);
        }
    }

    @Test
    public void testGetCitySportCentersEmpty() throws Exception {
        for (var cityId : cityIDs) {
            var city = new City(cityId);
            CITY.createCity(cityId);
        }
        PLAYER.createPlayer(playerIDs.get(0));

        for (var cityId : cityIDs) {
            var query = new HashMap<String, List<String>>();
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add(cityId);

            var responseFuture = asyncGetJSON(SPORT_CENTER_API_PATH, playerIDs.get(0), query);
            var response = responseFuture.get();
            Assert.assertEquals(HTTPStatus.OK, response.statusCode());

            Set<Integer> outSportCenters = GSON.fromJson(
                    response.body(),
                    new TypeToken<HashSet<SportCenter>>() {
                    }.getType()
            );
            Assert.assertEquals(Collections.emptySet(), outSportCenters);
        }

    }

    @Test
    public void testGetCitySportCentersExpectAllMySqlException() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(460);
        PLAYER.createPlayer(playerIDs.get(0));

        for (var code : testInputs) {
            SPORT_CENTER.setToBeThrown(code);

            var query = new HashMap<String, List<String>>();
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add("ArbitraryData");

            var responseFuture = asyncGetJSON(SPORT_CENTER_API_PATH, playerIDs.get(0), query);
            var response = responseFuture.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testGetSportCentersInvalidURLEncodedData() throws Exception {
        var cityQueries = new ArrayList<List<String>>();
        cityQueries.add(null);
        cityQueries.add(new ArrayList<>());
        cityQueries.add(new ArrayList<>());
        cityQueries.get(2).addAll(cityIDs);

        var testFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var cityQuery : cityQueries) {
            var query = new HashMap<String, List<String>>();
            if (cityQuery != null) {
                query.put("cityId", cityQuery);
            }
            testFutures.add(asyncPut(COURT_API_PATH, null, query));
        }

        for (var f : testFutures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
        }
    }
}
