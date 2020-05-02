package com.example.book2play.api.sport_center;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.City;
import com.example.book2play.types.SportCenter;
import com.example.types.GenericAPIResult;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

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

            var response = getJSON(SPORT_CENTER_API_PATH, playerIDs.get(0), query);
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
    public void testGetCitySportCentersExpectAllMySqlException() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(460);
        PLAYER.createPlayer(playerIDs.get(0));

        for (var code : testInputs) {
            SPORT_CENTER.setToBeThrown(code);

            var query = new HashMap<String, List<String>>();
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add("ArbitraryData");

            var response = getJSON(SPORT_CENTER_API_PATH, playerIDs.get(0), query);
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
        cityQueries.add(new ArrayList<>());
        cityQueries.add(new ArrayList<>());
        cityQueries.get(1).addAll(cityIDs);

        for (var cityQuery : cityQueries) {
            var query = new HashMap<String, List<String>>();
            query.put("cityId", cityQuery);
            var response = put(SPORT_CENTER_API_PATH, null, query);
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
        }
    }
}
