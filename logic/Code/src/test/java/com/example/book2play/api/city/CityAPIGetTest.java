package com.example.book2play.api.city;

import com.example.book2play.api.APITestSetup;
import com.example.book2play.api.Server;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.City;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class CityAPIGetTest extends APITestSetup {

    private final static URI API_PATH = URI.create("http://" + HOST + ':' + PORT + Server.CITY_BASE_URL);

    private static Set<City> IN_CITIES;

    @Before
    public void setupCityTest() throws MySQLException {
        IN_CITIES = new HashSet<City>();
        IN_CITIES.add(new City("HCM"));
        IN_CITIES.add(new City("HaNoi"));
        IN_CITIES.add(new City("DaNang"));
        IN_CITIES.add(new City("Hue"));

        for (var city : IN_CITIES) {
            CITY.createCity(city.getCityId());
        }
    }

    @Test
    public void testGetCities() throws Exception {
        CompletableFuture<HttpResponse<String>> responseFuture = asyncGetJSON(API_PATH, Collections.EMPTY_MAP);
        HttpResponse<String> response = responseFuture.get();
        Assert.assertEquals(200, response.statusCode());

        Set<Integer> outCities = GSON.fromJson(
                response.body(),
                new TypeToken<HashSet<City>>() {
                }.getType()
        );
        Assert.assertEquals(IN_CITIES, outCities);
    }
}
