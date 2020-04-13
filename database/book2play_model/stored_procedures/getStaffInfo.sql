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
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO;
		SET statusCode = @p1;
		ROLLBACK;
	END;

	START TRANSACTION;

	IF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 460; -- city id does not exist
	END IF;

	IF inSportCenterId NOT IN (
		SELECT sportCenterId
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 461; -- sportCenter id does not exist
	END IF;

	IF inStaffId NOT IN (
		SELECT staffId
		FROM staffs
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportCenterId = inSportCenterId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 463; -- staff id does not exists
	END IF;

    SET statusCode = 200;

	SELECT staffId, sportCenterId
	FROM staffs
	NATURAL JOIN sportCenters
	NATURAL JOIN cities
	WHERE cityId = inCityId
		AND sportCenterId = inSportCenterId
		AND staffId = inStaffId;
END//

DELIMITER ;