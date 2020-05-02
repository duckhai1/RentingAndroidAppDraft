package com.example.book2play.db.models;

public class MockModel {
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
