DELIMITER //

DROP PROCEDURE IF EXISTS getCourtsInCity//
CREATE PROCEDURE getCourtsInCity(
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
    
    SELECT courtId, cityId, sportcenterId
	FROM courts
	NATURAL JOIN cities
    NATURAL JOIN sportcenters
	WHERE cityId = inCityId;
END//

DELIMITER ;
    
    
    
    