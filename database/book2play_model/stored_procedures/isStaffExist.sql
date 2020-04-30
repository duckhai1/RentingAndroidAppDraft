/*
    Check if staff exist or not
*/
DELIMITER //

DROP PROCEDURE IF EXISTS isStaffExist //
CREATE PROCEDURE isStaffExist (
	IN inStaffId VARCHAR(100),
    IN inCityId VARCHAR(100),
    IN inSportCenterId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF inStaffId NOT IN (
		SELECT staffId
		FROM staffs
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE sportCenterId = inSportCenterId
			AND cityId = inCityId
	) THEN
		SET statusCode = 463; -- Staff does not exist
    ELSEIF inStaffId IN (
		SELECT staffId
		FROM staffs
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE sportCenterId = inSportCenterId
			AND cityId = inCityId
	) THEN
		SET statusCode = 406; -- Staff already exists

	END IF;
END//
DELIMITER ;
