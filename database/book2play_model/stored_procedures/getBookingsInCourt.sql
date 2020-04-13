DELIMITER //

DROP PROCEDURE IF EXISTS getBookingsInCourt//
CREATE PROCEDURE getBookingsInCourt(
	IN inCourtId VARCHAR(100),
	IN inCityId VARCHAR(100),
	IN inSportCenterId VARCHAR(100),
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
    
	IF inSportCenterId NOT IN (
		SELECT sportCenterId 
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 461; -- sportCenter id does not exist
	END IF;
    
    IF inCourtId NOT IN (
		SELECT courtId
		FROM courts
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportCenterId = inSportCenterId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 462; -- invalid court id
	END IF;
    
    SET statusCode = 200;
    
    SELECT bookingId, bookingStartTime, bookingEndTime, courtId, sportCenterId, cityId
	FROM bookings
	NATURAL JOIN courts
	NATURAL JOIN sportCenters
	NATURAL JOIN cities
	WHERE cityId = inCityId
		AND sportCenterId = inSportCenterId
		AND courtId = inCourtId
        AND bookingDate = inBookingDate
		ORDER BY bookingStartTime ASC;
END//

DELIMITER ;
