/*
	Change the name of a court given the court id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS updateCourtId//
CREATE PROCEDURE updateCourtId (
    IN newCourtId VARCHAR(100),
    IN inCourtId VARCHAR(100),
    IN inCityId VARCHAR(100),
    IN inSportCenterId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF newCourtId REGEXP '[^a-zA-Z0-9]+$' THEN
		SET statusCode = 462; -- invalid court id 
	ELSEIF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SET statusCode = 460; -- invalid city id
    ELSEIF inSportCenterId NOT IN (
		SELECT sportCenterId
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SET statusCode = 461; -- sport center does not exist
	ELSEIF inCourtId NOT IN (
		SELECT courtId
		FROM courts
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportCenterId = inSportCenterId
	) THEN
		SET statusCode = 462; -- invalid court id
	ELSEIF newCourtId IN (
		SELECT courtId
		FROM courts
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportCenterId = inSportCenterId
	) THEN
		SET statusCode = 404; -- court id exists
    ELSE
		SET statusCode = 200;
		UPDATE courts 
		SET courtId = newCourtId
		WHERE courtId = inCourtId
			AND sportCenterPk = (
				SELECT sportCenterPk
				FROM sportCenters
				NATURAL JOIN cities
				WHERE cityId = inCityId
					AND sportCenterId = inSportCenterId
			);
	END IF;
END//

DELIMITER ;