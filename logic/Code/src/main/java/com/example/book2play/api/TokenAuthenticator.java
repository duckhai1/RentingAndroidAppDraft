package com.example.book2play.api;

import com.example.book2play.db.exceptions.MySQLException;

public interface TokenAuthenticator {

    /**
     * Confirm whether a given token is belonged to a player
     *
     * @param token player facebook access token
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    String validatePlayer(String token);

    /**
     * Confirm whether a given token is belonged to a staff
     *
     * @param token         staff facebook access token
     * @param cityId        the unique identifier of the city the the sport center locates in
     * @param sportCenterId the unique identifier, in the city, of the sport center
     * @throws MySQLException if an access or connections error happened with the data source, or the status code returned by the stored procedure indicates an error happened
     */
    String validateStaff(String token, String cityId, String sportCenterId);

    String getId(String token);
}
