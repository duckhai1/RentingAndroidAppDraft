/*
	 The player identified by playerId creates an unpaid booking, 
     on the date indicate by timestamp, for the date indicate by date , 
     starting at the time indicated by startTime and ending at the time 
     indicated by endTime, for the court identified by cityId, centerId, and courtId
*/
DELIMITER //

DROP PROCEDURE IF EXISTS createBooking//
CREATE PROCEDURE createBooking (
    IN inBookingId VARCHAR(100),
	IN inTimestamp TIMESTAMP,
    IN inDate DATE,
    IN inStartTime TIME,
    IN inEndTime TIME,
	IN inCityId VARCHAR(100),
	IN inSportCenterId VARCHAR(100),
	IN inCourtId VARCHAR(100),
	IN inPlayerId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
    DECLARE openTime TIME;
    DECLARE closeTime TIME;

    SET openTime = '07:00:00';
    SET closeTime = '21:00:00';

	IF inBookingId REGEXP '[^a-zA-Z0-9]+' THEN
		SET statusCode = 465 ; -- invalid booking id 
	ELSEIF inDate < CURDATE() THEN
		SET statusCode = 466; -- booking date is invalid (past date)
    ELSEIF inStartTime < openTime
		OR inStartTime > closeTime
		OR MOD(UNIX_TIMESTAMP(inStartTime), 15 * 60) <> 0 THEN
		SET statusCode = 468; -- start time is not in working hour or is not in interval of 15 minutes with 00h00m is the start
	ELSEIF inEndTime < openTime
		OR inEndTime > closeTime
		OR MOD(UNIX_TIMESTAMP(inEndTime),(15 * 60)) <> 0 THEN
		SET statusCode = 469; -- end time is not in working hour or is not in interval of 15 minutes with 00h00m is the start
	ELSEIF TIMEDIFF(inEndTime, inStartTime) <> TIME('00:45:00')
		AND TIMEDIFF(inEndTime, inStartTime) <> TIME('01:00:00')
		AND TIMEDIFF(inEndTime, inStartTime) <> TIME('01:15:00')
		AND TIMEDIFF(inEndTime, inStartTime) <> TIME('01:30:00') THEN
		SET statusCode = 467; -- invalid duration, interval between start time and end time must be 45m, 1h, 1h15, or 1h30
	ELSEIF inBookingId IN (SELECT bookingId FROM bookings) THEN
		SET statusCode = 407 ; -- booking already exists 
	ELSEIF inPlayerId NOT IN (SELECT playerId FROM players) THEN
		SET statusCode =464 ; -- Player does not exist
	ELSEIF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SET statusCode = 460; -- City does not exist
	ELSEIF inSportCenterId NOT IN (
		SELECT sportCenterId
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SET statusCode = 461; -- Center does not exist
	ELSEIF inCourtId NOT IN (
		SELECT courtId
		FROM courts
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE sportCenterId = inSportCenterId
			AND cityId = inCityId
	) THEN
		SET statusCode = 462; -- Court does not exist
	ELSEIF (
        SELECT count(*)
        FROM bookings
		NATURAL JOIN players
        WHERE playerId = inPlayerId
            AND CAST(CONCAT(bookingDate, ' ', bookingStartTime) AS DATETIME) > NOW()
    ) >= 3 THEN
		SET statusCode = 410; -- the user already has 3 upcomming bookings
	ELSEIF (
		SELECT count(*)
        FROM bookings
		NATURAL JOIN players
		WHERE playerId = inPlayerId
			AND isPaid = FALSE
			AND cast(concat(bookingDate, ' ', bookingStartTime) as datetime) < NOW()
    ) > 0 THEN
		SET statusCode = 412;
	ELSEIF (
		SELECT count(*)
        FROM bookings
		NATURAL JOIN courts
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
        WHERE cityId = inCityId
			AND sportCenterId = inSportCenterId
			AND courtId = inCourtId
			AND bookingDate = inDate
			AND ((inStartTime <= bookingStartTime AND inEndTime > bookingStartTime)
				OR (inStartTime > bookingStartTime AND inStartTime < bookingEndTime))
    ) > 0 THEN
		SET statusCode = 413; -- the booking's period overlaps with another existing booking
	ELSE
		SET statusCode = 200;
		INSERT INTO bookings (bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, courtPk, playerPk)
		VALUES (
			inBookingId, inTimestamp, inDate, inStartTime, inEndTime,
			(
				SELECt courtPk
				FROM courts
				NATURAL JOIN sportCenters
				NATURAL JOIN cities
				WHERE cityId = inCityId
					AND sportCenterId = inSportCenterId
					AND courtId = inCourtId
			),
			(
				SELECT playerPk
				FROM players
				WHERE playerId = inPlayerId
			)
		);
	END IF;
END//

DELIMITER ;