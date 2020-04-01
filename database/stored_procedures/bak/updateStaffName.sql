DROP PROCEDURE IF EXISTS updateStaffName//
CREATE PROCEDURE updateStaffName(
    in input_staff_id INT,
    in input_name VARCHAR(50),
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_staff_id NOT IN (SELECT staff_id FROM staff) THEN 
        SET result_code = 479;
    ELSE
        SET result_code = 203;
        UPDATE staff 
        SET s_name = input_name
        WHERE staff_id = input_staff_id;
    END IF;
END//