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

import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class CourtAPIGetTest extends APITestSetup {

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

        for (var city : expected.keySet()) {
            var query = new HashMap<String, List<String>>();
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add(city.getCityId());

            var responseFuture = asyncGetJSON(COURT_API_PATH, query);
            var response = responseFuture.get();
            Assert.assertEquals(HTTPStatus.OK, response.statusCode());

            Set<Integer> outCourts = GSON.fromJson(
                    response.body(),
                    new TypeToken<HashSet<Court>>() {
                    }.getType()
            );
            Assert.assertEquals(expected.get(city), outCourts);
        }
    }

    @Test
    public void testGetCityCourtsEmpty() throws Exception {
        for (var cityId : cityIDs) {
            CITY.createCity(cityId);
        }

        for (var cityId : cityIDs) {
            var query = new HashMap<String, List<String>>();
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add(cityId);

            var responseFuture = asyncGetJSON(COURT_API_PATH, query);
            var response = responseFuture.get();
            Assert.assertEquals(HTTPStatus.OK, response.statusCode());

            Set<Integer> outCourts = GSON.fromJson(
                    response.body(),
                    new TypeToken<HashSet<Court>>() {
                    }.getType()
            );
            Assert.assertEquals(Collections.EMPTY_SET, outCourts);
        }
    }

    @Test
    public void testGetCityCourtsExpectAllMySqlExceptions() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(460);

        for (var code : testInputs) {
            COURT.setToBeThrown(code);

            var query = new HashMap<String, List<String>>();
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add("ArbitraryData");

            var responseFuture = asyncGetJSON(COURT_API_PATH, query);
            var response = responseFuture.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testGetCityCourtsInvalidURLEncodedData() throws Exception {
        var testInputs = new ArrayList<HashMap<String, List<String>>>();

        testInputs.add(new HashMap<>());

        testInputs.add(new HashMap<>());
        testInputs.get(1).put("cityId", new ArrayList<>());

        testInputs.add(new HashMap<>());
        testInputs.get(2).put("cityId", new ArrayList<>());
        testInputs.get(2).get("cityId").add(cityIDs.get(0));
        testInputs.get(2).get("cityId").add(cityIDs.get(1));

        testInputs.add(new HashMap<>());
        testInputs.get(3).put("cityName", new ArrayList<>());
        testInputs.get(3).get("cityName").add(cityIDs.get(0));

        var testFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var data : testInputs) {
            testFutures.add(asyncGetJSON(COURT_API_PATH, data));
        }

        for (var f : testFutures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
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

        for (var sportCenter : expected.keySet()) {
            var query = new HashMap<String, List<String>>();
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add(sportCenter.getCityId());
            query.put("sportCenterId", new ArrayList<>());
            query.get("sportCenterId").add(sportCenter.getSportCenterId());

            CompletableFuture<HttpResponse<String>> responseFuture = asyncGetJSON(COURT_API_PATH, query);
            HttpResponse<String> response = responseFuture.get();
            Assert.assertEquals(HTTPStatus.OK, response.statusCode());

            Set<Integer> outCourts = GSON.fromJson(
                    response.body(),
                    new TypeToken<HashSet<Court>>() {
                    }.getType()
            );
            Assert.assertEquals(expected.get(sportCenter), outCourts);
        }
    }

    @Test
    public void testGetSportCenterCourtsEmpty() throws Exception {
        var sportCenters = new ArrayList<SportCenter>();
        for (var cityId : cityIDs) {
            CITY.createCity(cityId);
            for (var sportCenterId : sportCenterIDs) {
                SPORT_CENTER.createCityCenter(sportCenterId, cityId);
                sportCenters.add(new SportCenter(sportCenterId, cityId));
            }
        }

        for (var sportCenter : sportCenters) {
            var query = new HashMap<String, List<String>>();
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add(sportCenter.getCityId());
            query.put("sportCenterId", new ArrayList<>());
            query.get("sportCenterId").add(sportCenter.getSportCenterId());

            CompletableFuture<HttpResponse<String>> responseFuture = asyncGetJSON(COURT_API_PATH, query);
            HttpResponse<String> response = responseFuture.get();
            Assert.assertEquals(HTTPStatus.OK, response.statusCode());

            Set<Integer> outCourts = GSON.fromJson(
                    response.body(),
                    new TypeToken<HashSet<Court>>() {
                    }.getType()
            );
            Assert.assertEquals(Collections.EMPTY_SET, outCourts);
        }
    }

    @Test
    public void testGetSportCenterCourtsExpectAllMySqlExceptions() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(460);
        testInputs.add(461);

        for (var code : testInputs) {
            COURT.setToBeThrown(code);

            var query = new HashMap<String, List<String>>();
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add("ArbitraryData");
            query.put("sportCenterId", new ArrayList<>());
            query.get("sportCenterId").add("ArbitraryData");

            var responseFuture = asyncGetJSON(COURT_API_PATH, query);
            var response = responseFuture.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testGetSportCenterCourtsInvalidURLEncodedData() throws Exception {
        var cityQueries = new ArrayList<List<String>>();
        cityQueries.add(null);
        cityQueries.add(new ArrayList<>());
        cityQueries.add(new ArrayList<>());
        cityQueries.get(2).add(cityIDs.get(0));
        cityQueries.add(new ArrayList<>());
        cityQueries.get(3).addAll(cityIDs);

        var sportCenterQueries = new ArrayList<List<String>>();
        sportCenterQueries.add(null);
        sportCenterQueries.add(new ArrayList<>());
        sportCenterQueries.add(new ArrayList<>());
        sportCenterQueries.get(2).add(sportCenterIDs.get(0));
        sportCenterQueries.add(new ArrayList<>());
        sportCenterQueries.get(3).addAll(sportCenterIDs);

        var testFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var cityQuery : cityQueries) {
            for (var sportCenterQuery : sportCenterQueries) {
                if (cityQuery != null && cityQuery.size() == 1
                        && sportCenterQuery != null && sportCenterQuery.size() == 1
                ) {
                    continue;
                }

                if (cityQuery != null && cityQuery.size() == 1
                        && sportCenterQuery == null
                ) {
                    continue;
                }
                var query = new HashMap<String, List<String>>();
                if (cityQuery != null) {
                    query.put("cityId", cityQuery);
                }
                if (sportCenterQuery != null) {
                    query.put("sportCenterId", sportCenterQuery);
                }
                testFutures.add(asyncPut(COURT_API_PATH, query));
            }
        }

        for (var f : testFutures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
        }
    }
}
