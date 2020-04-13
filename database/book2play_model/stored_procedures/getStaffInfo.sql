/*
	Get all information about a staff given the staff id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getStaffInfo//
CREATE PROCEDURE getStaffInfo(
	IN inStaffId VARCHAR(100),
	IN inCityId VARCHAR(100),
	IN inSportcenterId VARCHAR(100),
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

	IF inSportcenterId NOT IN (
		SELECT sportcenterId
		FROM sportcenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 461; -- sportcenter id does not exist
	END IF;

	IF inStaffId NOT IN (
		SELECT staffId
		FROM staffs
		NATURAL JOIN sportcenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportcenterId = inSportcenterId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 463; -- staff id does not exists
	END IF;

    SET statusCode = 200;

	SELECT *
	FROM staffs
	NATURAL JOIN sportcenters
	NATURAL JOIN cities
	WHERE cityId = inCityId
		AND sportcenterId = inSportcenterId
		AND staffId = inStaffId;
END//

DELIMITER ;