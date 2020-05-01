package com.example.book2play.db.models;

import com.example.book2play.db.CityModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.City;
import com.example.utils.Validators;

import java.util.Collection;
import java.util.HashSet;

public class MockCityModel extends MockModel implements CityModel {

    private MockModelDataSource ds;

    public MockCityModel(MockModelDataSource ds) {
        this.ds = ds;
    }

    @Override
    public Collection<City> getCities() throws MySQLException {
        return new HashSet<>(ds.getCities());
    }

    @Override
    public void createCity(String cityId) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }

        if (!Validators.isIdValid(cityId)) {
            throw new MySQLException(460);
        }

        var newCity = new City(cityId);
        if (ds.getCities().contains(newCity)) {
            throw new MySQLException(402);
        }

        ds.getCities().add(newCity);
    }

    public boolean isCityExist(String cityId) {
        return ds.getCities().contains(new City(cityId));
    }

    public void clearCities() {
        ds.getCities().clear();
    }
}
