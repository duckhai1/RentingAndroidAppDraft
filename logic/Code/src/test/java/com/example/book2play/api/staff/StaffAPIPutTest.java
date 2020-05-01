package com.example.book2play.api.staff;

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

public class StaffAPIPutTest extends APITestSetup {

    @Test
    public void testPutStaffSuccess() throws Exception {
        for (var cityId : cityIDs) {
            var city = new City(cityId);
            CITY.createCity(cityId);
            for (var sportCenterId : sportCenterIDs) {
                SPORT_CENTER.createCityCenter(sportCenterId, cityId);
                for (var staffId : staffIDs) {
                    STAFF.createStaff(staffId, cityId, sportCenterId);
                }
            }
        }

        var testFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var cityId : cityIDs) {
            for (var sportCenterId : sportCenterIDs) {
                for (var staffId : staffIDs) {
                    var query = new HashMap<String, List<String>>();
                    query.put("cityId", new ArrayList<>());
                    query.get("cityId").add(cityId);
                    query.put("sportCenterId", new ArrayList<>());
                    query.get("sportCenterId").add(sportCenterId);
                    query.put("oldStaffId", new ArrayList<>());
                    query.get("oldStaffId").add(staffId);
                    query.put("newStaffId", new ArrayList<>());
                    query.get("newStaffId").add("New" + staffId);
                    testFutures.add(asyncPut(STAFF_API_PATH, query));
                }
            }
        }

        for (var f : testFutures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.ACCEPTED, response.statusCode());
        }
    }

    @Test
    public void testPutStaffExpectAllMySqlExceptions() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(404);
        testInputs.add(460);
        testInputs.add(461);
        testInputs.add(462);

        for (var code : testInputs) {
            STAFF.setToBeThrown(code);
            var query = new HashMap<String, List<String>>();
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add("ArbitraryData");
            query.put("sportCenterId", new ArrayList<>());
            query.get("sportCenterId").add("ArbitraryData");
            query.put("oldStaffId", new ArrayList<>());
            query.get("oldStaffId").add("ArbitraryData");
            query.put("newStaffId", new ArrayList<>());
            query.get("newStaffId").add("ArbitraryData");

            var future = asyncPut(STAFF_API_PATH, query);
            var response = future.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testPutStaffInvalidURLEncodedData() throws Exception {
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

        var oldStaffQueries = new ArrayList<List<String>>();
        oldStaffQueries.add(null);
        oldStaffQueries.add(new ArrayList<>());
        oldStaffQueries.add(new ArrayList<>());
        oldStaffQueries.get(2).add(staffIDs.get(0));
        oldStaffQueries.add(new ArrayList<>());
        oldStaffQueries.get(3).addAll(staffIDs);

        var newStaffQueries = new ArrayList<List<String>>();
        newStaffQueries.add(null);
        newStaffQueries.add(new ArrayList<>());
        newStaffQueries.add(new ArrayList<>());
        newStaffQueries.get(2).add(staffIDs.get(0));
        newStaffQueries.add(new ArrayList<>());
        newStaffQueries.get(3).addAll(staffIDs);

        var testFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var cityQuery : cityQueries) {
            for (var sportCenterQuery : sportCenterQueries) {
                for (var oldStaffQuery : oldStaffQueries) {
                    for (var newStaffQuery : newStaffQueries) {
                        if (cityQuery != null && cityQuery.size() == 1
                                && sportCenterQuery != null && sportCenterQuery.size() == 1
                                && oldStaffQuery != null && oldStaffQuery.size() == 1
                                && newStaffQuery != null && newStaffQuery.size() == 1
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
                        if (oldStaffQuery != null) {
                            query.put("oldStaffId", oldStaffQuery);
                        }
                        if (newStaffQuery != null) {
                            query.put("newStaffId", newStaffQuery);
                        }
                        testFutures.add(asyncPut(STAFF_API_PATH, query));
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
