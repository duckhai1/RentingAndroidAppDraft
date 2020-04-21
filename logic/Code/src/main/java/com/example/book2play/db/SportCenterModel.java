package com.example.book2play.db;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.SportCenter;

import java.util.Collection;

public interface SportCenterModel {

    SportCenter getSportCenterInfo(String sportCenterId, String cityId) throws MySQLException;

    void createCityCenter(String sportCenterId, String cityId) throws MySQLException;

    void updateSportCenterId(String newSportCenterId, String oldSportCenterId, String cityId) throws MySQLException;

    void clearSportCenter() throws MySQLException;

    Collection<SportCenter> getCitySportCenters(String cityId) throws MySQLException;
}
