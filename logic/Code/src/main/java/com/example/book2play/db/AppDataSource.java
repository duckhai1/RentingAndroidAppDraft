package com.example.book2play.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface AppDataSource {
    /**
     * Return turn the connection that is established with the configuration of the object
     *
     * @return a connection to the database, guaranteed to be a object
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;
}
