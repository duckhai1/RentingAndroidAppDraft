DROP PROCEDURE IF EXISTS getStaffInfo//
CREATE PROCEDURE getStaffInfo(
	in input_staff_id INT,
    out result_code INT
)
BEGIN
	IF input_staff_id NOT in(SELECT input_staff_id FROM staff) THEN
			SET result_code = 479;
	ELSE
		    SET result_code = 201;
            SELECT *
            FROM staff
            WHERE staff_id = input_staff_id;
	END IF;
END//
DELIMITER ;
*/