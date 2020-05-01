package com.example.book2play.db.models;

import com.example.book2play.db.Authenticator;
import com.example.book2play.types.Player;
import com.example.book2play.types.Staff;

public class MockAuthenticator implements Authenticator {

    private MockModelDataSource ds;

    public MockAuthenticator(MockModelDataSource ds) {
        this.ds = ds;
    }

    @Override
    public boolean isPlayer(String playerId) {
        return ds.getPlayers().contains(new Player(playerId));
    }

    @Override
    public boolean isStaff(String staffId, String cityId, String sportCenterId) {
        return ds.getStaffs().contains(new Staff(staffId, cityId, sportCenterId));
    }
}
