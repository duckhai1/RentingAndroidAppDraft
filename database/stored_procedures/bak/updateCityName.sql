-- update city information
DROP PROCEDURE IF EXISTS updateCityName//
CREATE PROCEDURE updateCityName(
    in input_city_id INT,
    in input_name VARCHAR(50),
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_city_id NOT IN (SELECT city_id FROM city) THEN 
        SET result_code = 461;
    ELSE
        SET result_code = 203;
        UPDATE city 
        SET city_name = input_name
        WHERE city_id = input_city_id;
    END IF;
END//