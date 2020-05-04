package com.example.book2play.api.sport_center;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.SportCenter;
import com.example.types.GenericAPIResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SportCenterAPIPostTest extends APITestSetup {
    @Test
    public void testPostSportCenterSuccess() throws Exception {
        for (var cityId : cityIDs) {
            CITY.createCity(cityId);
            for (var sportCenterId : sportCenterIDs) {
                var response = postJSON(SPORT_CENTER_API_PATH, null, new SportCenter(sportCenterId, cityId));
                Assert.assertEquals(HTTPStatus.CREATED, response.statusCode());
            }
        }
    }

    @Test
    public void testGetCitySportCentersExpectAllMySqlException() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(403);
        testInputs.add(460);
        testInputs.add(461);

        for (var code : testInputs) {
            SPORT_CENTER.setToBeThrown(code);
            var response = postJSON(SPORT_CENTER_API_PATH, null, new SportCenter("ArbitraryData", "ArbitraryData"));
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testGetCitySportCentersInvalidJSONSchema() throws Exception {
        var testInputs = new ArrayList<Map<String, String>>();
        for (var cityId : cityIDs) {
            for (var sportCenterId : sportCenterIDs) {
                var data = new HashMap<String, String>();
                data.put("cityName", cityId);
                data.put("sportCenterName", sportCenterId);
                testInputs.add(data);
            }
        }

        for (var data : testInputs) {
            var response = postJSON(SPORT_CENTER_API_PATH, null, data);
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
        }
    }
}
