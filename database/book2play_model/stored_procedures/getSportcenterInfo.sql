/*
	Get all information about a sport center given the sport center id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getSportCenterInfo//
CREATE PROCEDURE getSportCenterInfo (
	IN inSportCenterId VARCHAR(100),
	IN inCityId VARCHAR(100),
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
		SET statusCode = 461; -- sportCenter id does not exist
    ELSE
		SET statusCode = 200;
		SELECT sportCenterId, cityId
		FROM sportCenters
		NATURAL JOIN cities
		WHERE sportCenterId = inSportCenterId
			AND cityId = inCityId;
	END IF;
END//

DELIMITER ;