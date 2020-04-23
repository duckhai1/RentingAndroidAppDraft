package com.example.book2play.db;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.City;

import java.util.Collection;

public interface CityModel {

    Collection<City> getCities() throws MySQLException;

    void createCity(String cityId) throws MySQLException;

    void clearCity() throws MySQLException;

}
