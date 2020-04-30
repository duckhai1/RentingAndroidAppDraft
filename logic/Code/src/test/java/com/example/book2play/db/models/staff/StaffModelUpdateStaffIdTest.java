package com.example.book2play.db.models.staff;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.DBModelsTestSetup;
import com.example.types.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.fail;

public class StaffModelUpdateStaffIdTest extends DBModelsTestSetup {
    @Before
    public void setupStaffs() throws Exception {
        CITY.createCity("HCM");
        SPORT_CENTER.createCityCenter("Q1", "HCM");

        STAFF.createStaff("P1", "HCM", "Q1");
        STAFF.createStaff("P2", "HCM", "Q1");
    }

    @Test
    public void testUpdateStaffIdSuccess() throws Exception {
        var testInputs = new ArrayList<Pair<String, String>>();
        testInputs.add(new Pair("P1", "P3"));
        testInputs.add(new Pair("P2", "P4"));

        for (var input : testInputs) {
            STAFF.updateStaffId(input.getY(), input.getX(), "HCM", "Q1");
        }
    }

    @Test
    public void testGetStaffInfoInvalidStaffId() throws Exception {
        final int EXPECTED_CODE = 463;
        var testInputs = new ArrayList<Pair<String, String>>();
        testInputs.add(new Pair("P!", "P3"));
        testInputs.add(new Pair("P__1", "P4"));
        testInputs.add(new Pair("!P1", "P3"));
        testInputs.add(new Pair("P3", "P4"));

        for (var input : testInputs) {
            try {
                STAFF.updateStaffId(input.getY(), input.getX(), "HCM", "Q1");
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void testUpdateStaffInvalidNewStaffId() throws Exception {
        final int EXPECTED_CODE = 463;
        var testInputs = new ArrayList<Pair<String, String>>();
        testInputs.add(new Pair("P1", "P!"));
        testInputs.add(new Pair("P2", "P__1"));
        testInputs.add(new Pair("P1", "!P1"));
        testInputs.add(new Pair("P2", "!PPP#P!"));

        for (var input : testInputs) {
            try {
                STAFF.updateStaffId(input.getY(), input.getX(), "HCM", "Q1");
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void testGetStaffInfoInvalidCityId() throws Exception {
        final int EXPECTED_CODE = 460;
        var testInputs = new ArrayList<String>();
        testInputs.add("DaNang");
        testInputs.add("HaNo!");
        testInputs.add("H@Noi");
        testInputs.add("#CM");

        for (String cityId : testInputs) {
            try {
                STAFF.updateStaffId("P1", "P3", cityId, "Q1");
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void testUpdateStaffInvalidCitySportCenterId() throws Exception {
        final int EXPECTED_CODE = 461;
        var testInputs = new ArrayList<String>();
        testInputs.add("Q!");
        testInputs.add("!Q");
        testInputs.add("Q#1");
        testInputs.add("Q3");

        for (String centerId : testInputs) {
            try {
                STAFF.updateStaffId("P1", "P3", "HCM", centerId);
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
            }
        }
    }
}
