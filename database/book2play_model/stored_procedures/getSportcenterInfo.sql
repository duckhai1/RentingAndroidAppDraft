/*
	Get all information about a sport center given the sport center id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getSportcenterInfo//
CREATE PROCEDURE getSportcenterInfo (
	IN inSportcenterId VARCHAR(100),
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
    
	IF inSportcenterId NOT IN (
		SELECT sportcenterId 
		FROM sportcenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 461; -- sportcenter id does not exist
	END IF;

	SELECT *
	FROM sportcenters
	NATURAL JOIN cities
	WHERE sportcenterId = inSportcenterId
		AND cityId = inCityId;
    SET statusCode = 200;
END//

DELIMITER ;