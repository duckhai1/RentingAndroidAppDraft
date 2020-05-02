package com.example.book2play.api.booking;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.Booking;
import com.example.types.GenericAPIResult;
import com.example.utils.TimeUtils;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class BookingAPIGetTest extends APITestSetup {

    @Before
    public void setupLotsOfBookingsWithOneStaff() throws Exception {
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
    public void testGetCourtBookingsSuccess() throws Exception {
        for (var cityId : cityIDs) {
            for (var sportCenterId : sportCenterIDs) {
                for (var courtId : courtIDs) {
                    var query = new HashMap<String, List<String>>();
                    query.put("cityId", new ArrayList<>());
                    query.get("cityId").add(cityId);
                    query.put("sportCenterId", new ArrayList<>());
                    query.get("sportCenterId").add(sportCenterId);
                    query.put("courtId", new ArrayList<>());
                    query.get("courtId").add(courtId);
                    query.put("date", new ArrayList<>());
                    query.get("date").add(TimeUtils.getDate(7).toString());

                    var responseFuture = asyncGetJSON(BOOKING_API_PATH, staffIDs.get(0), query);
                    var response = responseFuture.get();
                    Assert.assertEquals(HTTPStatus.OK, response.statusCode());

                    Set<Booking> outBookings = GSON.fromJson(
                            response.body(),
                            new TypeToken<HashSet<Booking>>() {
                            }.getType()
                    );
                    Assert.assertEquals(BOOKING.getCourtBookings(courtId, cityId, sportCenterId, TimeUtils.getDate(7)), outBookings);
                }
            }
        }
    }

    @Test
    public void testGetCourtBookingsExpectAllMySqlException() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(460);
        testInputs.add(461);
        testInputs.add(462);

        for (var code : testInputs) {
            for (var city : cityIDs) {
                for (var sportCenterId : sportCenterIDs) {
                    BOOKING.setToBeThrown(code);

                    var query = new HashMap<String, List<String>>();
                    query.put("cityId", new ArrayList<>());
                    query.get("cityId").add(city);
                    query.put("sportCenterId", new ArrayList<>());
                    query.get("sportCenterId").add(sportCenterId);
                    query.put("courtId", new ArrayList<>());
                    query.get("courtId").add("ArbitraryData");
                    query.put("date", new ArrayList<>());
                    query.get("date").add(TimeUtils.getDate(7).toString());

                    var responseFuture = asyncGetJSON(BOOKING_API_PATH, staffIDs.get(0), query);
                    var response = responseFuture.get();
                    Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

                    var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
                    Assert.assertNotEquals(null, apiRes);
                    Assert.assertTrue(apiRes.isHasError());
                    Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
                }
            }
        }
    }

    @Test
    public void testGetSportCenterBookingsSuccess() throws Exception {
        for (var cityId : cityIDs) {
            for (var sportCenterId : sportCenterIDs) {
                var query = new HashMap<String, List<String>>();
                query.put("cityId", new ArrayList<>());
                query.get("cityId").add(cityId);
                query.put("sportCenterId", new ArrayList<>());
                query.get("sportCenterId").add(sportCenterId);
                query.put("date", new ArrayList<>());
                query.get("date").add(TimeUtils.getDate(7).toString());

                var responseFuture = asyncGetJSON(BOOKING_API_PATH, staffIDs.get(0), query);
                var response = responseFuture.get();
                Assert.assertEquals(HTTPStatus.OK, response.statusCode());

                Set<Booking> outBookings = GSON.fromJson(
                        response.body(),
                        new TypeToken<HashSet<Booking>>() {
                        }.getType()
                );
                Assert.assertEquals(BOOKING.getSportCenterBookings(sportCenterId, cityId, TimeUtils.getDate(7)), outBookings);
            }
        }
    }

    @Test
    public void testGetSportCenterBookingsExpectAllMySqlException() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(460);
        testInputs.add(461);

        for (var code : testInputs) {
            for (var city : cityIDs) {
                for (var sportCenterId : sportCenterIDs) {
                    BOOKING.setToBeThrown(code);

                    var query = new HashMap<String, List<String>>();
                    query.put("cityId", new ArrayList<>());
                    query.get("cityId").add(city);
                    query.put("sportCenterId", new ArrayList<>());
                    query.get("sportCenterId").add(sportCenterId);
                    query.put("date", new ArrayList<>());
                    query.get("date").add(TimeUtils.getDate(7).toString());

                    var responseFuture = asyncGetJSON(BOOKING_API_PATH, staffIDs.get(0), query);
                    var response = responseFuture.get();
                    Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());

                    var apiRes = GSON.fromJson(response.body(), GenericAPIResult.class);
                    Assert.assertNotEquals(null, apiRes);
                    Assert.assertTrue(apiRes.isHasError());
                    Assert.assertEquals(code.intValue(), apiRes.getStatusCode());
                }
            }
        }
    }

    @Test
    public void testGetPlayerBookingsInCitySuccess() throws Exception {
        for (var player : playerIDs) {
            for (var city : cityIDs) {
                var query = new HashMap<String, List<String>>();
                query.put("cityId", new ArrayList<>());
                query.get("cityId").add(city);
                query.put("date", new ArrayList<>());
                query.get("date").add(TimeUtils.getDate(7).toString());

                var responseFuture = asyncGetJSON(BOOKING_API_PATH, player, query);
                var response = responseFuture.get();
                Assert.assertEquals(HTTPStatus.OK, response.statusCode());

                Set<Booking> outBookings = GSON.fromJson(
                        response.body(),
                        new TypeToken<HashSet<Booking>>() {
                        }.getType()
                );
                Assert.assertEquals(BOOKING.getPlayerBookingsInCity(player, city, TimeUtils.getDate(7)), outBookings);

            }
        }
    }

    @Test
    public void testGetPlayerBookingsInCityExpectAllMySqlException() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(460);
        testInputs.add(464);

        for (var code : testInputs) {
            for (var player : playerIDs) {
                BOOKING.setToBeThrown(code);

                var query = new HashMap<String, List<String>>();
                query.put("cityId", new ArrayList<>());
                query.get("cityId").add("ArbitraryData");
                query.put("date", new ArrayList<>());
                query.get("date").add(TimeUtils.getDate(7).toString());

                var responseFuture = asyncGetJSON(BOOKING_API_PATH, player, query);
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
    public void testGetPlayerBookingsSuccess() throws Exception {
        for (var player : playerIDs) {
            var responseFuture = asyncGetJSON(BOOKING_API_PATH, player, null);
            var response = responseFuture.get();
            Assert.assertEquals(HTTPStatus.OK, response.statusCode());

            Set<Booking> outBookings = GSON.fromJson(
                    response.body(),
                    new TypeToken<HashSet<Booking>>() {
                    }.getType()
            );
            Assert.assertEquals(BOOKING.getPlayerBookings(player), outBookings);
        }
    }

    @Test
    public void testGetPlayerBookingsExpectAllMySqlException() throws Exception {
        var testInputs = new ArrayList<Integer>();
        testInputs.add(464);

        for (var code : testInputs) {
            for (var player : playerIDs) {
                BOOKING.setToBeThrown(code);

                var query = new HashMap<String, List<String>>();
                query.put("cityId", new ArrayList<>());
                query.get("cityId").add("ArbitraryData");
                query.put("date", new ArrayList<>());
                query.get("date").add(TimeUtils.getDate(7).toString());

                var responseFuture = asyncGetJSON(BOOKING_API_PATH, player, query);
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
    public void testGetBookingsInvalidURLEncodedData() throws Exception {
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

        var courtQueries = new ArrayList<List<String>>();
        courtQueries.add(null);
        courtQueries.add(new ArrayList<>());
        courtQueries.add(new ArrayList<>());
        courtQueries.get(2).add(cityIDs.get(0));
        courtQueries.add(new ArrayList<>());
        courtQueries.get(3).addAll(courtIDs);

        var dateQueries = new ArrayList<List<String>>();
        dateQueries.add(null);
        dateQueries.add(new ArrayList<>());
        dateQueries.add(new ArrayList<>());
        dateQueries.get(2).add(TimeUtils.getDate(7).toString());
        dateQueries.get(2).add(TimeUtils.getDate(8).toString());

        for (var cityQuery : cityQueries) {
            for (var sportCenterQuery : sportCenterQueries) {
                for (var courtQuery : courtQueries) {
                    for (var dateQuery : dateQueries) {
                        if (dateQuery != null && dateQuery.size() == 1
                                && courtQuery != null && courtQueries.size() == 1
                                && sportCenterQuery != null && sportCenterQueries.size() == 1
                                && cityQuery != null && cityQuery.size() == 1) {
                            continue;
                        }

                        if (dateQuery != null && dateQuery.size() == 1
                                && sportCenterQuery != null && sportCenterQueries.size() == 1
                                && cityQuery != null && cityQuery.size() == 1) {
                            continue;
                        }

                        if (dateQuery != null && dateQuery.size() == 1
                                && cityQuery != null && cityQuery.size() == 1) {
                            continue;
                        }

                        if (dateQuery == null && cityQuery == null) {
                            continue;
                        }

                        var query = new HashMap<String, List<String>>();
                        if (cityQuery != null) {
                            query.put("cityId", cityQuery);
                        }
                        if (sportCenterQuery != null) {
                            query.put("sportCenterId", sportCenterQuery);
                        }
                        if (courtQuery != null) {
                            query.put("courtId", courtQuery);
                        }
                        if (dateQuery != null) {
                            query.put("date", dateQuery);
                        }

                        var responseFuture = asyncPut(STAFF_API_PATH, playerIDs.get(0), query);
                        var response = responseFuture.get();
                        Assert.assertEquals(HTTPStatus.BAD_REQUEST, response.statusCode());
                    }
                }
            }
        }
    }
}
