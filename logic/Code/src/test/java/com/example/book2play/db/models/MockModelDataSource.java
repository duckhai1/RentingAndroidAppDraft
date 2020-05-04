package com.example.book2play.db.models;

import com.example.book2play.types.*;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MockModelDataSource {
    private Set<Booking> bookings;
    private Set<City> cities;
    private Set<Court> courts;
    private Set<Player> players;
    private Set<SportCenter> sportCenters;
    private Set<Staff> staffs;

    public MockModelDataSource() {
        bookings = Collections.newSetFromMap(new ConcurrentHashMap<>());
        cities = Collections.newSetFromMap(new ConcurrentHashMap<>());
        courts = Collections.newSetFromMap(new ConcurrentHashMap<>());
        players = Collections.newSetFromMap(new ConcurrentHashMap<>());
        sportCenters = Collections.newSetFromMap(new ConcurrentHashMap<>());
        staffs = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public Set<City> getCities() {
        return cities;
    }

    public Set<Court> getCourts() {
        return courts;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public Set<SportCenter> getSportCenters() {
        return sportCenters;
    }

    public Set<Staff> getStaffs() {
        return staffs;
    }

    public void clear() {
        bookings.clear();
        cities.clear();
        courts.clear();
        players.clear();
        sportCenters.clear();
        staffs.clear();
    }
}
