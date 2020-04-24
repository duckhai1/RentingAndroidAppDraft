package com.example.book2play.db.models.court;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.ModelTestSetup;
import com.example.test_utils.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CourtModelCreateCourtTest extends ModelTestSetup {

    @Before
    public void setupSportCentersAndCities() throws Exception {
        CITY.createCity("HCM");
        SPORT_CENTER.createCityCenter("Q1", "HCM");
        SPORT_CENTER.createCityCenter("Q2", "HCM");
    }

    @Test
    public void testCreateCourtSuccess() throws Exception {
        COURT.createCityCenterCourt("P1", "HCM", "Q1");
        COURT.createCityCenterCourt("P1", "HCM", "Q2");
    }

    @Test
    public void createCityCenterCourtWithDuplicate() throws Exception {
        final int EXPECTED_CODE = 404;
        COURT.createCityCenterCourt("P1", "HCM", "Q1");
        COURT.createCityCenterCourt("P1", "HCM", "Q2");

        var testInputs = new ArrayList<Pair<String, String>>();
        testInputs.add(new Pair<>("P1", "Q1"));
        testInputs.add(new Pair<>("P1", "Q2"));

        for (var p : testInputs) {
            try {
                COURT.createCityCenterCourt(p.getX(), "HCM", p.getY());
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void createCityCenterCourtWithInvalidCourtId() throws Exception {
        final int EXPECTED_CODE = 462;
        COURT.createCityCenterCourt("P1", "HCM", "Q1");

        var testInputs = new ArrayList<String>();
        testInputs.add("P!");
        testInputs.add("!P");
        testInputs.add("P__1");

        for (var courtId : testInputs) {
            try {
                COURT.createCityCenterCourt(courtId, "HCM", "Q1");
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void createCityCenterCourtWithInvalidCityId() throws Exception {
        final int EXPECTED_CODE = 460;
        COURT.createCityCenterCourt("P1", "HCM", "Q1");

        var testInputs = new ArrayList<String>();
        testInputs.add("H(M");
        testInputs.add("HC#");
        testInputs.add("#CM");

        for (var cityId : testInputs) {
            try {
                COURT.createCityCenterCourt("P2", cityId, "Q1");
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void createCityCenterCourtWithInvalidCenterId() throws Exception {
        final int EXPECTED_CODE = 461;
        COURT.createCityCenterCourt("P1", "HCM", "Q1");

        var testInputs = new ArrayList<String>();
        testInputs.add("Q!");
        testInputs.add("!Q");
        testInputs.add("Q#1");

        for (var centerId : testInputs) {
            try {
                COURT.createCityCenterCourt("P2", "HCM", centerId);
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }
}
