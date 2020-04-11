package com.example.book2play.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLServer {
    private String host;
    private String port;
    private String database;
    private Properties props;

    public MySQLServer(String host, String port, String database, Properties props) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.props = props;
    }

    public Connection getConnection() throws SQLException {
        var conn = DriverManager.getConnection(
                "jdbc:mysql://" + this.host
                        + ":" + this.port
                        + "/" + this.database,
                this.props
        );

        return conn;
    }
}


