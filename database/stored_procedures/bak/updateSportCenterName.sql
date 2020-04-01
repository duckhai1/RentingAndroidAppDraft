DROP PROCEDURE IF EXISTS updateSportCenterName//
CREATE PROCEDURE updateSportCenterName(
    in input_sportcenter_id INT,
    in input_name VARCHAR(50),
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_sportcenter_id NOT IN (SELECT sportcentre_id FROM sportcenter) THEN 
        SET result_code = 462;
    ELSE
        SET result_code = 203;
        UPDATE sportcenter 
        SET sc_name = input_name
        WHERE sportcentre_id = input_sportcenter_id;
    END IF;
END//