package com.example.book2play.api.staff;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.Staff;
import com.example.types.GenericAPIResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StaffAPIPostTest extends APITestSetup {

    @Test
    public void testPostStaffSuccess() throws Exception {
        for (var cityId : cityIDs) {
            CITY.createCity(cityId);
            for (var sportCenterId : sportCenterIDs) {
                SPORT_CENTER.createCityCenter(sportCenterId, cityId);
                for (var staffId : staffIDs) {
                    var response = postJSON(STAFF_API_PATH, staffId, new Staff(staffId, cityId, sportCenterId));
                    Assert.assertEquals(HTTPStatus.CREATED, response.statusCode());
                }
            }
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
            var response = postJSON(STAFF_API_PATH, "ArbitraryData", new Staff("ArbitraryData", "ArbitraryData", "ArbitraryData"));
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testPostStaffInvalidJSONSchema() throws Exception {
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

        for (var data : testInputs) {
            var response = postJSON(STAFF_API_PATH, "ArbitraryData", data);
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
        }
    }
}
