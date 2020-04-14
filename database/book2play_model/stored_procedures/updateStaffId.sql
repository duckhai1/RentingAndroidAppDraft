/*
	Update new staff Id of a staff by input current staff id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS updateStaffId //
CREATE PROCEDURE updateStaffId (
    IN newStaffId VARCHAR(100),
    IN inStaffId VARCHAR(100),
    IN inCityId VARCHAR(100),
    IN inSportCenterId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF newStaffId REGEXP '[^a-zA-Z0-9]+' THEN
		SET statusCode = 463; -- invalid staff id 
	ELSEIF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SET statusCode = 460; -- invalid city id
    ELSEIF inSportCenterId NOT IN (
		SELECT sportCenterId
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SET statusCode = 461; -- invalid sportCenter id
	ELSEIF inStaffId NOT IN (
		SELECT staffId
		FROM staffs
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportCenterId = inSportCenterId
	) THEN
		SET statusCode = 463; -- invalid staff id
	ELSEIF newStaffId IN (
		SELECT staffId
		FROM staffs
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportCenterId = inSportCenterId
	) THEN
		SET statusCode = 406; -- staff id already exists
    ELSE
		SET statusCode = 200;
		UPDATE staffs 
		SET staffId = newStaffId
		WHERE staffId = inStaffId
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