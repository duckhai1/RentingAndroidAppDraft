package com.example.book2play;

import com.example.book2play.db.driver.MySQLDataSource;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.*;
import com.example.book2play.presentation.RestServer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Properties;

public class Main {
    public static void main(String[] args){
        runExample();

        System.out.println("Comming to me my book2play guest, come on!!");
        try {
            RestServer.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runExample(){
        var app = new App();
        //create connection with db
        ClassLoader loader = App.class.getClassLoader();
        InputStream stream = loader.getResourceAsStream("mysql_database.properties");
        var mySqlProps = new Properties();
        try {
            mySqlProps.load(stream);
        } catch (Exception e) {
            System.out.println("Could not get configurations for the database");
        }
        MySQLDataSource db= new MySQLDataSource(mySqlProps);
        BookingModel bookingModel = new BookingModel(db);
        PlayerModel playerModel = new PlayerModel(db);
        CourtModel courtModel = new CourtModel(db);
        SportCenterModel sportCenterModel = new SportCenterModel(db);
        CityModel cityModel = new CityModel(db);

        String bookingId = "booking1";
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
        Date bookingDate = Date.valueOf("2020-05-20");
        Time bookingStartTime = Time.valueOf("08:30:00");
        Time bookingEndTime = Time.valueOf("09:30:00");
        boolean isPaid = false;
        String courtId = "court1";
        String sportCenterId = "center1";
        String cityId = "city1";
        String playerId = "player1";

        try {
            playerModel.createPlayer("player1");
            cityModel.createCity("city1");
            sportCenterModel.createCityCenter("center1","city1");
            courtModel.createCityCenterCourt("court1","city1","center1");
            bookingModel.createBooking(bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, cityId, sportCenterId, courtId, playerId);
        } catch (MySQLException e) {
            e.printStackTrace();
        }
    }
}
