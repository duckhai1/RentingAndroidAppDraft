package com.example.book2play.api.court;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.City;
import com.example.types.GenericAPIResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                    var response = put(COURT_API_PATH, null, query);
                    Assert.assertEquals(HTTPStatus.ACCEPTED, response.statusCode());
                }
            }
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

            var response = put(COURT_API_PATH, null, query);
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

        var oldCourtQueries = new ArrayList<List<String>>();
        oldCourtQueries.add(new ArrayList<>());
        oldCourtQueries.add(new ArrayList<>());
        oldCourtQueries.get(1).add(courtIDs.get(0));
        oldCourtQueries.add(new ArrayList<>());
        oldCourtQueries.get(2).addAll(courtIDs);

        var newCourtQueries = new ArrayList<List<String>>();
        newCourtQueries.add(new ArrayList<>());
        newCourtQueries.add(new ArrayList<>());
        newCourtQueries.get(1).add(courtIDs.get(0));
        newCourtQueries.add(new ArrayList<>());
        newCourtQueries.get(2).addAll(courtIDs);

        for (var cityQuery : cityQueries) {
            for (var sportCenterQuery : sportCenterQueries) {
                for (var oldCourtQuery : oldCourtQueries) {
                    for (var newCourtQuery : newCourtQueries) {
                        if (cityQuery.size() == 1 && sportCenterQuery.size() == 1 && oldCourtQuery.size() == 1 && newCourtQuery.size() == 1) {
                            continue;
                        }

                        var query = new HashMap<String, List<String>>();
                        query.put("cityId", cityQuery);
                        query.put("sportCenterId", sportCenterQuery);
                        query.put("oldCourtId", oldCourtQuery);
                        query.put("newCourtId", newCourtQuery);
                        var response = put(COURT_API_PATH, null, query);
                        Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
                    }
                }
            }
        }
    }
}
