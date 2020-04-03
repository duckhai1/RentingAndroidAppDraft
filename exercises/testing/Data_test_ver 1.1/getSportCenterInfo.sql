/* test getSportCenterInfo interfaces */
/*version 1.1*/
    /* ver 1.1 sửa theo database */
/* getSportCenterInfo(sportcenter_id) */ 

CREATE PROCEDURE testGetSportCenterInfo 
BEGIN 
    --screnario
    INSERT INTO sportcenters(sportcenter_id, sportcenter_name, city_id) VALUES (100, 'Dinh Hoa Sport Center',001);
    --test
    DECLARE @resultcode; 
    CALL getSportCenterInfo(100, @resultcode);
    IF (@resultcode = 200) THEN
        --Success
        SELECT "Success" AS "result of testGetSportCenterInfo"
    ELSE 
        SELECT "Fail" AS "result of testGetSportCenterInfo"
    END IF;
END;    

CREATE PROCEDURE testGetSportCenterInfoInvalidSportCenterId
BEGIN 
    --screnario
    INSERT INTO sportcenters (sportcenter_id, sc_name, sc_address, city_id) VALUES (111, 'Dinh Hoa Sport Center', 'Dinh Hoa street', '001');
    --test
    DECLARE @resultcode; 
    CALL getSportCenterInfo(112, @resultcode);
    IF (@resultcode = 462) THEN
        -- 462 invalid sportcenter_id  
        SELECT "Fail" AS "result of testGetSportCenterInfoInvalidSportCenterId"
    ELSE    
        SELECT "Success" AS "result of testGetSportCenterInfoInvalidSportCenterId"
    END IF;
END;    
