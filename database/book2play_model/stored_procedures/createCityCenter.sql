/*
    Creates a sport center for the city identified by the city id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS createCityCenter //
CREATE PROCEDURE createCityCenter (
	IN inSportCenterId VARCHAR(100),
    IN inCityId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF inSportCenterId REGEXP '[^a-zA-Z0-9]+$' THEN
		SET statusCode = 461; -- invalid sportCenter id 
	ELSEIF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SET statusCode = 460; -- invalid city id
    ELSEIF inSportCenterId IN (
		SELECT sportCenterId
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SET statusCode = 403; -- center already exists 
	ELSE
		SET statusCode = 200;
		INSERT INTO sportCenters (sportCenterId, cityPk) 
		VALUES (inSportCenterId, (SELECT cityPk FROM cities WHERE cityId = inCityId));
	END IF;
END//

DELIMITER ;
