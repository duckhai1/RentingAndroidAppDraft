/*
    Creates a staff member of the center identified by cityId and centerId
*/
DELIMITER //

DROP PROCEDURE IF EXISTS createStaff //
CREATE PROCEDURE createStaff (
	IN inStaffId VARCHAR(100),
    IN inCityId VARCHAR(100),
    IN inSportCenterId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF inStaffId REGEXP '[^a-zA-Z0-9]+$' THEN
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
	ELSEIF inStaffId IN (
		SELECT staffId
		FROM staffs
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE sportCenterId = inSportCenterId
			AND cityId = inCityId
	) THEN
		SET statusCode = 406; -- Staff already exists
    ELSE
		SET statusCode = 200;
		INSERT INTO staffs (staffId, sportCenterPk)
		VALUES (
			inStaffId,
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
