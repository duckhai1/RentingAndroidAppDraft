/*
	Change the name of a sport center given the sport center id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS updateSportcenterId //
CREATE PROCEDURE updateSportcenterId (
    IN newSportcenterId VARCHAR(100),
    IN inSportcenterId VARCHAR(100),
    IN inCityId VARCHAR(100),
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

	IF newSportcenterId REGEXP '[^a-zA-Z0-9]+$' THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 461; -- invalid sportcenter id 
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

    IF newSportcenterId IN (
		SELECT sportcenterId
		FROM sportcenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 403; -- Sportcenter already exists
	END IF;

    UPDATE sportcenters
    SET sportcenterId = newSportcenterId
    WHERE sportcenterId = inSportcenterId
        AND cityPk = (
            SELECT cityPk
            FROM cities
            WHERE cityId = inCityID
        );
END//

DELIMITER ;