/*
   Get courts from the sport center identified by the centerId
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getCourtsInSportCenter //
CREATE PROCEDURE getCourtsInSportCenter (
	IN inCityId VARCHAR(100),
	IN inSportCenterId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SET statusCode = 460; -- city id does not exist
	
    ELSEIF inSportCenterId  NOT IN (
		SELECT sportCenterId 
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId) THEN
		SET statusCode = 461; -- invalid sportCenter id
	
	ELSE
		SET statusCode = 200;
		
        SELECT courtId, cityId, sportCenterId
		FROM courts
        NATURAL JOIN cities
		NATURAL JOIN sportCenters
        WHERE cityId = inCityId
		AND sportCenterId = inSportCenterId;
	END IF;
END//

DELIMITER ;