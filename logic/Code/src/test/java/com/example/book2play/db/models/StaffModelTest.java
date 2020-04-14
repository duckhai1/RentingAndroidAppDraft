package com.example.book2play.db.models;

import com.example.book2play.db.exceptions.MySQLException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StaffModelTest extends ModelTestSetup {

    @Before
    public void createScenario() throws Exception{
        var cityId = "HoChiMinh";
        var sportCenterId = "DinhHoa";

        CITY.createCity(cityId);
        SPORT_CENTER.createCityCenter(sportCenterId,cityId);
    }

    @Test
    public void createStaffOnEmptyDatabase() throws Exception{
        var staffId = "Phat";

        STAFF.createStaff(staffId,"HoChiMinh","DinhHoa");

    }

    @Test
    public void createStaffWithDuplicate() throws Exception{
        var staffId = "Phat";
        // 406 khong phai 461
        var expectedCode = 461;

        STAFF.createStaff(staffId, "HoChiMinh", "DinhHoa");
        try{
            STAFF.createStaff(staffId, "HoChiMinh", "Dinh Hoa");
            fail("Expected error" + expectedCode);
        }catch (MySQLException e){
            assertEquals(expectedCode, e.getStatusCode());
        }
    }

    @Test
    public void createStaffWithInvalidStaffId() throws Exception{
        var staffId = "Phat@@";
        var expectedCode = 463;

        try {
            STAFF.createStaff(staffId, "HoChiMinh", "DinhHoa");
            fail("expected error: " + expectedCode);
        }
        catch(MySQLException e){
            assertEquals(expectedCode, e.getStatusCode());
        }
    }

    @Test
    public void createStaffWithInvalidCityId() throws Exception{
        var expectedCode = 460;
        var staffId = "Phat";
        var cityId = "HoChiMinh@";
        var sportCenterId = "SaiGonCenter";

        try {
            CITY.createCity(cityId);
            SPORT_CENTER.createCityCenter(sportCenterId, cityId);

            STAFF.createStaff(staffId, cityId, sportCenterId);
            fail("Expected code: " + expectedCode);
        }catch (MySQLException e){
            assertEquals(expectedCode, e.getStatusCode());
        }

    }

    @Test
    public void createStaffWithInvalidSportCenterId() throws Exception{
        var expectedCode = 461;
        var staffId = "Phat";
        var cityId = "HoChiMinh2";
        var sportCenterId = "SaiGonCenter@";

        try {
            CITY.createCity(cityId);
            SPORT_CENTER.createCityCenter(sportCenterId, cityId);

            fail("Expected code: " + expectedCode);
        }catch (MySQLException e){
            assertEquals(expectedCode, e.getStatusCode());
        }

    }
}
