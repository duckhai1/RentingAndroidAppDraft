package com.example.book2play.db.models;

import com.example.book2play.types.*;

import java.util.HashSet;
import java.util.Set;

public class MockModelDataSource {
    private Set<Booking> bookings;
    private Set<City> cities;
    private Set<Court> courts;
    private Set<Player> players;
    private Set<SportCenter> sportCenters;
    private Set<Staff> staffs;

    public MockModelDataSource() {
        bookings = new HashSet<>();
        cities = new HashSet<>();
        courts = new HashSet<>();
        players = new HashSet<>();
        sportCenters = new HashSet<>();
        staffs = new HashSet<>();
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
}
