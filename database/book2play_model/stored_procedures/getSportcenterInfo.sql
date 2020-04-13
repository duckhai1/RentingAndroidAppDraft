/*
	Get all information about a sport center given the sport center id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getSportCenterInfo//
CREATE PROCEDURE getSportCenterInfo (
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
			SET MYSQL_ERRNO = 461; -- sportCenter id does not exist
	END IF;

    SET statusCode = 200;

	SELECT sportCenterId, cityId
	FROM sportCenters
	NATURAL JOIN cities
	WHERE sportCenterId = inSportCenterId
		AND cityId = inCityId;
END//

DELIMITER ;