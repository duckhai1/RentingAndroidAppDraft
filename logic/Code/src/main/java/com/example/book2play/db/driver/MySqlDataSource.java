package com.example.book2play.db.driver;

import com.example.book2play.App;
import com.example.book2play.db.AppDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * A class the encapsulates the MySQL database connections,
 * initializes and holds all the information about the data source
 * <p>
 * This ensures the user of the class can only access 1 data source object
 * throughout the lifetime of the process
 */
public class MySqlDataSource implements AppDataSource {

    private final static Logger LOG = Logger.getLogger("DATA_SOURCE");
    private static MySqlDataSource myDataSource;
    public final String DEFAULT_URL = "jdbc:mysql://localhost:3307/book2play";
    public final String DEFAULT_USERNAME = "root";
    public final String DEFAULT_PASSWORD = "root";
    private DataSource ds;

    private MySqlDataSource() {
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

    /**
     * Initialize the object on the first call and return that same object for any subsequent calls
     *
     * @return data source for getting entity information
     */
    public static MySqlDataSource getInstance() {
        if (myDataSource == null) {
            myDataSource = new MySqlDataSource();
        }

        return myDataSource;
    }

    /**
     * Get the connection for send requests to the database
     */
    public Connection getConnection() throws SQLException {
        LOG.info("Getting connection from: " + ds);
        return ds.getConnection();
    }
}


