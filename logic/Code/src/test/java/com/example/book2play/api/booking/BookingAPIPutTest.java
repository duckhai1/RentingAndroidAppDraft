package com.example.book2play.api.booking;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.types.GenericAPIResult;
import com.example.utils.TimeUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BookingAPIPutTest extends APITestSetup {
    @Before
    public void setupLotsOfBookings() throws Exception {
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
    }

    @Test
    public void testPutBookingSuccess() throws Exception {
        var testFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
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
            testFutures.add(asyncPut(BOOKING_API_PATH, staffIDs.get(0), query));
        }

        for (var f : testFutures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.ACCEPTED, response.statusCode());
        }
    }

    @Test
    public void testPutBookingExpectAllMySqlExceptions() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(401);
        testInputs.add(460);
        testInputs.add(461);
        testInputs.add(463);
        testInputs.add(465);

        for (var code : testInputs) {
            BOOKING.setToBeThrown(code);
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

                var responseFuture = asyncPut(BOOKING_API_PATH, staffIDs.get(0), query);
                var response = responseFuture.get();
                Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

                var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
                Assert.assertNotEquals(null, apiRes);
                Assert.assertTrue(apiRes.isHasError());
                Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
            }

        }

    }

    @Test
    public void testPutBookingInvalidURLEncodedData() throws Exception {
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

        var bookingQueries = new ArrayList<List<String>>();
        bookingQueries.add(null);
        bookingQueries.add(new ArrayList<>());
        bookingQueries.add(new ArrayList<>());
        bookingQueries.get(2).add(sportCenterIDs.get(0));
        bookingQueries.add(new ArrayList<>());
        bookingQueries.get(3).addAll(sportCenterIDs);

        var statusQueries = new ArrayList<List<String>>();
        statusQueries.add(null);
        statusQueries.add(new ArrayList<>());
        statusQueries.add(new ArrayList<>());
        statusQueries.get(2).add("true");
        statusQueries.add(new ArrayList<>());
        statusQueries.get(3).add("true");
        statusQueries.get(3).add("true");
        statusQueries.get(3).add("true");

        for (var cityQuery : cityQueries) {
            for (var sportCenterQuery : sportCenterQueries) {
                for (var bookingQuery : bookingQueries) {
                    for (var statusQuery : statusQueries) {
                        if (cityQuery != null && cityQuery.size() == 1
                                && sportCenterQuery != null && sportCenterQuery.size() == 1
                                && bookingQuery != null && bookingQuery.size() == 1
                                && statusQuery != null && statusQuery.size() == 1) {
                            continue;
                        }

                        var query = new HashMap<String, List<String>>();
                        if (cityQuery != null) {
                            query.put("cityId", cityQuery);
                        }
                        if (sportCenterQuery != null) {
                            query.put("sportCenterId", sportCenterQuery);
                        }
                        if (bookingQuery != null) {
                            query.put("bookingId", bookingQuery);
                        }
                        if (statusQuery != null) {
                            query.put("status", statusQuery);
                        }
                        var responseFuture = asyncPut(BOOKING_API_PATH, "ArbitraryData", query);
                        var response = responseFuture.get();
                        Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
                    }
                }
            }
        }
    }
}
