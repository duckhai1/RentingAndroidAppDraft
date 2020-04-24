package com.example.book2play.db.models.sport_center;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.ModelTestSetup;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SportCenterModelCreateCityCenterTest extends ModelTestSetup {

    @Before
    public void setupMultipleCities() throws Exception {
        CITY.createCity("SaiGon");
        CITY.createCity("HaNoi");
    }

    @Test
    public void testCreateCityCenterOnEmptyDatabase() throws Exception {
        SPORT_CENTER.createCityCenter("Q1", "SaiGon");
        SPORT_CENTER.createCityCenter("Q2", "SaiGon");
        SPORT_CENTER.createCityCenter("Q1", "HaNoi");
        SPORT_CENTER.createCityCenter("Q2", "HaNoi");
        SPORT_CENTER.createCityCenter("Q3", "HaNoi");
    }

    @Test
    public void testCreateCityCenterWithDuplicate() throws Exception {
        final int EXPECTED_CODE = 403;
        SPORT_CENTER.createCityCenter("Q1", "SaiGon");

        try {
            SPORT_CENTER.createCityCenter("Q1", "SaiGon");
            fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
        } catch (MySQLException e) {
            assertEquals(EXPECTED_CODE, e.getStatusCode());
        }
    }

    @Test
    public void testCreateCityCenterWithInvalidCityId() {
        final int EXPECTED_CODE = 460;

        var testInputs = new ArrayList<String>();
        testInputs.add("DaNang!");
        testInputs.add("HaNo!");
        testInputs.add("#CM");
        testInputs.add("H(M");

        for (var c : testInputs) {
            try {
                SPORT_CENTER.createCityCenter("Q1", c);
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void testCreateCityCenterInvalidCenterId() throws Exception {
        final int EXPECTED_CODE = 461;
        var testInputs = new ArrayList<String>();
        testInputs.add("Q!");
        testInputs.add("!Q");
        testInputs.add("Q!1");

        for (var c : testInputs) {
            try {
                SPORT_CENTER.createCityCenter(c, "SaiGon");
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }
}
