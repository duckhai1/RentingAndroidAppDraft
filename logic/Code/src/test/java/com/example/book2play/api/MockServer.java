package com.example.book2play.api;

import com.example.book2play.db.*;

import java.io.IOException;

public class MockServer extends Server {
    public MockServer(int port,
                      Authenticator authModel,
                      BookingModel bookingModel,
                      CityModel cityModel,
                      CourtModel courtModel,
                      PlayerModel playerModel,
                      SportCenterModel sportCenterModel,
                      StaffModel staffModel) throws IOException {
        super(null, port);
        this.authModel = authModel;
        this.bookingModel = bookingModel;
        this.cityModel = cityModel;
        this.courtModel = courtModel;
        this.playerModel = playerModel;
        this.sportCenterModel = sportCenterModel;
        this.staffModel = staffModel;
    }

    @Override
    protected void setupModels() {
    }
}
