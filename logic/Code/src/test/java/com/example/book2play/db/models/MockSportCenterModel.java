package com.example.book2play.db.models;

import com.example.book2play.db.SportCenterModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.SportCenter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MockSportCenterModel implements SportCenterModel {

    private Set<SportCenter> sportCenters;

    private static MockSportCenterModel SINGLETON = null;

    private MockSportCenterModel() {
        sportCenters = new HashSet<>();
    }

    public static MockSportCenterModel getInstance() {
        if (SINGLETON == null) {
            SINGLETON = new MockSportCenterModel();
        }

        return SINGLETON;
    }


    @Override
    public Collection<SportCenter> getCitySportCenters(String cityId) throws MySQLException {
        var cityModel = MockCityModel.getInstance();
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        return sportCenters.stream()
                .filter(sp -> sp.getCityId().equals(cityId))
                .collect(Collectors.toSet());
    }

    @Override
    public void createCityCenter(String sportCenterId, String cityId) throws MySQLException {
        var cityModel = MockCityModel.getInstance();
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        var newSportCenter = new SportCenter(sportCenterId, cityId);
        if (sportCenters.contains(newSportCenter)) {
            throw new MySQLException(403);
        }

        sportCenters.add(newSportCenter);
    }

    @Override
    public void updateSportCenterId(String newSportCenterId, String oldSportCenterId, String cityId) throws MySQLException {
        var cityModel = MockCityModel.getInstance();
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        var olSportCenter = new SportCenter(oldSportCenterId, cityId);
        if (!sportCenters.contains(oldSportCenterId)) {
            throw new MySQLException(461);
        }

        sportCenters.remove(oldSportCenterId);
        sportCenters.add(new SportCenter(newSportCenterId, cityId));
    }

    public boolean isSportCenterExist(String sportCenterId, String cityId) {
        return sportCenters.contains(new SportCenter(sportCenterId, cityId));
    }

    public void clearSportCenters() {
        sportCenters.clear();
    }
}
