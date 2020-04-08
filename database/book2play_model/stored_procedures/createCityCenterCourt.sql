/*
    Creates a court in the center identified by center Id for the city identified by city Id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS createCityCenterCourt //
CREATE PROCEDURE createCityCenterCourt (
	IN inCourtId VARCHAR(100),
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

	IF inCourtId REGEXP '[^a-zA-Z0-9]+$' THEN
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
			SET MYSQL_ERRNO = 461; -- invalid sportcenter id
	END IF;


    IF inCourtId IN (
		SELECT courtId
		FROM courts
		NATURAL JOIN sportcenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportcenterId = inSportcenterId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 404; -- court already exists 
	END IF;
   
    INSERT INTO courts (courtId, sportcenterPk) 
    VALUES (
		inCourtId,
		(
			SELECT sportcenterPk
			FROM sportcenters
			NATURAL JOIN cities
			WHERE cityId = inCityId
				AND sportcenterId = inSportcenterId
		)
	);
    SET statusCode = 200;
END//

DELIMITER ;
