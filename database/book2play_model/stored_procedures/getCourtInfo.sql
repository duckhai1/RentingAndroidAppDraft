/*
	Get all information about a court given the court id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getCourtInfo//
CREATE PROCEDURE getCourtInfo(
	IN inCourtId VARCHAR(100),
	IN inCityId VARCHAR(100),
	IN inSportCenterId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
    IF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SET statusCode = 460; -- invalid city id
    ELSEIF inSportCenterId NOT IN (
		SELECT sportCenterId
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SET statusCode = 461; -- sportCenter does not exist 
	ELSEIF inCourtId NOT IN (
		SELECT courtId
		FROM courts
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportCenterId = inSportCenterId
	) THEN
		SET statusCode = 462; -- invalid court id
    ELSE
		SET statusCode = 200;
		SELECT courtId, cityId, sportCenterId
		FROM courts
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportCenterId = inSportCenterId
			AND courtId = inCourtId;
	END IF;
END//

DELIMITER ;