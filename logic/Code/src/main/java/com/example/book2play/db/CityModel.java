package com.example.book2play.db;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.City;

import java.util.Collection;

public interface CityModel {

    /**
     * Get the collection of a existing cities in the data source
     *
     * @return collection of cities
     * @throws MySQLException
     */
    Collection<City> getCities() throws MySQLException;

    /**
     * Create a new city with the given city id in the data source
     *
     * @param cityId the unique identifier of the city
     * @throws MySQLException
     */
    void createCity(String cityId) throws MySQLException;

    /**
     * Clear the relation, for testing
     *
     * @throws MySQLException
     */
    void clearCity() throws MySQLException;

}
