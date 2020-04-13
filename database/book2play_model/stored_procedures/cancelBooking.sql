/*
	Player with a given player Id cancels a booking with a given booking id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS cancelBooking//
CREATE PROCEDURE cancelBooking (
    IN inBookingId VARCHAR(100),
	IN inPlayerId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF inPlayerId NOT IN (SELECT playerId FROM players) THEN
		SET statusCode =464; -- Player does not exist
	ELSEIF inBookingId NOT IN (SELECT bookingId FROM bookings) THEN
		SET statusCode = 465; -- invalid booking id
	ELSEIF NOT EXISTS (
		SELECT *
		FROM bookings
		NATURAL JOIN players
		WHERE bookingID = inBookingId
			AND playerId = inPlayerId
	) THEN
		SET statusCode = 401; -- Unauthorized
	ELSEIF TIMEDIFF(
		(SELECT (CAST(CONCAT(bookingDate, ' ', bookingStartTime) AS DATETIME))
		FROM bookings
		WHERE bookingId = inBookingId), NOW()) < TIME('24:00:00') THEN
		SET statusCode = 411; -- The booking is cancelled in less than 24h before the start time
	ELSE
		SET statusCode = 200;
		DELETE
		FROM bookings
		WHERE bookingId = inBookingId;
	END IF;
END//

DELIMITER ;