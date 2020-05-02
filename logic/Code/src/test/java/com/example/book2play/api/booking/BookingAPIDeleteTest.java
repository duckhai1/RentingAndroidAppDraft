package com.example.book2play.api.booking;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.types.GenericAPIResult;
import com.example.utils.TimeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BookingAPIDeleteTest extends APITestSetup {
    @Test
    public void testDeleteBookingSuccess() throws Exception {
        for (var playerID : playerIDs) {
            PLAYER.createPlayer(playerID);
        }

        for (var city : cityIDs) {
            CITY.createCity(city);
            for (var center : sportCenterIDs) {
                SPORT_CENTER.createCityCenter(center, city);
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

        var testFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var b : DS.getBookings()) {
            var query = new HashMap<String, List<String>>();
            query.put("bookingId", new ArrayList<>());
            query.get("bookingId").add(b.getBookingId());
            testFutures.add(asyncDelete(BOOKING_API_PATH, b.getPlayerId(), query));
        }

        for (var f : testFutures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.ACCEPTED, response.statusCode());
        }
    }

    @Test
    public void testDeleteBookingExpectAllMySqlExceptions() throws Exception {
        PLAYER.createPlayer(playerIDs.get(0));
        var testInputs = new ArrayList<Integer>();
        testInputs.add(401);
        testInputs.add(411);
        testInputs.add(464);
        testInputs.add(465);

        for (var code : testInputs) {
            BOOKING.setToBeThrown(code);

            var query = new HashMap<String, List<String>>();
            query.put("bookingId", new ArrayList<>());
            query.get("bookingId").add("ArbitraryData");

            var responseFuture = asyncDelete(BOOKING_API_PATH, playerIDs.get(0), query);
            var response = responseFuture.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

            var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
            Assert.assertNotEquals(null, apiRes);
            Assert.assertTrue(apiRes.isHasError());
            Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
        }
    }

    @Test
    public void testDeleteBookingInvalidURLEncodedData() throws Exception {
        PLAYER.createPlayer(playerIDs.get(0));
        var bookingQueries = new ArrayList<List<String>>();
        bookingQueries.add(null);
        bookingQueries.add(new ArrayList<>());
        bookingQueries.add(new ArrayList<>());
        bookingQueries.get(2).add("ArbitraryData");
        bookingQueries.get(2).add("ArbitraryData");
        bookingQueries.get(2).add("ArbitraryData");


        var testFutures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (var bookingQuery : bookingQueries) {
            var query = new HashMap<String, List<String>>();
            if (bookingQuery != null) {
                query.put("bookingId", bookingQuery);
            }
            testFutures.add(asyncPut(COURT_API_PATH, playerIDs.get(0), query));
        }

        for (var f : testFutures) {
            var response = f.get();
            Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
        }
    }
}
