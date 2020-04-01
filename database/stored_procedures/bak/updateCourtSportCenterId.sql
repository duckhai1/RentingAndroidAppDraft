
DROP PROCEDURE IF EXISTS updateCourtSportCenterLocation//
CREATE PROCEDURE updateCourtSportCenterLocation(
    in input_court_id INT,
    in input_sportcenter_id INT,
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_sportcenter_id NOT IN (SELECT sportcentre_id FROM sportcenter) THEN 
        SET result_code = 462;
    ELSEIF input_court_id NOT IN (SELECT court_id FROM court) THEN 
        SET result_code = 469;  
    ELSE
        SET result_code = 203;
        UPDATE court 
        SET sportcentre_id = input_sportcenter_id
        WHERE court_id = input_court_id;
    END IF;
END//
