package com.example.book2play.db;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.SportCenter;

import java.util.Collection;

public interface SportCenterModel {

    /**
     * Get a collection of all the sport centers located in the given city
     *
     * @param cityId the unique identifier of the city
     * @return the collection of all the sport centers in the city
     * @throws MySQLException
     */
    Collection<SportCenter> getCitySportCenters(String cityId) throws MySQLException;

    /**
     * Create new a sport center located in the given city
     *
     * @param sportCenterId the unique identifier in the city of the new sport center
     * @param cityId        the unique identifier of the city
     * @throws MySQLException
     */
    void createCityCenter(String sportCenterId, String cityId) throws MySQLException;

    /**
     * Change the unique identifier of the given sport center
     *
     * @param newSportCenterId the new unique identifier to be assigned to the sport center
     * @param oldSportCenterId the current unique identifier of the sport center
     * @param cityId           the unique identifier of the city that the sport centers locates in
     * @throws MySQLException
     */
    void updateSportCenterId(String newSportCenterId, String oldSportCenterId, String cityId) throws MySQLException;

    /**
     * Empty the sport center relation, for testing
     *
     * @throws MySQLException
     */
    void clearSportCenter() throws MySQLException;

}
