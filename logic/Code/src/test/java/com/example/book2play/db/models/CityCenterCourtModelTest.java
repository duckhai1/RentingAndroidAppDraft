package com.example.book2play.db.models;
import com.example.book2play.db.exceptions.MySQLException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CityCenterCourtModelTest extends ModelTestSetup {

    //create cityId, sportCenterId
    @Before
    public void createScenario() throws Exception{
        var cityId = "HoChiMinh";
        var sportCenterId = "DinhHoa";

        CITY.createCity(cityId);
        SPORT_CENTER.createCityCenter(sportCenterId, cityId);
    }

    @Test
    public void createCourtOnEmptyDatabase() throws Exception {
        var courtId = "courtA";
        var cityId = "HoChiMinh";
        var sportCenterId = "DinhHoa";

        //screnario
        CITY.createCity(cityId);
        SPORT_CENTER.createCityCenter(sportCenterId, cityId);

        //test
        courtModel.createCityCenterCourt(courtId, cityId, sportCenterId);

    }

    @Test
    public void createCourtWithDuplicate() throws Exception{
        var cityId = "HoChiMinh";
        var sportCenterId = "DinhHoa";
        var courtId = "courtA";
        var expectedCode = 404;

        //screnario
        CITY.createCity(cityId);
        SPORT_CENTER.createCityCenter(sportCenterId, cityId);

        //test
        courtModel.createCityCenterCourt(courtId,cityId,sportCenterId);
        try{
            courtModel.createCityCenterCourt(courtId,cityId,sportCenterId);
            fail("Expecting MySQLException with statusCode " + expectedCode);
        }catch (MySQLException e){
            assertEquals(expectedCode, e.getStatusCode());
        }
    }

    @Test
    public void createCourtWithInvalidCourtId() throws Exception{
        var cityId = "HoChiMinh";
        var sportCenterId = "DinhHoa";
        var courtId = "court@";
        var expectedCode = 462;

        //screnario
        CITY.createCity(cityId);
        SPORT_CENTER.createCityCenter(sportCenterId, cityId);

        //test
        try{
            courtModel.createCityCenterCourt(courtId,cityId,sportCenterId);
            fail("Expecting MySQLException with statusCode " + expectedCode);
        }catch (MySQLException e){
            assertEquals(expectedCode, e.getStatusCode());
        }
    }

    @Test
    public void createCourtWithInvalidCityId() throws Exception{
        var cityId = "HoChiMinh@";
        var sportCenterId = "DinhHoa";
        var courtId = "courtA";
        var expectedCode = 460;

        try{
            CITY.createCity(cityId);
            SPORT_CENTER.createCityCenter(sportCenterId,cityId);
            courtModel.createCityCenterCourt(courtId,cityId,sportCenterId);
            fail("Expecting MySQLException with statusCode " + expectedCode);
        }catch(MySQLException e){
            assertEquals(expectedCode, e.getStatusCode());
        }
    }

    @Test
    public void createCourtWithInvalidCenterId() throws Exception{
        var cityId = "HoChiMinh";
        var sportCenterId = "DinhHo@";
        var courtId = "courtA";
        var expectedCode = 461;

        try {
            CITY.createCity(cityId);
            SPORT_CENTER.createCityCenter(sportCenterId,cityId);
            courtModel.createCityCenterCourt(courtId,cityId,sportCenterId);
            fail("Expecting MySQLException with statusCode " + expectedCode);
        }catch(MySQLException e){
            assertEquals(expectedCode, e.getStatusCode());
        }
    }
}
