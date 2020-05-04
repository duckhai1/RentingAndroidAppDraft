package com.example.book2play.db.models.sport_center;

import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.DBModelsTestSetup;
import com.example.book2play.types.SportCenter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SportCenterModelGetCitySportCentersTest extends DBModelsTestSetup {
    @Before
    public void setupMultipleSportCenter() throws Exception {
        CITY.createCity("SaiGon");
        CITY.createCity("HaNoi");

        SPORT_CENTER.createCityCenter("Q1", "SaiGon");
        SPORT_CENTER.createCityCenter("Q2", "SaiGon");
        SPORT_CENTER.createCityCenter("Q3", "SaiGon");
        SPORT_CENTER.createCityCenter("HK", "HaNoi");
        SPORT_CENTER.createCityCenter("CG", "HaNoi");
    }

    @Test
    public void testGetCityCentersSuccess() throws Exception {
        var expected = new ArrayList<HashSet<SportCenter>>();

        var case1 = new HashSet<SportCenter>();
        case1.add(new SportCenter("Q1", "SaiGon"));
        case1.add(new SportCenter("Q2", "SaiGon"));
        case1.add(new SportCenter("Q3", "SaiGon"));
        expected.add(case1);

        var case2 = new HashSet<SportCenter>();
        case2.add(new SportCenter("HK", "HaNoi"));
        case2.add(new SportCenter("CG", "HaNoi"));
        expected.add(case2);

        var testInputs = new ArrayList<String>();
        testInputs.add("SaiGon");
        testInputs.add("HaNoi");

        for (var i = 0; i < testInputs.size(); i++) {
            var output = new HashSet<>(SPORT_CENTER.getCitySportCenters(testInputs.get(i)));
            Assert.assertEquals(expected.get(i), output);
        }
    }

    @Test
    public void testGetCityInvalidCityId() throws Exception {
        final int EXPECTED_CODE = 460;

        var testInputs = new ArrayList<String>();
        testInputs.add("Sa!Gon");
        testInputs.add("#aNoi");
        testInputs.add("#aNo!");
        testInputs.add("DaNang");

        for (var i = 0; i < testInputs.size(); i++) {
            try {
                var output = new HashSet<>(SPORT_CENTER.getCitySportCenters(testInputs.get(i)));
                fail("Expecting MySQLException with statusCode " + EXPECTED_CODE);
            } catch (MySQLException e) {
                assertEquals(EXPECTED_CODE, e.getStatusCode());
            }
        }
    }
}