DELIMITER //

DROP PROCEDURE IF EXISTS getCityCourts//
CREATE PROCEDURE getCityCourts(
	IN inCityId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
    IF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SET statusCode = 460; -- invalid city id
    ELSE 
		SELECT courtId, cityId, sportCenterId
		FROM courts
		NATURAL JOIN cities
		NATURAL JOIN sportCenters
		WHERE cityId = inCityId;
	END IF;
END//

DELIMITER ;
    
    
    
    