package com.example.book2play.api.sport_center;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.types.GenericAPIResult;
import org.junit.Assert;
import org.junit.Test;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SportCenterAPIPutTest extends APITestSetup {
    @Test
    public void testPutChangeSportCenterId() throws Exception {
        for (var cityId : cityIDs) {
            CITY.createCity(cityId);
            for (var sportCenterId : sportCenterIDs) {
                SPORT_CENTER.createCityCenter(sportCenterId, cityId);
            }
        }

        for (var sp : DS.getSportCenters()) {
            LOG.info(sp.getSportCenterId() + sp.getCityId());
        }

        var testFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var cityId : cityIDs) {
            for (var sportCenterId : sportCenterIDs) {
                var query = new HashMap<String, List<String>>();
                query.put("cityId", new ArrayList<>());
                query.get("cityId").add(cityId);
                query.put("newSportCenterId", new ArrayList<>());
                query.get("newSportCenterId").add("New" + sportCenterId);
                query.put("oldSportCenterId", new ArrayList<>());
                query.get("oldSportCenterId").add(sportCenterId);

                var response = put(SPORT_CENTER_API_PATH, null, query);
                Assert.assertEquals(HTTPStatus.ACCEPTED, response.statusCode());
            }
        }
    }

    @Test
    public void testPutChangeSportCenterIdExpectAllMySqlExceptions() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(403);
        testInputs.add(460);
        testInputs.add(461);

        for (var code : testInputs) {
            SPORT_CENTER.setToBeThrown(code);
            var query = new HashMap<String, List<String>>();
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add("ArbitraryData");
            query.put("oldSportCenterId", new ArrayList<>());
            query.get("oldSportCenterId").add("ArbitraryData");
            query.put("newSportCenterId", new ArrayList<>());
            query.get("newSportCenterId").add("ArbitraryData");

            var response = put(SPORT_CENTER_API_PATH, null, query);
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testPutChangeSportCenterIdInvalidURLEncodedData() throws Exception {
        var cityQueries = new ArrayList<List<String>>();
        cityQueries.add(new ArrayList<>());
        cityQueries.add(new ArrayList<>());
        cityQueries.get(1).add(cityIDs.get(0));
        cityQueries.add(new ArrayList<>());
        cityQueries.get(2).addAll(cityIDs);

        var oldSportCenterQueries = new ArrayList<List<String>>();
        oldSportCenterQueries.add(new ArrayList<>());
        oldSportCenterQueries.add(new ArrayList<>());
        oldSportCenterQueries.get(1).add(sportCenterIDs.get(0));
        oldSportCenterQueries.add(new ArrayList<>());
        oldSportCenterQueries.get(2).addAll(sportCenterIDs);

        var newSportCenterQueries = new ArrayList<List<String>>();
        newSportCenterQueries.add(new ArrayList<>());
        newSportCenterQueries.add(new ArrayList<>());
        newSportCenterQueries.get(1).add(sportCenterIDs.get(0));
        newSportCenterQueries.add(new ArrayList<>());
        newSportCenterQueries.get(2).addAll(sportCenterIDs);

        for (var cityQuery : cityQueries) {
            for (var oldSportCenterQuery : oldSportCenterQueries) {
                for (var newSportCenterQuery : newSportCenterQueries) {
                    if (cityQuery.size() == 1 && oldSportCenterQuery.size() == 1 && newSportCenterQuery.size() == 1) {
                        continue;
                    }

                    var query = new HashMap<String, List<String>>();
                    query.put("cityId", cityQuery);
                    query.put("sportCenterId", oldSportCenterQuery);
                    query.put("sportCenterId", newSportCenterQuery);

                    var response = put(SPORT_CENTER_API_PATH, null, query);
                    Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
                }
            }
        }
    }
}
