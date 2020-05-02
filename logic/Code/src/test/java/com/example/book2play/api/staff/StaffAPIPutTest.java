package com.example.book2play.api.staff;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.City;
import com.example.types.GenericAPIResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

                    var response = put(STAFF_API_PATH, null, query);
                    Assert.assertEquals(HTTPStatus.ACCEPTED, response.statusCode());
                }
            }
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

            var response = put(STAFF_API_PATH, null, query);
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testPutStaffInvalidURLEncodedData() throws Exception {
        PLAYER.createPlayer(playerIDs.get(0));

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

        var oldStaffQueries = new ArrayList<List<String>>();
        oldStaffQueries.add(new ArrayList<>());
        oldStaffQueries.add(new ArrayList<>());
        oldStaffQueries.get(1).add(staffIDs.get(0));
        oldStaffQueries.add(new ArrayList<>());
        oldStaffQueries.get(2).addAll(staffIDs);

        var newStaffQueries = new ArrayList<List<String>>();
        newStaffQueries.add(new ArrayList<>());
        newStaffQueries.add(new ArrayList<>());
        newStaffQueries.get(1).add(staffIDs.get(0));
        newStaffQueries.add(new ArrayList<>());
        newStaffQueries.get(2).addAll(staffIDs);

        for (var cityQuery : cityQueries) {
            for (var sportCenterQuery : sportCenterQueries) {
                for (var oldStaffQuery : oldStaffQueries) {
                    for (var newStaffQuery : newStaffQueries) {
                        if (cityQuery.size() == 1 && sportCenterQuery.size() == 1 && oldStaffQuery.size() == 1 && newStaffQuery.size() == 1) {
                            continue;
                        }

                        var query = new HashMap<String, List<String>>();
                        query.put("cityId", cityQuery);
                        query.put("sportCenterId", sportCenterQuery);
                        query.put("oldStaffId", oldStaffQuery);
                        query.put("newStaffId", newStaffQuery);

                        var response = put(STAFF_API_PATH, playerIDs.get(0), query);
                        Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
                    }
                }
            }
        }
    }
}
