DELIMITER //

DROP PROCEDURE IF EXISTS getCourtBookings//
CREATE PROCEDURE getCourtBookings(
	IN inCourtId VARCHAR(100),
	IN inCityId VARCHAR(100),
	IN inSportCenterId VARCHAR(100),
    IN inBookingDate Date,
    OUT statusCode INT
)
BEGIN
    IF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SET statusCode = 460; -- invalid city id
	ELSEIF inSportCenterId NOT IN (
		SELECT sportCenterId 
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SET statusCode = 461; -- sportCenter id does not exist
    ELSEIF inCourtId NOT IN (
		SELECT courtId
		FROM courts
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportCenterId = inSportCenterId
	) THEN
		SET statusCode = 462; -- invalid court id
    ELSE
		SET statusCode = 200;
		SELECT bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, playerId
		FROM bookings
			NATURAL JOIN players
			NATURAL JOIN courts
			NATURAL JOIN sportCenters
			NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportCenterId = inSportCenterId
			AND courtId = inCourtId
			AND bookingDate = inBookingDate
		ORDER BY bookingStartTime ASC;
	END IF;
END//

DELIMITER ;
