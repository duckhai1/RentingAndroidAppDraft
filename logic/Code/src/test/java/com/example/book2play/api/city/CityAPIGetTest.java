package com.example.book2play.api.city;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.types.City;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;

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

        var response= getJSON(CITY_API_PATH, playerIDs.get(0), null);
        Assert.assertEquals(HTTPStatus.OK, response.statusCode());

        Set<City> outCities = GSON.fromJson(
                response.body(),
                new TypeToken<HashSet<City>>() {
                }.getType()
        );
        Assert.assertEquals(expected, outCities);
    }
}

