package com.example.book2play.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface AppDataSource {
    /**
     * Return the connection that is established with the configuration stored in the object
     *
     * @return a connection to the database
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;
}
