package com.example.book2play.db.models;

import com.example.book2play.db.exceptions.MySQLException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CourtModelTest extends AbstractModelTest {

    @Test
    public void createCourtOnEmptyDatabase() throws Exception {
        var courtId = "courtA";
        var cityId = "HoChiMinh";
        var sportCenterId = "DinhHoa";

        //screnario
        CITY.createCity(cityId);
        SPORT_CENTER.createCityCenter(sportCenterId, cityId);

        //test
        COURT.createCityCenterCourt(courtId, cityId, sportCenterId);



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
        COURT.createCityCenterCourt(courtId,cityId,sportCenterId);
        try{
            COURT.createCityCenterCourt(courtId,cityId,sportCenterId);
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
            COURT.createCityCenterCourt(courtId,cityId,sportCenterId);
            fail("Expecting MySQLException with statusCode " + expectedCode);
        }catch (MySQLException e){
            assertEquals(expectedCode, e.getStatusCode());
        }
    }

    @Test
    public void createCourtWithInvalidCityId() {
        var cityId = "HoChiMinh@";
        var sportCenterId = "DinhHoa";
        var courtId = "courtA";
        var expectedCode = 460;

        try {
            CITY.createCity(cityId);
            SPORT_CENTER.createCityCenter(sportCenterId, cityId);
            COURT.createCityCenterCourt(courtId, cityId, sportCenterId);

            fail("Expecting MySQLException with statusCode " + expectedCode);
        }catch(MySQLException e){
            assertEquals(expectedCode, e.getStatusCode());
        }
    }

    @Test
    public void createCourtWithInvalidCenterId() {
        var cityId = "HoChiMinh";
        var sportCenterId = "DinhHo@";
        var courtId = "courtA";
        var expectedCode = 461;

        try {
            CITY.createCity(cityId);
            SPORT_CENTER.createCityCenter(sportCenterId, cityId);
            COURT.createCityCenterCourt(courtId, cityId, sportCenterId);
            fail("Expecting MySQLException with statusCode " + expectedCode);
        }catch(MySQLException e){
            assertEquals(expectedCode, e.getStatusCode());
        }
    }
}
