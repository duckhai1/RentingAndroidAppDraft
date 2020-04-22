/*
	Get all bookings of the sport center in the given date
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getSportCenterBookings//
CREATE PROCEDURE getSportCenterBookings(
	IN inSportCenterId VARCHAR(100),
    IN inCityId VARCHAR(100),
    IN inBookingDate DATE,
    OUT statusCode INT
)
BEGIN
	IF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SET statusCode = 460; -- city id does not exist
	ELSEIF inSportCenterId NOT IN (
		SELECT sportCenterId
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SET statusCode = 461; -- sportCenter id does not exist
	ELSEIF NOT EXISTS (
		SELECT *
		FROM bookings
		NATURAL JOIN courts
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportCenterId = inSportCenterId
			AND bookingDate = inBookingDate
	) THEN
		SET statusCode = 466; -- no bookings in the given date
	ELSE
		SET statusCode = 200;
		SELECT bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, playerId
		FROM bookings
		NATURAL JOIN courts
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportCenterId = inSportCenterId
			AND bookingDate = inBookingDate;
	END IF;
END//

DELIMITER ;