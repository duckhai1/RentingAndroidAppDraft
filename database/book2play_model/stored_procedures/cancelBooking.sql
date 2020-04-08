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
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO;
		SET statusCode = @p1;
		ROLLBACK;
	END;

	START TRANSACTION;

	IF inPlayerId NOT IN (SELECT playerId FROM players) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO =464; -- Player does not exist
	END IF;

    IF inBookingId NOT IN (SELECT bookingId FROM bookings) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 465; -- invalid booking id
	END IF;

	IF NOT EXISTS (
		SELECT *
		FROM bookings
		NATURAL JOIN players
		WHERE bookingID = inBookingId
			AND playerId = inPlayerId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 401; -- Unauthorized
	END IF;

    IF TIMEDIFF(
		(SELECT (CAST(CONCAT(bookingDate, ' ', bookingStartTime) AS DATETIME))
		FROM bookings
		WHERE bookingId = inBookingId), NOW()) < TIME('24:00:00') THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 411; -- The booking is cancelled in less than 24h before the start time
	END IF;

	DELETE
	FROM bookings
	WHERE bookingId = inBookingId;
    SET statusCode = 200;
END//

DELIMITER ;