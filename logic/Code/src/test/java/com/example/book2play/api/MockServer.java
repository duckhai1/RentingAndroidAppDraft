package com.example.book2play.api;

import com.example.book2play.db.models.*;

import java.io.IOException;

public class MockServer extends Server {
    public MockServer(int port) throws IOException {
        super(null, port);
    }

    @Override
    protected void setupModels() {
        var newPlayerModel = new MockPlayerModel();
        var newStaffModel = new MockStaffModel();

        this.authModel = new MockAuthenticator(newPlayerModel, newStaffModel);
        this.bookingModel = new MockBookingModel();
        this.cityModel = new MockCityModel();
        this.courtModel = new MockCourtModel();
        this.playerModel = newPlayerModel;
        this.sportCenterModel = new MockSportCenterModel();
        this.staffModel = newStaffModel;
    }
}
