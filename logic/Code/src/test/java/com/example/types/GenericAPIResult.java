package com.example.types;

import com.google.gson.annotations.Expose;

public class GenericAPIResult {
    @Expose
    private boolean hasError;
    @Expose
    private int statusCode;
    @Expose
    private String message;

    public GenericAPIResult(boolean hasError, int statusCode, String message) {
        this.hasError = hasError;
        this.statusCode = statusCode;
        this.message = message;
    }

    public boolean isHasError() {
        return hasError;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Res(" + hasError + ", " + statusCode + ", " + message + ")";
    }
}
