package com.example.book2play.db.models;

import com.example.book2play.db.CourtModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Court;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MockCourtModel extends MockModel implements CourtModel {

    private MockModelDataSource ds;

    public MockCourtModel(MockModelDataSource ds) {
        this.ds = ds;
    }


    @Override
    public Collection<Court> getCityCourts(String cityId) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }
        var cityModel = new MockCityModel(ds);
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        return ds.getCourts().stream()
                .filter(c -> c.getCityId().equals(cityId))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Court> getSportCenterCourts(String sportCenterId, String cityId) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }
        var cityModel = new MockCityModel(ds);
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        var sportCenterModel = new MockSportCenterModel(ds);;
        if (!sportCenterModel.isSportCenterExist(sportCenterId, cityId)) {
            throw new MySQLException(461);
        }

        return ds.getCourts().stream()
                .filter(c -> c.getCityId().equals(cityId)
                        && c.getSportCenterId().equals(sportCenterId))
                .collect(Collectors.toSet());
    }

    @Override
    public void createCityCenterCourt(String courtId, String cityId, String sportCenterId) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }
        var cityModel = new MockCityModel(ds);
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        var sportCenterModel = new MockSportCenterModel(ds);;
        if (!sportCenterModel.isSportCenterExist(sportCenterId, cityId)) {
            throw new MySQLException(461);
        }

        var newCourt = new Court(courtId, cityId, sportCenterId);
        if (ds.getCourts().contains(newCourt)) {
            throw new MySQLException(404);
        }

        ds.getCourts().add(newCourt);
    }

    @Override
    public void updateCourtId(String newCourtId, String oldCourtId, String cityId, String sportCenterId) throws MySQLException {
        if (getToBeThrown() != 0) {
            throw new MySQLException(getToBeThrown());
        }
        var cityModel = new MockCityModel(ds);
        if (!cityModel.isCityExist(cityId)) {
            throw new MySQLException(460);
        }

        var sportCenterModel = new MockSportCenterModel(ds);;
        if (!sportCenterModel.isSportCenterExist(sportCenterId, cityId)) {
            throw new MySQLException(461);
        }

        var oldCourt = new Court(oldCourtId, cityId, sportCenterId);
        if (!ds.getCourts().contains(oldCourt)) {
            throw new MySQLException(462);
        }

        ds.getCourts().remove(oldCourt);
        ds.getCourts().add(new Court(newCourtId, cityId, sportCenterId));
    }

    public boolean isCourtExist(String courtId, String cityId, String sportCenterId) {
        return ds.getCourts().contains(new Court(courtId, cityId, sportCenterId));
    }

    public void clearCourts() {
        ds.getCourts().clear();
    }
}
