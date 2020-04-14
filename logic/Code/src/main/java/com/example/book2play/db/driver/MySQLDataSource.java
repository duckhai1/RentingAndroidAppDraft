package com.example.book2play.db.driver;

import com.example.book2play.db.AppDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLDataSource implements AppDataSource {
    private String url;
    private Properties props;
    private DataSource ds;

    public MySQLDataSource(Properties props) {
        setupDataSource(props);
        this.props = props;
    }

    public Connection getConnection() throws SQLException {
        return this.ds.getConnection();
    }

    private void setupDataSource(Properties props) {
        var newDs = new MysqlDataSource();
        newDs.setURL(props.getProperty("url"));
        newDs.setUser(props.getProperty("user"));
        newDs.setPassword(props.getProperty("password"));
        ds = newDs;
    }
}


