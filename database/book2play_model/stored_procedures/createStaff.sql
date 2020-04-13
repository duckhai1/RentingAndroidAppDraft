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
	    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO;
		SET statusCode = @p1;
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
    
    IF inSportCenterId NOT IN (
		SELECT sportCenterId
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 461; -- invalid sportCenter id
	END IF;

	IF inStaffId IN (
		SELECT staffId
		FROM staffs
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE sportCenterId = inSportCenterId
			AND cityId = inCityId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 406; -- Staff already exists
	END IF;

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
END//

DELIMITER ;
