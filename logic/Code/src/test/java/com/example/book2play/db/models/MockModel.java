package com.example.book2play.db.models;

import java.util.logging.Logger;

public class MockModel {
    protected static final Logger LOG = Logger.getLogger("MOCK_MODEL");
    private int toBeThrown;

    public MockModel() {
        toBeThrown = 0;
    }

    public void setToBeThrown(int code) {
        toBeThrown = code;
    }

    public void clearToBeThrown() {
        toBeThrown = 0;
    }

    public int getToBeThrown() {
        return toBeThrown;
    }
}
