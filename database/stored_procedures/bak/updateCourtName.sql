
DROP PROCEDURE IF EXISTS updateCourtName//
CREATE PROCEDURE updateCourtName(
    in input_court_id INT,
    in input_name VARCHAR(50),
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_court_id NOT IN (SELECT court_id FROM court) THEN 
        SET result_code = 469; 
    ELSE
        SET result_code = 203;
        UPDATE court 
        SET court_name = input_name
        WHERE court_id = input_court_id;
    END IF;
END//