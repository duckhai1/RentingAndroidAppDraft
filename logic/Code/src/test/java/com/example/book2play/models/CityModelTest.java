package com.example.book2play.models;

import com.example.book2play.db.exceptions.MySQLException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CityModelTest extends ModelTestSetup {
    @Test
    public void testCreateCityOnEmptyDatabase() throws Exception {
        try {
            cityModel.createCity("HoChiMinh");
        } catch (MySQLException e) {
            throw new Exception("Could not create city", e);
        }
    }

    @Test
    public void testCreateCityWithDuplicateId() throws Exception {
        try {
            cityModel.createCity("HoChiMinh");
        } catch (MySQLException e) {
            throw new Exception("Could not create city", e);
        }

        try {
            cityModel.createCity("HoChiMinh");
            fail("Expecting an exception");
        } catch (MySQLException e) {
            assertEquals(e.getStatusCode(), 402);
        }
    }
}
