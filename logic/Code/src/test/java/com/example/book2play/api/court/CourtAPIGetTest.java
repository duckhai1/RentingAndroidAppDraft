package com.example.book2play.api.court;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.City;
import com.example.book2play.types.Court;
import com.example.book2play.types.SportCenter;
import com.example.types.GenericAPIResult;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class CourtAPIGetTest extends APITestSetup {

    @Test
    public void testGetCourtsInvalidURLEncodedData() throws Exception {
        var cityQueries = new ArrayList<List<String>>();
        cityQueries.add(new ArrayList<>());
        cityQueries.add(new ArrayList<>());
        cityQueries.get(1).add(cityIDs.get(0));
        cityQueries.add(new ArrayList<>());
        cityQueries.get(2).addAll(cityIDs);

        var sportCenterQueries = new ArrayList<List<String>>();
        sportCenterQueries.add(new ArrayList<>());
        sportCenterQueries.add(new ArrayList<>());
        sportCenterQueries.get(1).add(sportCenterIDs.get(0));
        sportCenterQueries.add(new ArrayList<>());
        sportCenterQueries.get(2).addAll(sportCenterIDs);

        for (var cityQuery : cityQueries) {
            for (var sportCenterQuery : sportCenterQueries) {
                if (cityQuery.size() == 1 && sportCenterQuery.size() == 1) {
                    continue;
                }

                if (cityQuery.size() == 1 && sportCenterQuery.size() == 0) {
                    continue;
                }

                var query = new HashMap<String, List<String>>();
                query.put("cityId", cityQuery);
                query.put("sportCenterId", sportCenterQuery);
                var response = put(COURT_API_PATH, null, query);
                Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
            }
        }
    }

    @Test
    public void testGetCityCourtsSuccess() throws Exception {
        var expected = new HashMap<City, HashSet<Court>>();
        for (var cityId : cityIDs) {
            var city = new City(cityId);
            CITY.createCity(cityId);
            expected.put(city, new HashSet<>());
            for (var sportCenterId : sportCenterIDs) {
                SPORT_CENTER.createCityCenter(sportCenterId, cityId);
                for (var courtId : courtIDs) {
                    COURT.createCityCenterCourt(courtId, cityId, sportCenterId);
                    expected.get(city).add(new Court(courtId, cityId, sportCenterId));
                }
            }
        }
        PLAYER.createPlayer(playerIDs.get(0));


        for (var city : expected.keySet()) {
            var query = new HashMap<String, List<String>>();
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add(city.getCityId());

            var response = getJSON(COURT_API_PATH, playerIDs.get(0), query);
            Assert.assertEquals(HTTPStatus.OK, response.statusCode());

            Set<Court> outCourts = GSON.fromJson(
                    response.body(),
                    new TypeToken<HashSet<Court>>() {
                    }.getType()
            );
            Assert.assertEquals(expected.get(city), outCourts);
        }
    }

    @Test
    public void testGetCityCourtsExpectAllMySqlExceptions() throws Exception {
        PLAYER.createPlayer(playerIDs.get(0));
        var testInputs = new ArrayList<Integer>();
        testInputs.add(460);

        for (var code : testInputs) {
            COURT.setToBeThrown(code);

            var query = new HashMap<String, List<String>>();
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add("ArbitraryData");

            var response = getJSON(COURT_API_PATH, playerIDs.get(0), query);
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testGetSportCenterCourtsSuccess() throws Exception {
        var expected = new HashMap<SportCenter, HashSet<Court>>();
        for (var cityId : cityIDs) {
            CITY.createCity(cityId);
            for (var sportCenterId : sportCenterIDs) {
                var sportCenter = new SportCenter(sportCenterId, cityId);
                expected.put(sportCenter, new HashSet<>());
                SPORT_CENTER.createCityCenter(sportCenterId, cityId);
                for (var courtId : courtIDs) {
                    COURT.createCityCenterCourt(courtId, cityId, sportCenterId);
                    expected.get(sportCenter).add(new Court(courtId, cityId, sportCenterId));
                }
            }
        }
        PLAYER.createPlayer(playerIDs.get(0));


        for (var sportCenter : expected.keySet()) {
            var query = new HashMap<String, List<String>>();
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add(sportCenter.getCityId());
            query.put("sportCenterId", new ArrayList<>());
            query.get("sportCenterId").add(sportCenter.getSportCenterId());

            var response = getJSON(COURT_API_PATH, playerIDs.get(0), query);
            Assert.assertEquals(HTTPStatus.OK, response.statusCode());

            Set<Court> outCourts = GSON.fromJson(
                    response.body(),
                    new TypeToken<HashSet<Court>>() {
                    }.getType()
            );
            Assert.assertEquals(expected.get(sportCenter), outCourts);
        }
    }

    @Test
    public void testGetSportCenterCourtsExpectAllMySqlExceptions() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(460);
        testInputs.add(461);
        PLAYER.createPlayer(playerIDs.get(0));


        for (var code : testInputs) {
            COURT.setToBeThrown(code);

            var query = new HashMap<String, List<String>>();
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add("ArbitraryData");
            query.put("sportCenterId", new ArrayList<>());
            query.get("sportCenterId").add("ArbitraryData");

            var response = getJSON(COURT_API_PATH, playerIDs.get(0), query);
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }
}
