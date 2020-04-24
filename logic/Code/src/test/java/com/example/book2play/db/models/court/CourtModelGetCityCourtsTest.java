package com.example.book2play.db.models.court;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.ModelTestSetup;
import com.example.book2play.types.Court;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.fail;

public class CourtModelGetCityCourtsTest extends ModelTestSetup {
    @Before
    public void setupCourts() throws Exception {
        CITY.createCity("HCM");
        CITY.createCity("HaNoi");

        SPORT_CENTER.createCityCenter("Q1", "HCM");
        SPORT_CENTER.createCityCenter("HK", "HaNoi");

        COURT.createCityCenterCourt("P1", "HCM", "Q1");
        COURT.createCityCenterCourt("P2", "HCM", "Q1");
        COURT.createCityCenterCourt("P3", "HCM", "Q1");

        COURT.createCityCenterCourt("P1", "HaNoi", "HK");
        COURT.createCityCenterCourt("P2", "HaNoi", "HK");
    }

    @Test
    public void testGetCityCourtSuccess() throws Exception {
        var testInputs = new ArrayList<String>();
        testInputs.add("HCM");
        testInputs.add("HaNoi");

        var expected = new ArrayList<HashSet<Court>>();
        var case1 = new HashSet<Court>();
        var case2 = new HashSet<Court>();

        case1.add(new Court("P1", "HCM", "Q1"));
        case1.add(new Court("P2", "HCM", "Q1"));
        case1.add(new Court("P3", "HCM", "Q1"));

        case2.add(new Court("P1", "HaNoi", "HK"));
        case2.add(new Court("P2", "HaNoi", "HK"));

        expected.add(case1);
        expected.add(case2);

        for (var i = 0; i < testInputs.size(); i++) {
            var output = new HashSet<>(COURT.getCityCourts(testInputs.get(i)));
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
                COURT.getCityCourts(cityId);
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                Assert.assertEquals("Unexpected error code", EXPECTED_CODE, e.getStatusCode());
            }
        }
    }
}
