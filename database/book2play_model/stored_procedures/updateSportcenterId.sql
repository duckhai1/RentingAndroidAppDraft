/*
	Change the name of a sport center given the sport center id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS updateSportCenterId //
CREATE PROCEDURE updateSportCenterId (
    IN newSportCenterId VARCHAR(100),
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

	IF newSportCenterId REGEXP '[^a-zA-Z0-9]+$' THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 461; -- invalid sportCenter id 
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

    IF newSportCenterId IN (
		SELECT sportCenterId
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 403; -- SportCenter already exists
	END IF;

    SET statusCode = 200;

    UPDATE sportCenters
    SET sportCenterId = newSportCenterId
    WHERE sportCenterId = inSportCenterId
        AND cityPk = (
            SELECT cityPk
            FROM cities
            WHERE cityId = inCityID
        );
END//

DELIMITER ;