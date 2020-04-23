package com.example.book2play.db.driver;

import com.example.book2play.App;
import com.example.book2play.db.AppDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class MySqlDataSource implements AppDataSource {

    public final String DEFAULT_URL = "jdbc:mysql://localhost:3307/book2play";
    public final String DEFAULT_USERNAME = "root";
    public final String DEFAULT_PASSWORD = "root";

    private final static Logger LOG = Logger.getLogger("DATA_SOURCE");
    private static MySqlDataSource myDataSource;
    private DataSource ds;

    private MySqlDataSource() {
        setupDataSource();
    }

    public static MySqlDataSource getInstance() {
        if (myDataSource == null) {
            myDataSource = new MySqlDataSource();
        }

        return myDataSource;
    }

    public Connection getConnection() throws SQLException {
        LOG.info("Getting connection from: " + ds);
        return ds.getConnection();
    }

    private void setupDataSource() {
        var stream = App.class
                .getClassLoader()
                .getResourceAsStream("mysql_database.properties");

        var props = new Properties();
        try {
            props.load(stream);
            var sqlDs = new MysqlDataSource();
            sqlDs.setURL(props.getProperty("url", DEFAULT_URL));
            sqlDs.setUser(props.getProperty("user", DEFAULT_USERNAME));
            sqlDs.setPassword(props.getProperty("password", DEFAULT_PASSWORD));
            ds = sqlDs;
            LOG.info("Setup connection to MySQL: " + ds);
        } catch (Exception e) {
            LOG.warning("Could not read mysql properties, using default values" + e.getMessage());
        }
    }
}


