package com.example.book2play.db.exceptions;

/**
 * Main exception type for the package
 * Encapsulate almost every other exception the happened within the package
 */
public class MySQLException extends Exception {

    protected Integer statusCode;

    public MySQLException() {
    }

    public MySQLException(String msg) {
        super(msg);
    }

    public MySQLException(Throwable cause) {
        super(cause);
    }

    public MySQLException(String message, Throwable cause) {
        super(message, cause);
    }

    public MySQLException(Integer code) {
        super("Encounter error code " + code);
        this.statusCode = code;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
