package com.example.book2play.db.models.court;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.DBModelsTestSetup;
import com.example.book2play.types.Court;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.fail;

public class CourtModelGetSportCenterCourtsTest extends DBModelsTestSetup {
    @Before
    public void setupCourts() throws Exception {
        CITY.createCity("HCM");

        SPORT_CENTER.createCityCenter("Q1", "HCM");
        SPORT_CENTER.createCityCenter("Q2", "HCM");

        COURT.createCityCenterCourt("P1", "HCM", "Q1");
        COURT.createCityCenterCourt("P2", "HCM", "Q1");
        COURT.createCityCenterCourt("P3", "HCM", "Q1");

        COURT.createCityCenterCourt("P1", "HCM", "Q2");
        COURT.createCityCenterCourt("P2", "HCM", "Q2");
    }

    @Test
    public void testGetSportCenterCourtSuccess() throws Exception {
        var testInputs = new ArrayList<String>();
        testInputs.add("Q1");
        testInputs.add("Q2");

        var expected = new ArrayList<HashSet<Court>>();
        var case1 = new HashSet<Court>();
        var case2 = new HashSet<Court>();

        case1.add(new Court("P1", "HCM", "Q1"));
        case1.add(new Court("P2", "HCM", "Q1"));
        case1.add(new Court("P3", "HCM", "Q1"));

        case2.add(new Court("P1", "HCM", "Q2"));
        case2.add(new Court("P2", "HCM", "Q2"));

        expected.add(case1);
        expected.add(case2);

        for (var i = 0; i < testInputs.size(); i++) {
            var output = new HashSet<>(COURT.getSportCenterCourts(testInputs.get(i), "HCM"));
            Assert.assertEquals(expected.get(i), output);
        }
    }

    @Test
    public void testCityCourtInvalidCityId() throws Exception {
        final int EXPECTED_CODE = 460;
        var testInputs = new ArrayList<String>();
        testInputs.add("DaNang");
        testInputs.add("HaNo!");
        testInputs.add("H@Noi");
        testInputs.add("#CM");

        for (String cityId : testInputs) {
            try {
                COURT.getSportCenterCourts("Q1", cityId);
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
            }
        }
    }

    @Test
    public void testCityCourtInvalidSportCenterId() throws Exception {
        final int EXPECTED_CODE = 461;
        var testInputs = new ArrayList<String>();
        testInputs.add("Q!");
        testInputs.add("!Q");
        testInputs.add("Q#1");
        testInputs.add("Q3");

        for (String centerId : testInputs) {
            try {
                COURT.getSportCenterCourts(centerId, "HCM");
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
            }
        }
    }
}
