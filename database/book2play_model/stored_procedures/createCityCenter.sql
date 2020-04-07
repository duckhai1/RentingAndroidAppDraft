/*
    Creates a sport center for the city identified by the city id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS createCityCenter //
CREATE PROCEDURE createCityCenter (
	IN inSportcenterId VARCHAR(100),
    IN inCityId VARCHAR(100)
)
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

    START TRANSACTION;

	IF inSportcenterId REGEXP '[^a-zA-Z0-9]+$' THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 461; -- invalid sportcenter id 
	END IF;

    IF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 460; -- invalid city id
	END IF;
    
    IF inSportcenterId IN (
		SELECT sportcenterId
		FROM sportcenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 403; -- center already exists 
	END IF;
   
    INSERT INTO sportcenters (sportcenterId, cityPk) 
    VALUES (inSportcenterId, (SELECT cityPk FROM cities WHERE cityId = inCityId));
END//

DELIMITER ;