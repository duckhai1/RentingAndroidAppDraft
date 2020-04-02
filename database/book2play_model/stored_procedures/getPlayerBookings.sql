/*
	Get all bookings of a player in the given city and date
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getPlayerBookings //
CREATE PROCEDURE getPlayerBookings (
	IN inPlayerId INT,
	IN inCityId INT,
	IN inBookingDate DATE
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

	START TRANSITION;

	IF inPlayerId NOT IN (SELECT playerId FROM bookings) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 478;
	END IF;

	IF inCityId NOT IN (
		SELECT cityId
		FROM bookings
		NATURAL JOIN courts
		NATURAL JOIN sportcenters
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 461; -- city id does not exist
	END IF;

	IF inBookingDate NOT in (SELECT bookingDate FROM bookings) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 463; -- no bookings in given date
	END IF;


	SELECT *
	FROM bookings
	NATURAL JOIN courts
	NATURAL JOIN sportcenters
	WHERE playerId = inPlayerId
		AND cityId = inCityId
		AND bookingDate = inBookingDate;
END//

DELIMITER ;