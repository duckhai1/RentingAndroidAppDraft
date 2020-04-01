DROP PROCEDURE IF EXISTS updateSportCenterCityId//
CREATE PROCEDURE updateSportCenterCityId(
    in input_sportcenter_id INT,
    in input_city_id INT,
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_sportcenter_id NOT IN (SELECT sportcentre_id FROM sportcenter) THEN 
        SET result_code = 462;
    ELSEIF input_city_id NOT IN (SELECT city_id FROM city) THEN 
        SET result_code = 461;  
    ELSE
        SET result_code = 203;
        UPDATE sportcenter 
        SET city_id = input_city_id
        WHERE sportcentre_id = input_sportcenter_id;
    END IF;
END//
