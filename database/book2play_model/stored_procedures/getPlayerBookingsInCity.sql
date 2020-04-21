/*
	Get all bookings of a player in the given city and date
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getPlayerBookingsInCity //
CREATE PROCEDURE getPlayerBookingsInCity (
	IN inPlayerId VARCHAR(100),
	IN inCityId VARCHAR(100),
	IN inBookingDate DATE,
    OUT statusCode INT
)
BEGIN
	IF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SET statusCode = 460; -- city id does not exist
	ELSEIF inPlayerId NOT IN (SELECT playerId FROM players) THEN
		SET statusCode = 464;
	ELSEIF NOT EXISTS (
		SELECT *
		FROM bookings
		NATURAL JOIN courts
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		NATURAL JOIN players
		WHERE cityId = inCityID
			AND playerId = inPlayerId
			AND bookingDate = inBookingDate
	) THEN
		SET statusCode = 466; -- no bookings in given date 
    ELSE
		SET statusCode = 200;
		SELECT bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, player
		FROM bookings
			NATURAL JOIN players
			NATURAL JOIN courts
			NATURAL JOIN sportCenters
			NATURAL JOIN cities
		WHERE cityId = inCityId
			AND bookingDate = inBookingDate
			AND playerId = inPlayerId;
	END IF;
END//

DELIMITER ;