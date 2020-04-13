/*
    Creates a court in the center identified by center Id for the city identified by city Id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS createCityCenterCourt //
CREATE PROCEDURE createCityCenterCourt (
	IN inCourtId VARCHAR(100),
    IN inCityId VARCHAR(100),
    IN inSportCenterId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF inCourtId REGEXP '[^a-zA-Z0-9]+$' THEN
		SET statusCode = 462; -- invalid court id 
    ELSEIF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SET statusCode = 460; -- invalid city id
   	ELSEIF inSportCenterId NOT IN (
		SELECT sportCenterId
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SET statusCode = 461; -- invalid sportCenter id
	ELSEIF inCourtId IN (
		SELECT courtId
		FROM courts
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportCenterId = inSportCenterId
	) THEN
		SET statusCode = 404; -- court already exists 
	ELSE
		SET statusCode = 200;
		INSERT INTO courts (courtId, sportCenterPk) 
		VALUES (
			inCourtId,
			(
				SELECT sportCenterPk
				FROM sportCenters
				NATURAL JOIN cities
				WHERE cityId = inCityId
					AND sportCenterId = inSportCenterId
			)
		);
	END IF;
END//

DELIMITER ;
