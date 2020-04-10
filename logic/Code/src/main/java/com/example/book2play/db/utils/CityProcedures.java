package com.example.book2play.db.utils;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.types.City;

import java.util.Collection;

public interface CityProcedures {

    Collection<City> getCities() throws MySQLException;

    void createCity(String cityId) throws MySQLException;

    void clearCity() throws MySQLException;
}
