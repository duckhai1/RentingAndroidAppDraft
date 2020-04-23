package com.example.book2play.db;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Court;

import java.util.Collection;

public interface CourtModel {

    /**
     * Get the collection of courts in the given city
     *
     * @param cityId the unique identifier of the city
     * @return the collection of courts from the city
     * @throws MySQLException
     */
    Collection<Court> getCityCourts(String cityId) throws MySQLException;

    /**
     * Get all the courts exist in the given sport center
     *
     * @param sportCenterId the unique identifier for the sport center in the given city
     * @param cityId        the unique identifier of the city
     * @return the collection of courts in the city
     * @throws MySQLException
     */
    Collection<Court> getSportCenterCourts(String sportCenterId, String cityId) throws MySQLException;

    /**
     * Create a court with the given unique identifier under the given sport center and city
     *
     * @param courtId       the unique identifier of the court in the given sport center
     * @param cityId        the unique identifier of the city
     * @param sportCenterId the unique identifier if the sport center in the given city
     * @throws MySQLException
     */
    void createCityCenterCourt(String courtId, String cityId, String sportCenterId) throws MySQLException;

    /**
     * Change the court id of a court to a new one
     *
     * @param newCourtId    the new unique identifier in the given sport center to be given to the court
     * @param oldCourtId    the current unique identifier of the court in the given sport center
     * @param cityId        the unique identifier of the city
     * @param sportCenterId the unique identifier of the sport center in the given city
     * @throws MySQLException
     */
    void updateCourtId(String newCourtId, String oldCourtId, String cityId, String sportCenterId) throws MySQLException;

    /**
     * Empty the court relation, for testing
     *
     * @throws MySQLException
     */
    void clearCourt() throws MySQLException;

}
