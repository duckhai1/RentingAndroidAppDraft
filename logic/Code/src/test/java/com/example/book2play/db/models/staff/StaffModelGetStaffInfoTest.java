package com.example.book2play.db.models.staff;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.ModelTestSetup;
import com.example.book2play.types.Staff;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.fail;

public class StaffModelGetStaffInfoTest extends ModelTestSetup {
    @Before
    public void setupStaffs() throws Exception {
        CITY.createCity("HCM");
        SPORT_CENTER.createCityCenter("Q1", "HCM");

        STAFF.createStaff("P1", "HCM", "Q1");
        STAFF.createStaff("P2", "HCM", "Q1");
    }

    @Test
    public void testGetStaffInfoSuccess() throws Exception {
        var testInputs = new ArrayList<String>();
        testInputs.add("P1");
        testInputs.add("P2");

        var expected = new ArrayList<Staff>();
        expected.add(new Staff("P1", "HCM", "Q1"));
        expected.add(new Staff("P2", "HCM", "Q1"));

        for (var i = 0; i < testInputs.size(); i++) {
            var output = STAFF.getStaffInfo(testInputs.get(i), "HCM", "Q1");
            Assert.assertEquals(expected.get(i), output);
        }
    }

    @Test
    public void testGetStaffInfoInvalidStaffId() throws Exception {
        final int EXPECTED_CODE = 463;
        var testInputs = new ArrayList<String>();
        testInputs.add("P!");
        testInputs.add("P__1");
        testInputs.add("!P1");
        testInputs.add("P3");

        for (String staffId : testInputs) {
            try {
                STAFF.getStaffInfo(staffId, "HCM", "Q1");
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
                STAFF.getStaffInfo("P1", cityId, "Q1");
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void testGetStaffInfoInvalidCitySportCenterId() throws Exception {
        final int EXPECTED_CODE = 461;
        var testInputs = new ArrayList<String>();
        testInputs.add("Q!");
        testInputs.add("!Q");
        testInputs.add("Q#1");
        testInputs.add("Q3");

        for (String centerId : testInputs) {
            try {
                STAFF.getStaffInfo("P1", "HCM", centerId);
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
            }
        }
    }
}
