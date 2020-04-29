package com.example.book2play.api;

import com.example.book2play.db.models.MySQLAuthenticator;
import com.example.book2play.db.models.MockBookingModel;

import java.io.IOException;

public class MockServer extends Server {
    public MockServer(int port) throws IOException {
        super(null, port);
    }

    @Override
    protected void setupModels() {
        this.authModel = new MySQLAuthenticator();
        this.bookingModel= new MockBookingModel();

    }
}
