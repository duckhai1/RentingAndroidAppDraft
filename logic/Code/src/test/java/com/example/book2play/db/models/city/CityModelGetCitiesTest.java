package com.example.book2play.db.models.city;

import com.example.book2play.db.models.ModelTestSetup;
import com.example.book2play.types.City;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

public class CityModelGetCitiesTest extends ModelTestSetup {

    @Test
    public void testGetOneCity() throws Exception {
        var cities = new ArrayList<City>();
        cities.add(new City("HoChiMinh"));

        for (var city : cities) {
            CITY.createCity(city.getCityId());
        }

        var output = CITY.getCities();
        Assert.assertEquals(cities, output);
    }

    @Test
    public void testGetMultiplesCities() throws Exception {
        var cities = new HashSet<City>();
        cities.add(new City("HoChiMinh"));
        cities.add(new City("HaNoi"));
        cities.add(new City("DaNang"));

        for (var city : cities) {
            CITY.createCity(city.getCityId());
        }

        var output = new HashSet<>(CITY.getCities());
        Assert.assertEquals(cities, output);
    }

    @Test
    public void testGetNoCity() throws Exception {
        var cities = new HashSet<City>();
        for (var city : cities) {
            CITY.createCity(city.getCityId());
        }

        var output = new HashSet<>(CITY.getCities());
        Assert.assertEquals(cities, output);
    }
}
