package com.example.book2play.api.city;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.City;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CityAPIGetTest extends APITestSetup {

    @Test
    public void testGetCities() throws Exception {
        var expected = cityIDs.stream().map(City::new).collect(Collectors.toSet());
        for (var id : cityIDs) {
            CITY.createCity(id);
        }
        PLAYER.createPlayer(playerIDs.get(0));

        var responseFuture = asyncGetJSON(CITY_API_PATH, playerIDs.get(0), new HashMap<>());
        var response = responseFuture.get();
        Assert.assertEquals(HTTPStatus.OK, response.statusCode());

        Set<Integer> outCities = GSON.fromJson(
                response.body(),
                new TypeToken<HashSet<City>>() {
                }.getType()
        );
        Assert.assertEquals(expected, outCities);
    }

    @Test
    public void testGetCitiesEmptyDatabase() throws Exception {
        var responseFuture = asyncGetJSON(CITY_API_PATH, new HashMap<>());
        var response = responseFuture.get();
        Assert.assertEquals(HTTPStatus.OK, response.statusCode());

        Set<Integer> outCities = GSON.fromJson(
                response.body(),
                new TypeToken<HashSet<City>>() {
                }.getType()
        );
        Assert.assertEquals(Collections.emptySet(), outCities);
    }
}
