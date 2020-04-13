/*
    Creates a sport center for the city identified by the city id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS createCityCenter //
CREATE PROCEDURE createCityCenter (
	IN inSportCenterId VARCHAR(100),
    IN inCityId VARCHAR(100),
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

	IF inSportCenterId REGEXP '[^a-zA-Z0-9]+$' THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 461; -- invalid sportCenter id 
	END IF;

    IF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 460; -- invalid city id
	END IF;
    
    IF inSportCenterId IN (
		SELECT sportCenterId
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 403; -- center already exists 
	END IF;
   
    SET statusCode = 200;

    INSERT INTO sportCenters (sportCenterId, cityPk) 
    VALUES (inSportCenterId, (SELECT cityPk FROM cities WHERE cityId = inCityId));
END//

DELIMITER ;
