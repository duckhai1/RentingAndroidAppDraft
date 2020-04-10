/*
	Get all bookings of a player in the given city and date
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getPlayerBookings //
CREATE PROCEDURE getPlayerBookings (
	IN inPlayerId VARCHAR(100),
	IN inCityId VARCHAR(100),
	IN inBookingDate DATE,
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
			SET MYSQL_ERRNO = 460; -- city id does not exist
	END IF;

	IF inPlayerId NOT IN (SELECT playerId FROM players) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 464;
	END IF;

	IF NOT EXISTS (
		SELECT *
		FROM bookings
		NATURAL JOIN courts
		NATURAL JOIN sportcenters
		NATURAL JOIN cities
		NATURAL JOIN players
		WHERE cityId = inCityID
			AND playerId = inPlayerId
			AND bookingDate = inBookingDate
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 466; -- no bookings in given date
	END IF;

    SET statusCode = 200;

	SELECT *
	FROM bookings
	NATURAL JOIN courts
	NATURAL JOIN sportcenters
	NATURAL JOIN cities
	NATURAL JOIN players
	WHERE cityId = inCityId
		AND bookingDate = inBookingDate
		AND playerId = inPLayerId;
END//

DELIMITER ;