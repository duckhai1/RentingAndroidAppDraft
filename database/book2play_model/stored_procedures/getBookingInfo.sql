/*
	Get all information about a booking given the booking id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getBookingInfo//
CREATE PROCEDURE getBookingInfo (
    IN inBookingId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
    IF inBookingId NOT IN (SELECT bookingId FROM bookings ) THEN
		SET statusCode = 465; -- invalid booking id
	ELSE
		SET statusCode = 200; 
		SELECT bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, playerId
		FROM bookings
			NATURAL JOIN players
			NATURAL JOIN courts
			NATURAL JOIN sportCenters
			NATURAL JOIN cities
		WHERE bookingId = inBookingId;
	END IF;
END//

DELIMITER ;