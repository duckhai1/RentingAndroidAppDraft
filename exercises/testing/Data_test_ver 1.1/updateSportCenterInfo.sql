/* test updateSportCenterInfo */
/* version 1.2 */ 
    /* version 1.1 fix error code  */ 
    /* ver1.2 sửa tên + định dạng theo database */
/* updateSportCenterName(sportcenter_id, sportcenter_name) / updateSportCenterAddress(sportcenter_id, city_id) */

CREATE PROCEDURE testupdateSportCenterName
    --screnario
    INSERT INTO sportcenters(sportcenter_id, sportcenter_name, city_id) VALUES (100, 'Dinh Hoa Sport Center',001);
    --test
    DECLARE @resultcode; 
    CALL updateSportCenterName(100,'VGU Sport Center',@resultcode); 
    IF (@resultcode = 203) THEN
        --Success updated
        SELECT "Success" AS "result of testupdateNameSportCenter"
    ELSE 
        SELECT "Fail" AS "result of testupdateNameSportCenter"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testUpdateSportCenterNameInvalidSportCenterId
BEGIN 
    --screnario
    INSERT INTO sportcenters(sportcenter_id, sportcenter_name, city_id) VALUES (100, 'Dinh Hoa Sport Center',001);
    --test
    DECLARE @resultcode; 
    CALL updateSportCenterName(101,'VGU SportCenter',@resultcode); 
    IF (@resultcode = 462) THEN
        -- 462 Invalid Sport Center Id
        SELECT "Fail" AS "result of testUpdateSportCenterNameInvalidSportCenterId"
    ELSE 
        SELECT "Success" AS "result of testUpdateSportCenterNameInvalidSportCenterId"
    END IF;
END;

---------------------------------------------------------

CREATE PROCEDURE testUpdateSportCenterNameInvalid
BEGIN 
    --screnario
    INSERT INTO sportcenters(sportcenter_id, sportcenter_name, city_id) VALUES (100, 'Dinh Hoa Sport Center',001);
    --test
    DECLARE @resultcode; 
    CALL updateSportCenterName(111,'123abc',@resultcode); 
    IF (@resultcode = 472) THEN
        -- 472 Invalid name
        SELECT "Fail" AS "result of testUpdateSportCenterNameInvalid"
    ELSE 
        SELECT "Success" AS "result of testUpdateSportCenterNameInvalid"
    END IF;
END;

---------------------------------------------------------
/*
CREATE PROCEDURE testupdateSportCenterAddress
    --screnario
    INSERT INTO sportcenters (sportcenter_id, sc_name, sc_address, city_id) VALUES (111, 'Dinh Hoa Sport Center', 'Dinh Hoa street', '001');
    --test
    DECLARE @resultcode; 
    CALL updateSportCenterAddress(111,'VGU Street',@resultcode); 
    IF (@resultcode = 203) THEN
        --Success updated
        SELECT "Success" AS "result of testupdateSportCenterAddress"
    ELSE 
        SELECT "Fail" AS "result of testupdateSportCenterAddress"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testUpdateSportCenterAddressInvalidSportCenterId
BEGIN 
    --screnario
    INSERT INTO sportcenters (sportcenter_id, sc_name, sc_address, city_id) VALUES (111, 'Dinh Hoa Sport Center', 'Dinh Hoa street', '001');
    --test
    DECLARE @resultcode; 
    CALL updateSportCenterAddress(101,'VGU Street',@resultcode); 
    IF (@resultcode = 462) THEN
        -- 462 Invalid Sport Center Id
        SELECT "Fail" AS "result of testUpdateSportCenterAddressInvalidSportCenterId"
    ELSE 
        SELECT "Success" AS "result of testUpdateSportCenterAddressInvalidSportCenterId"
    END IF;
END;

---------------------------------------------------------

CREATE PROCEDURE testUpdateSportCenterAddressInvalid
BEGIN 
    --screnario
    INSERT INTO sportcenters (sportcenter_id, sc_name, sc_address, city_id) VALUES (111, 'Dinh Hoa Sport Center', 'Dinh Hoa street', '001');
    --test
    DECLARE @resultcode; 
    CALL updateSportCenterAddress(111,'No-where street',@resultcode); 
    IF (@resultcode = 473) THEN
        -- 473 Invalid address
        SELECT "Fail" AS "result of testUpdateSportCenterAddressInvalid"
    ELSE 
        SELECT "Success" AS "result of testUpdateSportCenterAddressInvalid"
    END IF;
END;
*/