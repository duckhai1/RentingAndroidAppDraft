package com.example.book2play.db.models;

import com.example.book2play.db.SportCenterModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.SportCenter;

import java.util.Collection;
import java.util.stream.Collectors;

public class MockSportCenterModel extends MockModel implements SportCenterModel {

    private MockModelDataSource ds;

    public MockSportCenterModel(MockModelDataSource ds) {
        this.ds = ds;
    }

    @Override
    public Collection<SportCenter> getCitySportCenters(String cityId) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }
        var cityModel = new MockCityModel(ds);
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        return ds.getSportCenters().stream()
                .filter(sp -> sp.getCityId().equals(cityId))
                .collect(Collectors.toSet());
    }

    @Override
    public void createCityCenter(String sportCenterId, String cityId) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }
        var cityModel = new MockCityModel(ds);
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        var newSportCenter = new SportCenter(sportCenterId, cityId);
        if (ds.getSportCenters().contains(newSportCenter)) {
            throw new MySQLException(403);
        }

        ds.getSportCenters().add(newSportCenter);
    }

    @Override
    public void updateSportCenterId(String newSportCenterId, String oldSportCenterId, String cityId) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }
        var cityModel = new MockCityModel(ds);
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        var oldSportCenter = new SportCenter(oldSportCenterId, cityId);
        if (!ds.getSportCenters().contains(oldSportCenter)) {
            throw new MySQLException(461);
        }

        ds.getSportCenters().remove(oldSportCenterId);
        ds.getSportCenters().add(new SportCenter(newSportCenterId, cityId));
    }

    public boolean isSportCenterExist(String sportCenterId, String cityId) {
        return ds.getSportCenters().contains(new SportCenter(sportCenterId, cityId));
    }

    public void clearSportCenters() {
        ds.getSportCenters().clear();
    }
}
