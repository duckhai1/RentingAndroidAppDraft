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
	IN inSportcenterId VARCHAR(100),
	IN inCourtId VARCHAR(100),
	IN inPlayerId VARCHAR(100)
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

	IF inBookingId REGEXP '[^a-zA-Z0-9]+$' THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 465 ; -- invalid booking id 
	END IF;

	IF inDate < CURDATE() THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 466; -- booking date is invalid (past date)
	END IF;
	
    IF inStartTime < openTime
		OR inStartTime > closeTime
		OR MOD(UNIX_TIMESTAMP(inStartTime), 15 * 60) <> 0 THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 468; -- start time is not in working hour or is not in interval of 15 minutes with 00h00m is the start
	END IF;

    IF inEndTime < openTime
		OR inEndTime > closeTime
		OR MOD(UNIX_TIMESTAMP(inEndTime),(15 * 60)) <> 0 THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 469; -- end time is not in working hour or is not in interval of 15 minutes with 00h00m is the start
	END IF;

    IF TIMEDIFF(inEndTime, inStartTime) <> TIME('00:45:00')
		AND TIMEDIFF(inEndTime, inStartTime) <> TIME('01:00:00')
		AND TIMEDIFF(inEndTime, inStartTime) <> TIME('01:15:00')
		AND TIMEDIFF(inEndTime, inStartTime) <> TIME('01:30:00') THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 467; -- invalid duration, interval between start time and end time must be 45m, 1h, 1h15, or 1h30
	END IF;

    IF inBookingId IN (SELECT bookingId FROM bookings) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 407 ; -- booking already exists 
	END IF;

    IF inPlayerId NOT IN (SELECT playerId FROM players) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO =464 ; -- Player does not exist
	END IF;

	IF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 460; -- City does not exist
	END IF;

	IF inSportcenterId NOT IN (
		SELECT sportcenterId
		FROM sportcenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 461; -- Center does not exist
	END IF;

    IF inCourtId NOT IN (
		SELECT courtId
		FROM courts
		NATURAL JOIN sportcenters
		NATURAL JOIN cities
		WHERE sportcenterId = inSportcenterId
			AND cityId = inCityId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 462; -- Court does not exist
	END IF;

	IF (
        SELECT count(*)
        FROM bookings
		NATURAL JOIN players
        WHERE playerId = inPlayerId
            AND CAST(CONCAT(bookingDate, ' ', bookingStartTime) AS DATETIME) > NOW()
    ) >= 3 THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 410; -- the user already has 3 upcomming bookings
	END IF;

    IF (
		SELECT count(*)
        FROM bookings
		NATURAL JOIN players
		WHERE playerId = inPlayerId
			AND isPaid = FALSE
			AND cast(concat(bookingDate, ' ', bookingStartTime) as datetime) < NOW()
    ) > 0 THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 412;
	END IF;

	IF (
		SELECT count(*)
        FROM bookings
		NATURAL JOIN courts
		NATURAL JOIN sportcenters
		NATURAL JOIN cities
        WHERE cityId = inCityId
			AND sportcenterId = inSportcenterId
			AND courtId = inCourtId
			AND bookingDate = inDate
			AND ((inStartTime <= bookingStartTime AND inEndTime > bookingStartTime)
				OR (inStartTime > bookingStartTime AND inStartTime < bookingEndTime))
    ) > 0 THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 413; -- the booking's period overlaps with another existing booking
	END IF;

	INSERT INTO bookings (bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, courtPk, playerPk)
	VALUES (
		inBookingId, inTimestamp, inDate, inStartTime, inEndTime,
		(
			SELECt courtPk
			FROM courts
			NATURAL JOIN sportcenters
			NATURAL JOIN cities
			WHERE cityId = inCityId
				AND sportcenterId = inSportcenterId
				AND courtId = inCourtId
		),
		(
			SELECT playerPk
			FROM players
			WHERE playerId = inPlayerId
		)
	);
END//

DELIMITER ;