package com.example.book2play.db.models;

import com.example.book2play.db.CityModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.City;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MockCityModel implements CityModel {

    private Set<City> cities;

    public MockCityModel() {
        cities = new HashSet<>();
    }

    @Override
    public Collection<City> getCities() throws MySQLException {
        return new HashSet<>(cities);
    }

    @Override
    public void createCity(String cityId) throws MySQLException {
        cities.add(new City(cityId));
    }
}
