package com.example.book2play.api.court;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.City;
import com.example.types.GenericAPIResult;
import org.junit.Assert;
import org.junit.Test;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CourtAPIPutTest extends APITestSetup {

    @Test
    public void testPutCourtSuccess() throws Exception {
        for (var cityId : cityIDs) {
            var city = new City(cityId);
            CITY.createCity(cityId);
            for (var sportCenterId : sportCenterIDs) {
                SPORT_CENTER.createCityCenter(sportCenterId, cityId);
                for (var courtId : courtIDs) {
                    COURT.createCityCenterCourt(courtId, cityId, sportCenterId);
                }
            }
        }

        var testFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var cityId : cityIDs) {
            for (var sportCenterId : sportCenterIDs) {
                for (var courtId : courtIDs) {
                    var query = new HashMap<String, List<String>>();
                    query.put("cityId", new ArrayList<>());
                    query.get("cityId").add(cityId);
                    query.put("sportCenterId", new ArrayList<>());
                    query.get("sportCenterId").add(sportCenterId);
                    query.put("oldCourtId", new ArrayList<>());
                    query.get("oldCourtId").add(courtId);
                    query.put("newCourtId", new ArrayList<>());
                    query.get("newCourtId").add("New" + courtId);
                    testFutures.add(asyncPut(COURT_API_PATH, query));
                }
            }
        }

        for (var f : testFutures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.ACCEPTED, response.statusCode());
        }
    }

    @Test
    public void testPutCourtExpectAllMySqlExceptions() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(404);
        testInputs.add(460);
        testInputs.add(461);
        testInputs.add(462);

        for (var code : testInputs) {
            COURT.setToBeThrown(code);
            var query = new HashMap<String, List<String>>();
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add("ArbitraryData");
            query.put("sportCenterId", new ArrayList<>());
            query.get("sportCenterId").add("ArbitraryData");
            query.put("oldCourtId", new ArrayList<>());
            query.get("oldCourtId").add("ArbitraryData");
            query.put("newCourtId", new ArrayList<>());
            query.get("newCourtId").add("ArbitraryData");

            var future = asyncPut(COURT_API_PATH, query);
            var response = future.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testPutCourtInvalidURLEncodedData() throws Exception {
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

        var oldCourtQueries = new ArrayList<List<String>>();
        oldCourtQueries.add(null);
        oldCourtQueries.add(new ArrayList<>());
        oldCourtQueries.add(new ArrayList<>());
        oldCourtQueries.get(2).add(courtIDs.get(0));
        oldCourtQueries.add(new ArrayList<>());
        oldCourtQueries.get(3).addAll(courtIDs);

        var newCourtQueries = new ArrayList<List<String>>();
        newCourtQueries.add(null);
        newCourtQueries.add(new ArrayList<>());
        newCourtQueries.add(new ArrayList<>());
        newCourtQueries.get(2).add(courtIDs.get(0));
        newCourtQueries.add(new ArrayList<>());
        newCourtQueries.get(3).addAll(courtIDs);

        var testFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var cityQuery : cityQueries) {
            for (var sportCenterQuery : sportCenterQueries) {
                for (var oldCourtQuery : oldCourtQueries) {
                    for (var newCourtQuery : newCourtQueries) {
                        if (cityQuery != null && cityQuery.size() == 1
                                && sportCenterQuery != null && sportCenterQuery.size() == 1
                                && oldCourtQuery != null && oldCourtQuery.size() == 1
                                && newCourtQuery != null && newCourtQuery.size() == 1
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
                        if (oldCourtQuery != null) {
                            query.put("oldCourtId", oldCourtQuery);
                        }
                        if (newCourtQuery != null) {
                            query.put("newCourtId", newCourtQuery);
                        }
                        testFutures.add(asyncPut(COURT_API_PATH, query));
                    }
                }
            }
        }

        for (var f : testFutures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
        }
    }
}
