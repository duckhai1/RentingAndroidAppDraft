package com.example.book2play.api;

import com.example.book2play.db.models.*;

import java.io.IOException;

public class MockServer extends Server {

    public MockServer(int port,
                       MockBookingModel bookingModel,
                       MockCityModel cityModel,
                       MockCourtModel courtModel,
                       MockPlayerModel playerModel,
                       MockSportCenterModel sportCenterModel,
                       MockStaffModel staffModel,
                       MockAuthenticator authModel
    ) throws IOException {
        super(null, port);
        this.bookingModel = bookingModel;
        this.cityModel = cityModel;
        this.courtModel = courtModel;
        this.playerModel = playerModel;
        this.sportCenterModel = sportCenterModel;
        this.staffModel = staffModel;
        this.authModel = authModel;
    }

    @Override
    protected void setupModels() {
    }

    public void stop() {
        srv.stop(0);
    }


}
