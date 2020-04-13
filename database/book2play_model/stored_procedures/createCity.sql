/* 
     Creates a city identified by a city id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS createCity //
CREATE PROCEDURE createCity (
	IN inCityId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF inCityId REGEXP '[^a-zA-Z0-9]+' THEN
		SET statusCode = 460;
    ELSEIF inCityId IN (SELECT cityId FROM cities) THEN
		SET statusCode = 402;
	ELSE
		SET statusCode = 200;
		INSERT INTO cities (cityId)
		VALUES (inCityId);
	END IF;
END//

DELIMITER ;
