package com.example.book2play.db.models;

import com.example.book2play.db.Authenticator;
import com.example.book2play.db.exceptions.MySQLException;

public class MockAuthenticator implements Authenticator {

    private MockPlayerModel mockPlayerModel;
    private MockStaffModel mockStaffModel;

    public MockAuthenticator(MockPlayerModel mockPlayerModel, MockStaffModel mockStaffModel) {
        this.mockPlayerModel = mockPlayerModel;
        this.mockStaffModel = mockStaffModel;
    }

    @Override
    public boolean isPlayer(String playerId) throws MySQLException {
        return mockPlayerModel.playerExists(playerId);
    }

    @Override
    public boolean isStaff(String staffId, String cityId, String sportCenterId) throws MySQLException {
        return mockStaffModel.staffExists(staffId, cityId, sportCenterId);
    }

    @Override
    public String getPlayerId(String token) throws MySQLException {
        return null;
    }

    @Override
    public void signupPlayer(String playerId, String password) throws MySQLException {

    }

    @Override
    public String loginPlayer(String playerId, String password) throws MySQLException {
        return null;
    }

    @Override
    public void logoutPlayer(String token) throws MySQLException {

    }
}
