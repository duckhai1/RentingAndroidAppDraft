package com.example.book2play.db.models.sport_center;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.DBModelsTestSetup;
import com.example.types.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.fail;

public class SportCenterModelUpdateSportCenterIdTest extends DBModelsTestSetup {

    @Before
    public void setupSportCenters() throws Exception {
        CITY.createCity("HCM");

        SPORT_CENTER.createCityCenter("Q1", "HCM");
        SPORT_CENTER.createCityCenter("Q2", "HCM");
    }

    @Test
    public void testUpdateSportCenterIdSuccessfully() throws Exception {
        var fns = new HashSet<Pair<String, String>>();
        fns.add(new Pair<>("Q1", "Q3"));
        fns.add(new Pair<>("Q2", "Q4"));

        for (var fn : fns) {
            SPORT_CENTER.updateSportCenterId(fn.getY(), fn.getX(), "HCM");
        }
    }

    @Test
    public void testUpdateSportCenterIdExists() throws Exception {
        final int EXPECTED_CODE = 403;
        var fns = new HashSet<Pair<String, String>>();
        fns.add(new Pair<>("Q1", "Q2"));
        fns.add(new Pair<>("Q2", "Q1"));

        for (var fn : fns) {
            try {
                SPORT_CENTER.updateSportCenterId(fn.getY(), fn.getX(), "HCM");
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void testUpdateSportCenterInvalidNewId() throws Exception {
        final int EXPECTED_CODE = 461;
        var fns = new HashSet<Pair<String, String>>();
        fns.add(new Pair<>("Q2", "Q!"));
        fns.add(new Pair<>("Q2", "!Q"));
        fns.add(new Pair<>("Q2", "Q__1"));

        for (var fn : fns) {
            try {
                SPORT_CENTER.updateSportCenterId(fn.getY(), fn.getX(), "HCM");
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void testUpdateSportCenterIdIdInvalidInputId() throws Exception {
        final int EXPECTED_CODE = 461;
        var fns = new HashSet<Pair<String, String>>();
        fns.add(new Pair<>("Q!", "Q2"));
        fns.add(new Pair<>("!Q", "Q2"));
        fns.add(new Pair<>("Q__1", "Q2"));

        for (var fn : fns) {
            try {
                SPORT_CENTER.updateSportCenterId(fn.getY(), fn.getX(), "HCM");
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    public void testUpdateSportCenterIdIdInvalidCityId() throws Exception {
        final int EXPECTED_CODE = 460;
        var testInputs = new HashSet<String>();
        testInputs.add("HaNoi");
        testInputs.add("H(M");
        testInputs.add("HCM!");
        testInputs.add("!HCM");

        for (var c : testInputs) {
            try {
                SPORT_CENTER.updateSportCenterId("Q1", "Q3", c);
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }
}
