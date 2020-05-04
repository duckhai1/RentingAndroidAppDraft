package com.example.book2play.db.models.staff;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.DBModelsTestSetup;
import com.example.types.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StaffModelCreateStaffTest extends DBModelsTestSetup {

    @Before
    public void setupSportCentersAndCities() throws Exception {
        CITY.createCity("HCM");
        SPORT_CENTER.createCityCenter("Q1", "HCM");
        SPORT_CENTER.createCityCenter("Q2", "HCM");
    }

    @Test
    public void testCreateStaffSuccess() throws Exception {
        STAFF.createStaff("P1", "HCM", "Q1");
        STAFF.createStaff("P1", "HCM", "Q2");
    }

    @Test
    public void createStaffWithDuplicate() throws Exception {
        final int EXPECTED_CODE = 406;
        STAFF.createStaff("P1", "HCM", "Q1");
        STAFF.createStaff("P1", "HCM", "Q2");

        var testInputs = new ArrayList<Pair<String, String>>();
        testInputs.add(new Pair<>("P1", "Q1"));
        testInputs.add(new Pair<>("P1", "Q2"));

        for (var p : testInputs) {
            try {
                STAFF.createStaff(p.getX(), "HCM", p.getY());
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void createStaffWithInvalidStaffId() throws Exception {
        final int EXPECTED_CODE = 463;
        STAFF.createStaff("P1", "HCM", "Q1");

        var testInputs = new ArrayList<String>();
        testInputs.add("P!");
        testInputs.add("!P");
        testInputs.add("P__1");

        for (var staffId : testInputs) {
            try {
                STAFF.createStaff(staffId, "HCM", "Q1");
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void createStaffWithInvalidCityId() throws Exception {
        final int EXPECTED_CODE = 460;
        STAFF.createStaff("P1", "HCM", "Q1");

        var testInputs = new ArrayList<String>();
        testInputs.add("H(M");
        testInputs.add("HC#");
        testInputs.add("#CM");

        for (var cityId : testInputs) {
            try {
                STAFF.createStaff("P2", cityId, "Q1");
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void createStaffWithInvalidCenterId() throws Exception {
        final int EXPECTED_CODE = 461;
        STAFF.createStaff("P1", "HCM", "Q1");

        var testInputs = new ArrayList<String>();
        testInputs.add("Q!");
        testInputs.add("!Q");
        testInputs.add("Q#1");

        for (var centerId : testInputs) {
            try {
                STAFF.createStaff("P2", "HCM", centerId);
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }
}
