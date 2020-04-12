package com.example.book2play.models;

import com.example.book2play.db.exceptions.MySQLException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CityModelTest extends ModelTestSetup {
    @Test
    public void testCreateCityOnEmptyDatabase() throws Exception {
        cityModel.createCity("HoChiMinh");
    }

    @Test
    public void testCreateCityWithDuplicateId() throws Exception {
        cityModel.createCity("HoChiMinh");

        try {
            cityModel.createCity("HoChiMinh");
            fail("Expecting MySQLException with statusCode 402");
        } catch (MySQLException e) {
            assertEquals(402, e.getStatusCode());
        }
    }
}
