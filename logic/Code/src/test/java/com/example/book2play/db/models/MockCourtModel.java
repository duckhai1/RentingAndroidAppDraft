package com.example.book2play.db.models;

import com.example.book2play.db.CourtModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Court;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MockCourtModel implements CourtModel {

    private Set<Court> courts;

    private static MockCourtModel SINGLETON = null;

    private MockCourtModel() {
        courts = new HashSet<>();
    }

    public static MockCourtModel getInstance() {
        if (SINGLETON == null) {
            SINGLETON = new MockCourtModel();
        }

        return SINGLETON;
    }


    @Override
    public Collection<Court> getCityCourts(String cityId) throws MySQLException {
        var cityModel = MockCityModel.getInstance();
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        return courts.stream()
                .filter(c -> c.getCityId().equals(cityId))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Court> getSportCenterCourts(String sportCenterId, String cityId) throws MySQLException {
        var cityModel = MockCityModel.getInstance();
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        var sportCenterModel = MockSportCenterModel.getInstance();
        if (!sportCenterModel.isSportCenterExist(sportCenterId, cityId)) {
            throw new MySQLException(461);
        }

        return courts.stream()
                .filter(c -> c.getCityId().equals(cityId)
                        && c.getSportCenterId().equals(sportCenterId))
                .collect(Collectors.toSet());
    }

    @Override
    public void createCityCenterCourt(String courtId, String cityId, String sportCenterId) throws MySQLException {
        var cityModel = MockCityModel.getInstance();
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        var sportCenterModel = MockSportCenterModel.getInstance();
        if (!sportCenterModel.isSportCenterExist(sportCenterId, cityId)) {
            throw new MySQLException(461);
        }

        var newCourt = new Court(courtId, cityId, sportCenterId);
        if (courts.contains(newCourt)) {
            throw new MySQLException(404);
        }

        courts.add(newCourt);
    }

    @Override
    public void updateCourtId(String newCourtId, String oldCourtId, String cityId, String sportCenterId) throws MySQLException {
        var cityModel = MockCityModel.getInstance();
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        var sportCenterModel = MockSportCenterModel.getInstance();
        if (!sportCenterModel.isSportCenterExist(sportCenterId, cityId)) {
            throw new MySQLException(461);
        }

        var oldCourt = new Court(oldCourtId, cityId, sportCenterId);
        if (!courts.contains(oldCourt)) {
            throw new MySQLException(462);
        }

        courts.remove(oldCourt);
        courts.add(new Court(newCourtId, cityId, sportCenterId));
    }

    public boolean isCourtExist(String courtId, String cityId, String sportCenterId) {
        return courts.contains(new Court(courtId, cityId, sportCenterId));
    }

    public void clearCourts() {
        courts.clear();
    }
}
