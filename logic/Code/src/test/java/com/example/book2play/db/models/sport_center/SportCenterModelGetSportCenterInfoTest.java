package com.example.book2play.db.models.sport_center;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.ModelTestSetup;
import com.example.book2play.types.City;
import com.example.book2play.types.SportCenter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

public class SportCenterModelGetSportCenterInfoTest extends ModelTestSetup {
    @Before
    public void setupMultipleSportCenter() throws Exception {
        CITY.createCity("SaiGon");

        SPORT_CENTER.createCityCenter("Q1", "SaiGon");
    }

    @Test
    public void testGetSportCentersInfoSuccess() throws Exception {
        var output = SPORT_CENTER.getSportCenterInfo("Q1", "SaiGon");
        Assert.assertEquals(new SportCenter("Q1", "SaiGon"), output);
    }

    @Test
    public void testGetSportCentersInfoInvalidCityId() throws Exception {
        final int EXPECTED_CODE = 460;

        var testInputs = new ArrayList<String>();
        testInputs.add("Sa!Gon");
        testInputs.add("#aNoi");
        testInputs.add("#aNo!");
        testInputs.add("DaNang");

        for (var c : testInputs) {
            try {
                var output = SPORT_CENTER.getSportCenterInfo("Q1", c);
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void testGetSportCentersInfoInvalidSportCenterId() throws Exception {
        final int EXPECTED_CODE = 461;

        var testInputs = new ArrayList<String>();
        testInputs.add("Q!");
        testInputs.add("!Q");
        testInputs.add("Q!1");
        testInputs.add("Q3");
        testInputs.add("Q4");

        for (var c : testInputs) {
            try {
                var output = SPORT_CENTER.getSportCenterInfo(c, "SaiGon");
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }
}