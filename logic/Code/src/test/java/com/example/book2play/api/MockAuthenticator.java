package com.example.book2play.api;

import com.example.book2play.db.models.MockModelDataSource;
import com.example.book2play.db.models.MockPlayerModel;
import com.example.book2play.db.models.MockStaffModel;

public class MockAuthenticator implements TokenAuthenticator {

    private MockPlayerModel playerModel;
    private MockStaffModel staffModel;

    public MockAuthenticator(MockModelDataSource ds) {
        this.playerModel = new MockPlayerModel(ds);
        this.staffModel = new MockStaffModel(ds);
    }

    @Override
    public String validatePlayer(String token, String tokenType) {
        if (playerModel.isPlayerExist(token)) {
            return token;
        }

        return null;
    }

    @Override
    public String validateStaff(String token, String tokenType, String cityId, String sportCenterId) {
        if (staffModel.isStaffExist(token, cityId, sportCenterId)) {
            return token;
        }

        return null;
    }

    @Override
    public String getId(String token, String tokenType) {
        return token;
    }
}
