package com.example.book2play.api.booking;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.Booking;
import com.example.types.GenericAPIResult;
import com.example.utils.TimeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingAPIPostTest extends APITestSetup {
    @Test
    public void testPostBookingSuccess() throws Exception {
        for (var playerID : playerIDs) {
            PLAYER.createPlayer(playerID);
        }

        for (var city : cityIDs) {
            CITY.createCity(city);
            for (var center : sportCenterIDs) {
                SPORT_CENTER.createCityCenter(center, city);
                STAFF.createStaff(staffIDs.get(0), city, center);
                for (var court : courtIDs) {
                    COURT.createCityCenterCourt(court, city, center);
                    for (var i = 0; i < playerIDs.size(); i++) {
                        var response = postJSON(BOOKING_API_PATH, playerIDs.get(i), new Booking(
                                null, null,
                                TimeUtils.getDate(7),
                                TimeUtils.getTime(9 + i, 0, 0),
                                TimeUtils.getTime(10 + i, 0, 0),
                                false, city, center, court, null
                        ));
                        Assert.assertEquals(HTTPStatus.CREATED, response.statusCode());
                    }
                }
            }
        }
    }

    @Test
    public void testPostBookingExpectAllMySqlExceptions() throws Exception {
        PLAYER.createPlayer(playerIDs.get(0));
        var testInputs = new ArrayList<Integer>();
        testInputs.add(407);
        testInputs.add(410);
        testInputs.add(412);
        testInputs.add(413);
        testInputs.add(460);
        testInputs.add(461);
        testInputs.add(462);
        testInputs.add(464);
        testInputs.add(465);
        testInputs.add(466);
        testInputs.add(467);
        testInputs.add(468);
        testInputs.add(469);

        for (var code : testInputs) {
            BOOKING.setToBeThrown(code);

            var response= postJSON(BOOKING_API_PATH, playerIDs.get(0), new Booking(
                    null, null,
                    TimeUtils.getDate(7),
                    TimeUtils.getTime(9, 0, 0),
                    TimeUtils.getTime(10, 0, 0),
                    false, "ArbitraryData", "ArbitraryData", "ArbitraryData", null
            ));
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }

    }

    @Test
    public void testPostBookingInvalidJSONSchema() throws Exception {
        PLAYER.createPlayer(playerIDs.get(0));
        var testInputs = new ArrayList<Map<String, String>>();
        for (var city : cityIDs) {
            for (var center : sportCenterIDs) {
                for (var court : courtIDs) {
                    for (var i = 0; i < playerIDs.size(); i++) {
                        var data = new HashMap<String, String>();
                        data.put("date", city);
                        data.put("start", city);
                        data.put("end", city);
                        data.put("city", city);
                        data.put("sportCenter", city);
                        data.put("court", city);
                        testInputs.add(data);
                    }
                }
            }
        }

        for (var data : testInputs) {
            var response = postJSON(BOOKING_API_PATH, playerIDs.get(0), data);
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
        }
    }
}
