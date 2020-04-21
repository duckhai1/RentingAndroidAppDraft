/*
	Get all bookings of a player with the given player Id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getPlayerBookings //
CREATE PROCEDURE getPlayerBookings (
	IN inPlayerId VARCHAR(100),
    OUT statusCode INT
)
BEGIN

	IF inPlayerId NOT IN (SELECT playerId FROM players) THEN
		SET statusCode = 464; invalid player id
    ELSE
		SET statusCode = 200;
		SELECT bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, playerId
		FROM bookings
			NATURAL JOIN players
			NATURAL JOIN courts
			NATURAL JOIN sportCenters
			NATURAL JOIN cities
		WHERE playerId = inPlayerId;
	END IF;
END//

DELIMITER ;