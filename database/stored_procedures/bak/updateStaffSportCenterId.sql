DROP PROCEDURE IF EXISTS updateStaffSportCenterId//
CREATE PROCEDURE updateStaffSportCenterId(
    in input_staff_id INT,
    in input_sportcenter_id INT,
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_staff_id NOT IN (SELECT staff_id FROM staff) THEN 
        SET result_code = 479;
    ELSEIF input_sportcenter_id NOT IN (SELECT sportcenter_id FROM sportcenter) THEN
        SET result_code = 462;
    ELSE
        SET result_code = 203;
        UPDATE staff 
        SET sportcenter_id = input_sportcenter_id
        WHERE staff_id = input_staff_id;
    END IF;
END//