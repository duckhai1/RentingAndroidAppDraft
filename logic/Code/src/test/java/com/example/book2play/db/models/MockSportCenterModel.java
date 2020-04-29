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

    public MockSportCenterModel() {
        sportCenters = new HashSet<>();
    }

    @Override
    public Collection<SportCenter> getCitySportCenters(String cityId) throws MySQLException {
        return sportCenters.stream()
                .filter(sp -> sp.getCityId().equals(cityId))
                .collect(Collectors.toSet());
    }

    @Override
    public void createCityCenter(String sportCenterId, String cityId) throws MySQLException {
        sportCenters.add(new SportCenter(sportCenterId, cityId));
    }

    @Override
    public void updateSportCenterId(String newSportCenterId, String oldSportCenterId, String cityId) throws MySQLException {
        SportCenter updatedSportCenter = null;
        for (var sp : sportCenters) {
            if (sp.getSportCenterId().equals(oldSportCenterId) && sp.getCityId().equals(cityId)) {
                updatedSportCenter = sp;
                break;
            }
        }

        if (updatedSportCenter != null) {
            sportCenters.remove(updatedSportCenter);
            sportCenters.add(new SportCenter(newSportCenterId, cityId));
        }
    }
}
