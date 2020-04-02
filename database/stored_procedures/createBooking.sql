-- MakeBooking
DELIMITER //

DROP PROCEDURE IF EXISTS createBooking//

CREATE PROCEDURE createBooking (
    IN inPlayerId INT,
    IN inCourtId INT,
    IN inDate DATE,
    IN inStartTime TIME,
    IN inEndTime TIME
)
BEGIN
    DECLARE openTime TIME;
    DECLARE closeTime TIME;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

    SET openTime = '07:00:00';
    SET closeTime = '21:00:00';

	START TRANSACTION;

    IF inPlayerId NOT IN (SELECT playerId FROM players) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 478; -- player is does not exist
	END IF;

    IF inCourtId NOT IN (SELECT courtId FROM courts) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 469; -- court id does not exist
	END IF;

    IF inStartTime < openTime
		OR inStartTime > closeTime
		OR MOD(UNIX_TIMESTAMP(inStartTime), 15 * 60) <> 0 THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 470; -- start time is not in working hour or is not in interval of 15 minutes with 00h00m is the start
	END IF;

    IF inEndTime < openTime
		OR inEndTime > closeTime
		OR MOD(UNIX_TIMESTAMP(inEndTime),(15 * 60)) <> 0 THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 471; -- end time is not in working hour or is not in interval of 15 minutes with 00h00m is the start
	END IF;

    IF TIMEDIFF(inEndTime, inStartTime) <> TIME('00:45:00')
		AND TIMEDIFF(inEndTime, inStartTime) <> TIME('01:00:00')
		AND TIMEDIFF(inEndTime, inStartTime) <> TIME('01:15:00')
		AND TIMEDIFF(inEndTime, inStartTime) <> TIME('01:30:00') THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 465; -- invalid duration, interval between start time and end time must be 45m, 1h, 1h15, or 1h30
	END IF;

	IF (
        SELECT count(*)
        FROM bookings
        WHERE playerId = inPlayerId
            AND CAST(CONCAT(bookingDate, ' ', bookingStartTime) AS DATETIME) > NOW()
    ) >= 3 THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 411; -- the user already has 3 upcomming bookings
	END IF;

	IF (
		SELECT count(*)
        FROM bookings
        WHERE courtId = inCourtId
			AND bookingDate = inDate
			AND ((inStartTime < bookingStartTime AND inEndTime > bookingStartTime)
				OR (inStartTime > bookingStartTime AND inStartTime < bookingEndTime))
    ) > 0 THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 464; -- the booking's period overlaps with another existing booking
	END IF;

    IF (
		SELECT count(*)
        FROM bookings
		WHERE playerId = inPlayerId
			AND isPaid = FALSE
			AND cast(concat(bookingDate, ' ', bookingStartTime) as datetime) < NOW()
    ) > 0 THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 413;
	END IF;

	INSERT INTO bookings (bookingDate, bookingStartTime, bookingEndTime, createdAt, playerId, courtId)
	VALUES (inDate, inStartTime, inEndTime, NOW(), inPlayerId, inCourtId);
END//

DELIMITER ;
