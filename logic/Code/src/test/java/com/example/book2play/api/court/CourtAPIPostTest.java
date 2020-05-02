package com.example.book2play.api.court;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.Court;
import com.example.types.GenericAPIResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CourtAPIPostTest extends APITestSetup {

    @Test
    public void testPostCourtSuccess() throws Exception {
        for (var cityId : cityIDs) {
            CITY.createCity(cityId);
            for (var sportCenterId : sportCenterIDs) {
                SPORT_CENTER.createCityCenter(sportCenterId, cityId);
                for (var courtId : courtIDs) {
                    var response = postJSON(COURT_API_PATH, null, new Court(courtId, cityId, sportCenterId));
                    Assert.assertEquals(HTTPStatus.CREATED, response.statusCode());
                }
            }
        }
    }

    @Test
    public void testPostCourtExpectAllMySqlExceptions() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(404);
        testInputs.add(460);
        testInputs.add(461);
        testInputs.add(462);

        for (var code : testInputs) {
            COURT.setToBeThrown(code);
            var response = postJSON(COURT_API_PATH, null, new Court("ArbitraryData", "ArbitraryData", "ArbitraryData"));
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testPostCourtInvalidJSONSchema() throws Exception {
        var testInputs = new ArrayList<Map<String, String>>();
        for (var cityId : cityIDs) {
            for (var sportCenterId : sportCenterIDs) {
                for (var courtId : courtIDs) {
                    var data = new HashMap<String, String>();
                    data.put("cityName", cityId);
                    data.put("sportCenterName", sportCenterId);
                    data.put("courtName", courtId);
                    testInputs.add(data);
                }
            }
        }

        for (var data : testInputs) {
            var response = postJSON(COURT_API_PATH, null, data);
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
        }
    }
}
