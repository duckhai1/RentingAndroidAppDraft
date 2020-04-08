/*
	Update new staff Id of a staff by input current staff id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS updateStaffId //
CREATE PROCEDURE updateStaffId (
    IN newStaffId VARCHAR(100),
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

	IF newStaffId REGEXP '[^a-zA-Z0-9]+$' THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 463; -- invalid staff id 
	END IF;

    IF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 460; -- invalid city id
	END IF;
    
    IF inSportcenterId NOT IN (
		SELECT sportcenterId
		FROM sportcenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 461; -- invalid sportcenter id
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
			SET MYSQL_ERRNO = 463; -- invalid staff id
	END IF;

	IF newStaffId IN (
		SELECT staffId
		FROM staffs
		NATURAL JOIN sportcenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportcenterId = inSportcenterId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 406; -- staff id already exists
	END IF;

	UPDATE staffs 
	SET staffId = newStaffId
	WHERE staffId = inStaffId
		AND sportcenterPk = (
			SELECT sportcenterPk
			FROM sportcenters
			NATURAL JOIN cities
			WHERE cityId = inCityId
				AND sportcenterId = inSportCenterId
		);
    SET statusCode = 200;
END//

DELIMITER ;