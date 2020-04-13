DELIMITER //

DROP PROCEDURE IF EXISTS getCities //
CREATE PROCEDURE getCities (
    OUT statusCode INT
)
BEGIN
	SET statusCode = 200; 
	SELECT cityId
	FROM cities
END//

DELIMITER ;