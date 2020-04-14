package com.example.book2play.db.models;

import com.example.book2play.db.exceptions.MySQLException;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SportCenterModelTest extends ModelTestSetup {

    @Test
    public void testCreateCityCenterOnEmptyDatabase() throws Exception {
        var sportCenterId = "DinhHoa";
        var cityId = "HoChiMinh";

        CITY.createCity(cityId);

        SPORT_CENTER.createCityCenter(sportCenterId, cityId);
    }

    @Test
    public void testCreateCityCenterWithDuplicate() throws Exception {
        var expectedCode = 403;

        var cityId = "HoChiMinh";
        CITY.createCity(cityId);

        var sportCenterId = "DinhHoa";
        SPORT_CENTER.createCityCenter(sportCenterId, cityId);

        try {
            SPORT_CENTER.createCityCenter(sportCenterId, cityId);
            fail("Expecting MySQLException with statusCode " + expectedCode);
        } catch (MySQLException e) {
            assertEquals(expectedCode, e.getStatusCode());
        }
    }

    @Test
    public void testCreateCityCenterWithInvalidSportCenterId() throws Exception {
        var cityId = "HoChiMinh";
        CITY.createCity(cityId);

        var expectedCode = 461;
        var sportCenterIds = new ArrayList<String>();
        sportCenterIds.add("Dinh Hoa Center @@");
        sportCenterIds.add("@@@");

        for (var sportCenterId : sportCenterIds) {
            try {
                SPORT_CENTER.createCityCenter(sportCenterId, cityId);
                fail("Expecting MySQLException with statusCode " + expectedCode);
            } catch (MySQLException e) {
                assertEquals(expectedCode, e.getStatusCode());
            }
        }
    }

    @Test
    public void TestCreateCityCenterWithInvalidCityId() {
        var cityId = "S@iGon";
        var expectedCode = 460;
        var sportCenterId = "DinhHoa";

        try {
            CITY.createCity(cityId);
            SPORT_CENTER.createCityCenter(sportCenterId, cityId);
            fail("Expecting MySQLException with statusCode " + expectedCode);
        } catch (MySQLException e) {
            assertEquals(expectedCode, e.getStatusCode());
        }
    }

}
