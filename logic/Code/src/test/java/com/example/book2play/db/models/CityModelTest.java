package com.example.book2play.db.models;

import com.example.book2play.db.exceptions.MySQLException;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CityModelTest extends ModelTestSetup {
    @Test
    public void testCreateCityOnEmptyDatabase() throws Exception {
        var cityId = "HoChiMinh";
        cityModel.createCity(cityId);
    }

    @Test
    public void testCreateCityOnEmptyDataMultipleIds() throws Exception {
        var cities = new ArrayList<String>();
        cities.add("SaiGon");
        cities.add("HaNoi");

        for (var city : cities) {
            cityModel.createCity(city);
        }
    }

    @Test
    public void testCreateCityWithDuplicateId() throws Exception {
        var expectedCode = 402;
        var cityId = "HoChiMinh";

        cityModel.createCity(cityId);
        try {
            cityModel.createCity(cityId);
            fail("Expecting MySQLException with statusCode " + expectedCode);
        } catch (MySQLException e) {
            assertEquals(expectedCode, e.getStatusCode());
        }
    }

    @Test
    public void testCreateCityWithInvalidIdFormat() {
        var expectedCode = 460;
        var cities = new ArrayList<String>();
        cities.add("Sai Gon");
        cities.add("@SaiGon");

        for (var city : cities) {
            try {
                cityModel.createCity(city);
                fail("Expecting MySQLException with statusCode " + expectedCode);
            } catch (MySQLException e) {
                assertEquals(expectedCode, e.getStatusCode());
            }
        }
    }
}
