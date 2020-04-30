package com.example.book2play.api;

import com.example.book2play.db.models.*;

import java.io.IOException;

public class MockServer extends Server {

    private static MockServer SINGLETON = null;

    private MockServer(int port) throws IOException {
        super(null, port);
        bookingModel = MockBookingModel.getInstance();
        cityModel = MockCityModel.getInstance();
        courtModel = MockCourtModel.getInstance();
        playerModel = MockPlayerModel.getInstance();
        sportCenterModel = MockSportCenterModel.getInstance();
        staffModel = MockStaffModel.getInstance();

        authModel = new MockAuthenticator(
                MockPlayerModel.getInstance(),
                MockStaffModel.getInstance()
        );
    }

    public static MockServer getInstance(int port) throws IOException {
        if (SINGLETON == null) {
            SINGLETON = new MockServer(port);
        }

        return SINGLETON;
    }

    @Override
    protected void setupModels() {
    }

    public void stop() {
        srv.stop(0);
    }


}
