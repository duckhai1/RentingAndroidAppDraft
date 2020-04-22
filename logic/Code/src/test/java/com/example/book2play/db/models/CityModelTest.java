package com.example.book2play.db.models;

import com.example.book2play.db.exceptions.MySQLException;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CityModelTest extends AbstractModelTest {

    @Test
    public void testCreateCityOnEmptyDatabase() throws Exception {
        var cityId = "HoChiMinh";
        CITY.createCity(cityId);
    }

    @Test
    public void testCreateCityOnEmptyDataMultipleIds() throws Exception {
        var cities = new ArrayList<String>();
        cities.add("SaiGon");
        cities.add("HaNoi");

        for (var city : cities) {
            CITY.createCity(city);
        }
    }

    @Test
    public void testCreateCityWithDuplicateId() throws Exception {
        final int EXPECTED_CODE = 402;
        var cityId = "HoChiMinh";

        CITY.createCity(cityId);
        try {
            CITY.createCity(cityId);
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateCityWithInvalidIdFormat() {
        final int EXPECTED_CODE = 460;

        var cities = new ArrayList<String>();
        cities.add("Sai Gon");
        cities.add("@SaiGon");

        for (var city : cities) {
            try {
                CITY.createCity(city);
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
            }
        }
    }
}
