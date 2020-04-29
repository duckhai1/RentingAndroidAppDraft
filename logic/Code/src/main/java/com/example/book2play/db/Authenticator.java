package com.example.book2play.db;

import com.example.book2play.db.exceptions.MySQLException;

public interface Authenticator {

    /**
     * Confirm whether a given id is playerId
     *
     * @param playerId the unique identifier to be confirmed
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    boolean isPlayer(String playerId) throws MySQLException;

    /**
     * Confirm whether a given id is staffId
     *
     * @param staffId the unique identifier to be confirmed
     * @param cityId  the unique identifier of the city the the sport center locates in
     * @param sportCenterId the unique identifier, in the city, of the sport center
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    boolean isStaff(String staffId, String cityId, String sportCenterId) throws MySQLException;

}
