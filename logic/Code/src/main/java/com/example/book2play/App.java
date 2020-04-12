package com.example.book2play;

import com.example.book2play.db.MySQLServer;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.CityModel;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class App {

    final static Logger LOG = Logger.getAnonymousLogger();
    private Properties mySqlProps = new Properties();

    public App() {
        ClassLoader loader = App.class.getClassLoader();
        InputStream stream = loader.getResourceAsStream("mysql_database.properties");

        try {
            mySqlProps.load(stream);
        } catch (Exception e) {
            LOG.warning("Could not read mysql properties" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        var app = new App();
        app.runExample();
    }

    public void runExample() {
        var host = this.mySqlProps.getProperty("mysql_host", "127.0.0.1");
        var port = this.mySqlProps.getProperty("mysql_port", "3306");
        var database = this.mySqlProps.getProperty("mysql_database", "book2play");

        LOG.info("Connection to MySQL database at " + host + ":" + port + "/" + database);

        var srv = new MySQLServer(host, port, database, mySqlProps);
        var cityModel = new CityModel(srv);

        try {
            cityModel.createCity("HaNoi");
        } catch (MySQLException e) {
            LOG.warning(e.getMessage());
        }
    }
}
