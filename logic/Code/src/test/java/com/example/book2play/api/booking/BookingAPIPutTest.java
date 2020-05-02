package com.example.book2play.api.booking;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.types.GenericAPIResult;
import com.example.utils.TimeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookingAPIPutTest extends APITestSetup {
    @Test
    public void testPutBookingSuccess() throws Exception {
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
                        BOOKING.createBooking(
                                TimeUtils.getTimestamp(),
                                TimeUtils.getDate(7),
                                TimeUtils.getTime(9 + i, 0, 0),
                                TimeUtils.getTime(10 + i, 0, 0),
                                city, center, court, playerIDs.get(i)
                        );
                    }
                }
            }
        }

        for (var b : DS.getBookings()) {
            var query = new HashMap<String, List<String>>();
            query.put("status", new ArrayList<>());
            query.get("status").add("true");
            query.put("bookingId", new ArrayList<>());
            query.get("bookingId").add(b.getBookingId());
            query.put("sportCenterId", new ArrayList<>());
            query.get("sportCenterId").add(b.getSportCenterId());
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add(b.getCityId());
            var response = put(BOOKING_API_PATH, staffIDs.get(0), query);
            Assert.assertEquals(HTTPStatus.ACCEPTED, response.statusCode());
        }
    }

    @Test
    public void testPutBookingExpectAllMySqlExceptions() throws Exception {
        CITY.createCity(cityIDs.get(0));
        SPORT_CENTER.createCityCenter(sportCenterIDs.get(0), cityIDs.get(0));
        STAFF.createStaff(staffIDs.get(0), cityIDs.get(0), sportCenterIDs.get(0));
        var testInputs = new ArrayList<Integer>();
        testInputs.add(401);
        testInputs.add(460);
        testInputs.add(461);
        testInputs.add(463);
        testInputs.add(465);

        for (var code : testInputs) {
            BOOKING.setToBeThrown(code);

            var query = new HashMap<String, List<String>>();
            query.put("status", new ArrayList<>());
            query.get("status").add("true");
            query.put("bookingId", new ArrayList<>());
            query.get("bookingId").add("ArbitraryData");
            query.put("sportCenterId", new ArrayList<>());
            query.get("sportCenterId").add(sportCenterIDs.get(0));
            query.put("cityId", new ArrayList<>());
            query.get("cityId").add(cityIDs.get(0));

            var response = put(BOOKING_API_PATH, staffIDs.get(0), query);
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testPutBookingInvalidURLEncodedData() throws Exception {
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

        var bookingQueries = new ArrayList<List<String>>();
        bookingQueries.add(new ArrayList<>());
        bookingQueries.add(new ArrayList<>());
        bookingQueries.get(1).add(sportCenterIDs.get(0));
        bookingQueries.add(new ArrayList<>());
        bookingQueries.get(2).addAll(sportCenterIDs);

        var statusQueries = new ArrayList<List<String>>();
        statusQueries.add(new ArrayList<>());
        statusQueries.add(new ArrayList<>());
        statusQueries.get(1).add("true");
        statusQueries.add(new ArrayList<>());
        statusQueries.get(2).add("true");
        statusQueries.get(2).add("true");

        for (var cityQuery : cityQueries) {
            for (var sportCenterQuery : sportCenterQueries) {
                for (var bookingQuery : bookingQueries) {
                    for (var statusQuery : statusQueries) {
                        if (cityQuery.size() == 1 && sportCenterQuery.size() == 1 && bookingQuery.size() == 1 && statusQuery.size() == 1) {
                            continue;
                        }

                        var query = new HashMap<String, List<String>>();
                        query.put("cityId", cityQuery);
                        query.put("sportCenterId", sportCenterQuery);
                        query.put("bookingId", bookingQuery);
                        query.put("status", statusQuery);

                        var response = put(BOOKING_API_PATH, "ArbitraryData", query);
                        Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
                    }
                }
            }
        }
    }
}
