/*
	Get all bookings for a given date and city id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getBookings//
CREATE PROCEDURE getBookings (
    IN inCityId VARCHAR(100),
    IN inBookingDate DATE,
    OUT statusCode INT
)
BEGIN
    IF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SET statusCode = 460; -- invalid city id
    ELSE
		SET statusCode = 200;
		SELECT bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, playerId
		FROM bookings
			NATURAL JOIN players
			NATURAL JOIN courts
			NATURAL JOIN sportCenters
			NATURAL JOIN cities
		WHERE cityId = inCityId
			AND bookingDate = inBookingDate ;
	END IF;
END//

DELIMITER ;
