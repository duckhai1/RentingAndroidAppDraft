package com.example.book2play.api.staff;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.Staff;
import com.example.types.GenericAPIResult;
import org.junit.Assert;
import org.junit.Test;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class StaffAPIPostTest extends APITestSetup {

    @Test
    public void testPostStaffSuccess() throws Exception {
        var futures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var cityId : cityIDs) {
            CITY.createCity(cityId);
            for (var sportCenterId : sportCenterIDs) {
                SPORT_CENTER.createCityCenter(sportCenterId, cityId);
                for (var staffId : staffIDs) {
                    futures.add(asyncPostJSON(STAFF_API_PATH, staffId, new Staff(staffId, cityId, sportCenterId)));
                }
            }
        }

        for (var f : futures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.CREATED, response.statusCode());
        }
    }

    @Test
    public void testPostStaffExpectAllMySqlExceptions() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(406);
        testInputs.add(460);
        testInputs.add(461);
        testInputs.add(463);

        for (var code : testInputs) {
            STAFF.setToBeThrown(code);
            var future = asyncPostJSON(STAFF_API_PATH, "ArbitraryData", new Staff("ArbitraryData", "ArbitraryData", "ArbitraryData"));
            var response = future.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testPostStaffInvalidJSONFormat() throws Exception {
        var testInputs = new ArrayList<Map<String, String>>();
        for (var cityId : cityIDs) {
            for (var sportCenterId : sportCenterIDs) {
                for (var staffId : staffIDs) {
                    var data = new HashMap<String, String>();
                    data.put("cityName", cityId);
                    data.put("sportCenterName", sportCenterId);
                    data.put("staffName", staffId);
                    testInputs.add(data);
                }
            }
        }

        var testFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var data : testInputs) {
            testFutures.add(asyncPostJSON(STAFF_API_PATH, "ArbitraryData", data));
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
