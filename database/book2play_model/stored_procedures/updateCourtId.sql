/*
	Change the name of a court given the court id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS updateCourtId//
CREATE PROCEDURE updateCourtId (
    IN newCourtId VARCHAR(100),
    IN inCourtId VARCHAR(100),
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

	IF newCourtId REGEXP '[^a-zA-Z0-9]+$' THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 462; -- invalid court id 
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
			SET MYSQL_ERRNO = 461; -- sport center does not exist
	END IF;

	IF inCourtId NOT IN (
		SELECT courtId
		FROM courts
		NATURAL JOIN sportcenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportcenterId = inSportcenterId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 462; -- invalid court id
	END IF;

	IF newCourtId IN (
		SELECT courtId
		FROM courts
		NATURAL JOIN sportcenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportcenterId = inSportcenterId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 404; -- court id exists
	END IF;

	UPDATE courts 
	SET courtId = newCourtId
	WHERE courtId = inCourtId
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