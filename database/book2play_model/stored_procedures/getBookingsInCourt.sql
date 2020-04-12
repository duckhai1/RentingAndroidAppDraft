DELIMITER //

DROP PROCEDURE IF EXISTS getBookingsInCourt//
CREATE PROCEDURE getBookingsInCourt(
	IN inCourtId VARCHAR(100),
	IN inCityId VARCHAR(100),
	IN inSportcenterId VARCHAR(100),
    IN inBookingDate Date,
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
    
    SET statusCode = 200;
    
    SELECT bookingId, bookingStartTime, bookingEndTime, courtId, sportcenterId, cityId
	FROM bookings
	NATURAL JOIN courts
	NATURAL JOIN sportcenters
	NATURAL JOIN cities
	WHERE cityId = inCityId
		AND sportcenterId = inSportcenterId
		AND courtId = inCourtId
        AND bookingDate = inBookingDate
		ORDER BY bookingStartTime ASC;
END//

DELIMITER ;
