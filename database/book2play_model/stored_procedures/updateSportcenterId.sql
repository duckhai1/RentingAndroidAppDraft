/*
	Change the name of a sport center given the sport center id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS updateSportCenterId //
CREATE PROCEDURE updateSportCenterId (
    IN newSportCenterId VARCHAR(100),
    IN inSportCenterId VARCHAR(100),
    IN inCityId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF newSportCenterId REGEXP '[^a-zA-Z0-9]+$' THEN
		SET statusCode = 461; -- invalid sportCenter id 
	ELSEIF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SET statusCode = 460; -- invalid city id
	ELSEIF inSportCenterId NOT IN (
		SELECT sportCenterId
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SET statusCode = 461; -- invalid sportCenter id
	ELSEIF newSportCenterId IN (
		SELECT sportCenterId
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SET statusCode = 403; -- SportCenter already exists
    ELSE
		SET statusCode = 200;
		UPDATE sportCenters
		SET sportCenterId = newSportCenterId
		WHERE sportCenterId = inSportCenterId
			AND cityPk = (
				SELECT cityPk
				FROM cities
				WHERE cityId = inCityID
			);
	END IF;
END//

DELIMITER ;