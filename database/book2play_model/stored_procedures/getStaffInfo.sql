/*
	Get all information about a staff given the staff id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getStaffInfo//
CREATE PROCEDURE getStaffInfo(
	IN inStaffId VARCHAR(100),
	IN inCityId VARCHAR(100),
	IN inSportCenterId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SET statusCode = 460; -- city id does not exist
	ELSEIF inSportCenterId NOT IN (
		SELECT sportCenterId
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SET statusCode = 461; -- sportCenter id does not exist
	ELSEIF inStaffId NOT IN (
		SELECT staffId
		FROM staffs
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportCenterId = inSportCenterId
	) THEN
		SET statusCode = 463; -- staff id does not exists
    ELSE
		SET statusCode = 200;
		SELECT staffId, sportCenterId
		FROM staffs
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportCenterId = inSportCenterId
			AND staffId = inStaffId;
	END IF;
END//

DELIMITER ;