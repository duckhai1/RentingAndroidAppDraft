package com.example.book2play.db.models.utils;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.types.City;

import java.util.Collection;

public interface CityModel {

    Collection<City> getCities() throws MySQLException;

    void createCity(String cityId) throws MySQLException;

    void clearCity() throws MySQLException;
}
