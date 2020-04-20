/*
   Get sport centers from a city identified by the cityId
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getCentersInCity //
CREATE PROCEDURE getCentersInCity (
	IN InCityId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SET statusCode = 460; -- invalid city id
	
	ELSE
		SET statusCode = 200;
		
        SELECT sportCenterId, cityId
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = InCityId;
	END IF;
END//

DELIMITER ;