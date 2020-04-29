package com.example.book2play.db.models;

import com.example.book2play.db.CityModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.City;
import com.example.test_utils.Validators;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MockCityModel implements CityModel {

    private Set<City> cities;

    private static MockCityModel SINGLETON = null;

    private MockCityModel() {
        cities = new HashSet<>();
    }

    public static MockCityModel getInstance() {
        if (SINGLETON == null) {
            SINGLETON = new MockCityModel();
        }

        return SINGLETON;
    }

    @Override
    public Collection<City> getCities() throws MySQLException {
        return new HashSet<>(cities);
    }

    @Override
    public void createCity(String cityId) throws MySQLException {
        if (!Validators.isIdValid(cityId)) {
            throw new MySQLException(460);
        }

        var newCity = new City(cityId);
        if (cities.contains(newCity)) {
            throw new MySQLException(402);
        }

        cities.add(newCity);
    }

    public void clearCities() {
        cities.clear();
    }
}
