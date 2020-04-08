/*
    Creates a staff member of the center identified by cityId and centerId
*/
DELIMITER //

DROP PROCEDURE IF EXISTS createStaff //
CREATE PROCEDURE createStaff (
	IN inStaffId VARCHAR(100),
    IN inCityId VARCHAR(100),
    IN inSportcenterId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

    START TRANSACTION;

	IF inStaffId REGEXP '[^a-zA-Z0-9]+$' THEN
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

	IF inStaffId IN (
		SELECT staffId
		FROM staffs
		NATURAL JOIN sportcenters
		NATURAL JOIN cities
		WHERE sportcenterId = inSportcenterId
			AND cityId = inCityId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 406; -- Staff already exists
	END IF;
    
    INSERT INTO staffs (staffId, sportcenterPk)
	VALUES (
		inStaffId,
		(
			SELECT sportcenterPk
			FROM sportcenters
			NATURAL JOIN cities
			WHERE cityId = inCityId
				AND sportcenterId = inSportcenterId
		)
	);
END//

DELIMITER ;
