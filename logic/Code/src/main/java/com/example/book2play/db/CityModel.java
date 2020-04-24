package com.example.book2play.db;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.City;

import java.util.Collection;

public interface CityModel {

    /**
     * Get the collection of a existing cities in the data source
     *
     * @return collection of cities
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    Collection<City> getCities() throws MySQLException;

    /**
     * Create a new city with the given city id in the data source
     *
     * @param cityId the unique identifier of the city
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    void createCity(String cityId) throws MySQLException;

    /**
     * Clear the relation, for testing
     *
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     * @deprecated will be moved to test only
     */
    void clearCity() throws MySQLException;

}
