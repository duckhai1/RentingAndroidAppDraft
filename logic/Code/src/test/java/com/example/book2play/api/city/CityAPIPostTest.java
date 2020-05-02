package com.example.book2play.api.city;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.Server;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.City;
import com.example.types.GenericAPIResult;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CityAPIPostTest extends APITestSetup {
    private final static URI CITY_API_PATH = URI.create("http://" + HOST + ':' + PORT + Server.CITY_BASE_URL);

    @Test
    public void testPostCitySuccess() throws Exception {
        for (var s : cityIDs) {
            var response = postJSON(CITY_API_PATH, null, new City(s));
            Assert.assertEquals(HTTPStatus.CREATED, response.statusCode());
        }
    }

    @Test
    public void testPostCityExpectAllMySqlExceptions() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(402);
        testInputs.add(460);

        for (var code : testInputs) {
            CITY.setToBeThrown(code);
            var response = postJSON(CITY_API_PATH, null, new City("ArbitraryData"));
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testPostCityInvalidJSONSchema() throws Exception {
        var testInputs = new ArrayList<Map<String, String>>();
        for (var id : cityIDs) {
            var data = new HashMap<String, String>();
            data.put("cityName", id);
            testInputs.add(data);
        }

        for (var data : testInputs) {
            var response = postJSON(CITY_API_PATH, null, data);
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
        }
    }
}
