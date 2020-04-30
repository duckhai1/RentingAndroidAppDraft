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
        return courts.stream()
                .filter(c -> c.getCityId().equals(cityId))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Court> getSportCenterCourts(String sportCenterId, String cityId) throws MySQLException {
        return courts.stream()
                .filter(c -> c.getCityId().equals(cityId)
                        && c.getSportCenterId().equals(sportCenterId))
                .collect(Collectors.toSet());
    }

    @Override
    public void createCityCenterCourt(String courtId, String cityId, String sportCenterId) throws MySQLException {
        courts.add(new Court(courtId, cityId, sportCenterId));
    }

    @Override
    public void updateCourtId(String newCourtId, String oldCourtId, String cityId, String sportCenterId) throws MySQLException {
        Court updatedCourt = null;
        for (var c : courts) {
            if (c.getCourtId().equals(oldCourtId) && c.getCityId().equals(cityId) && c.getSportCenterId().equals(sportCenterId)) {
                updatedCourt = c;
                break;
            }
        }

        if (updatedCourt != null) {
            courts.remove(updatedCourt);
            courts.add(new Court(newCourtId, cityId, sportCenterId));
        }
    }

    public void clearCourts() {
        courts.clear();
    }
}
