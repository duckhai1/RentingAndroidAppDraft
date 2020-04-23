package com.example.book2play.db;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Court;

import java.util.Collection;

public interface CourtModel {

    Collection<Court> getCityCourts(String cityId) throws MySQLException;

    Collection<Court> getSportCenterCourts(String sportCenterId, String cityId) throws MySQLException;

    void createCityCenterCourt(String courtId, String cityId, String sportCenterId) throws MySQLException;

    void updateCourtId(String newCourtId, String oldCourtId, String cityId, String sportCenterId) throws MySQLException;

    void clearCourt() throws MySQLException;

}
