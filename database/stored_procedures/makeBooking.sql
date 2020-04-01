-- MakeBooking
DELIMITER //

DROP PROCEDURE IF EXISTS makeBooking//

CREATE PROCEDURE makeBooking(
    IN inPlayerId INT,
    IN inCourtId INT,
    IN inDate DATE,
    IN inStartTime TIME,
    IN inEndTime TIME,
    OUT resultCode INT
)
BEGIN
    DECLARE openTime TIME;
    DECLARE closeTime TIME;
    SET openTime = '07:00:00';
    SET closeTime = '21:00:00';

    IF inPlayerId NOT IN (SELECT playerId FROM players) THEN
        SET resultCode = 478; -- player is does not exist

    ELSEIF inCourtId NOT IN (SELECT courtId FROM courts) THEN
        SET resultCode = 469; -- court id does not exist

    ELSEIF inStartTime < openTime
		OR inStartTime > closeTime
		OR MOD(UNIX_TIMESTAMP(inStartTime), 15 * 60) <> 0 THEN
        SET resultCode = 470; -- start time is not in working hour or is not in interval of 15 minutes with 00h00m is the start

    ELSEIF inEndTime < openTime
		OR inEndTime > closeTime
		OR MOD(UNIX_TIMESTAMP(inEndTime),(15 * 60)) <> 0 THEN
        SET resultCode = 471; -- end time is not in working hour or is not in interval of 15 minutes with 00h00m is the start

    ELSEIF TIMEDIFF(inEndTime, inStartTime) <> TIME('00:45:00')
		AND TIMEDIFF(inEndTime, inStartTime) <> TIME('01:00:00')
		AND TIMEDIFF(inEndTime, inStartTime) <> TIME('01:15:00')
		AND TIMEDIFF(inEndTime, inStartTime) <> TIME('01:30:00') THEN
        SET resultCode = 465; -- invalid duration, interval between start time and end time must be 45m, 1h, 1h15, or 1h30

	ELSEIF (
        SELECT count(*)
        FROM bookings
        WHERE playerId = inPlayerId
            AND CAST(CONCAT(bookingDate, ' ', bookingStartTime) AS DATETIME) > NOW()
    ) >= 3 THEN
		SET resultCode = 411; -- the user already has 3 upcomming bookings

	ELSEIF (
		SELECT count(*)
        FROM bookings
        WHERE courtId = inCourtId
			AND bookingDate = inDate
			AND ((inStartTime <= bookingStartTime AND inEndTime > bookingStartTime)
				OR (inStartTime >= bookingStartTime AND inStartTime < bookingEndTime))
    ) > 0 THEN
		SET resultCode = 464; -- the booking's period overlaps with another existing booking

    ELSEIF (
		SELECT count(*)
        FROM bookings
		WHERE player_id = inPlayerId
			AND isPaid = FALSE
			AND isCancelled = FALSE
			AND cast(concat(bookingDate, ' ', bookingStartTime) as datetime) < NOW()
    ) > 0 THEN
		SET resultCode = 413;

    ELSE
		SET resultCode = 202;
		INSERT INTO bookings (bookingDate, bookingStartTime, bookingEndTime, createdAt, playerId, courtId)
		VALUES (inDate, inStartTime, inEndTime, NOW(), inPlayerId, inCourtId);
    END IF;
END//

DELIMITER ;
