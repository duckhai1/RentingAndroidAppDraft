package com.example.book2play;

import com.example.book2play.db.MySQLServer;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.CityModel;

import java.io.IOException;
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

    public void runExample() {
        var host = this.mySqlProps.getProperty("mysql_host");
        var port = this.mySqlProps.getProperty("mysql_port");
        var database = this.mySqlProps.getProperty("mysql_database");

        var srv = new MySQLServer(
                host.equals("") ? "127.0.0.1" : host,
                port.equals("") ? "3306" : port,
                database.equals("") ? "book2play" : database,
                this.mySqlProps
        );

        var cityModel = new CityModel(srv);
        try {
            cityModel.createCity("HoChiMinh");
        } catch (MySQLException e) {
            LOG.warning(e.getMessage());
        }
    }

    public static void main(String[] args) {
        var app = new App();
        app.runExample();
    }
}
