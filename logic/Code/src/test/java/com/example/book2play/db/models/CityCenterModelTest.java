package com.example.book2play.db.models;

import com.example.book2play.db.exceptions.MySQLException;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CityCenterModelTest extends ModelTestSetup {

    @Test
    public void testCreateCityCenterOnEmptyDatabase() throws Exception {
        var sportCenterId = "Dinh Hoa Center";
        var cityId = "HoChiMinh";
        cityModel.createCity(cityId);
        sportCenterModel.createCityCenter(sportCenterId,cityId);
    }

    @Test
    public void testCreateCityCenterWithDuplicate() throws Exception {
        // CALL createCity for scenario
        var cityId = "HoChiMinh";

        cityModel.createCity(cityId);

        //test
        var expectedCode = 403;
        var sportCenterId = "Dinh Hoa Center";

        sportCenterModel.createCityCenter(sportCenterId,cityId);
        try {
            sportCenterModel.createCityCenter(sportCenterId,cityId);
            fail("Expecting MySQLException with statusCode " + expectedCode);
        } catch (MySQLException e){
            assertEquals(expectedCode,e.getStatusCode());
        }
    }

    @Test
    public void testCreateCityCenterWithInvalidSportCenterId() throws Exception {
        //CALL createCity for scenario
        var cityId = "HoChiMinh";

        cityModel.createCity(cityId);

        //Test
        var expectedCode = 461;
        var sportCenterIds = new ArrayList<String>();
        sportCenterIds.add("Dinh Hoa Center @@");
        sportCenterIds.add("@@@");

        for( var sportCenterId: sportCenterIds ) {
            try {
                sportCenterModel.createCityCenter(sportCenterId, cityId);
                fail("Expecting MySQLException with statusCode " + expectedCode);
            } catch (MySQLException e) {
                assertEquals(expectedCode, e.getStatusCode());
            }
        }
    }

    @Test
    public void TestCreateCityCenterWithInvalidCityId() throws Exception {
        //C Invalid CityId
        var cityId = "S@iGon";

        //test
        var expectedCode = 460;
        var sportCenterId = "Dinh Hoa Center";
        try {
            cityModel.createCity(cityId);
            sportCenterModel.createCityCenter(sportCenterId, cityId);
            fail("Expecting MySQLException with statusCode " + expectedCode);
        }catch (MySQLException e){
            assertEquals(expectedCode,e.getStatusCode());
        }
    }

}
