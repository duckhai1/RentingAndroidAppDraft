package com.example.book2play.db.models.utils;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.types.Court;

import java.util.Collection;

public interface CourtModel {

    Court getCourtInfo(String courtId, String cityId, String sportCenterId) throws MySQLException;

    void createCityCenterCourt(String courtId, String cityId, String sportCenterId) throws MySQLException;

    void updateCourtId(String newCourtId, String oldCourtId, String cityId, String sportCenterId) throws MySQLException;

    void clearCourt() throws MySQLException;

    Collection<Court> getCourtsInCity(String cityId) throws MySQLException;
}
