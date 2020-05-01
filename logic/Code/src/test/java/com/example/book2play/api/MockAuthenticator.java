package com.example.book2play.api;

import com.example.book2play.api.utils.FbTokenValidator;
import com.example.book2play.db.models.MockModelDataSource;
import com.example.book2play.db.models.MockPlayerModel;
import com.example.book2play.db.models.MockStaffModel;

public class MockAuthenticator extends FbTokenValidator {

    private MockModelDataSource ds;

    public MockAuthenticator(MockModelDataSource ds) {
        super(new MockPlayerModel(ds), new MockStaffModel(ds));
    }

    @Override
    public String getId(String token) {
        return token;
    }
}
