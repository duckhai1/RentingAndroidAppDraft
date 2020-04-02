DROP DATABASE IF EXISTS book2play_test;
CREATE DATABASE book2play_test;
USE book2play_test;

CREATE TABLE IF NOT EXISTS cities (
  cityId INT NOT NULL AUTO_INCREMENT,
  cityName VARCHAR(64) NOT NULL,

  PRIMARY KEY (cityId)
);

CREATE TABLE IF NOT EXISTS sportcenters (
    sportcenterId INT NOT NULL AUTO_INCREMENT,
    sportcenterName VARCHAR(64) NOT NULL,
	cityId INT NOT NULL,

    PRIMARY KEY (sportcenterId),
	FOREIGN KEY (cityId)
		REFERENCES cities (cityId)
		ON DELETE CASCADE
); 

CREATE TABLE IF NOT EXISTS courts (
    courtId INT NOT NULL AUTO_INCREMENT,
    courtName VARCHAR(64) NOT NULL UNIQUE,
    sportcenterId INT NOT NULL,

    PRIMARY KEY (courtId),
	FOREIGN KEY (sportcenterId)
		REFERENCES sportcenters (sportcenterId)
		ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS staffs (
    staffId INT NOT NULL AUTO_INCREMENT,
    staffName VARCHAR(64) NOT NULL,
    sportcenterId INT NOT NULL,

    PRIMARY KEY (staffId),
	FOREIGN KEY (sportcenterId)
		REFERENCES sportcenters (sportcenterId)
		ON DELETE CASCADE
); 

CREATE TABLE IF NOT EXISTS players (
    playerId INT NOT NULL AUTO_INCREMENT,
    playerName VARCHAR(64) NOT NULL,

    PRIMARY KEY (playerId)
); 

CREATE TABLE IF NOT EXISTS bookings (
    bookingId INT NOT NULL AUTO_INCREMENT,
    bookingDate DATE NOT NULL,
    bookingStartTime TIME NOT NULL,
    bookingEndTime TIME NOT NULL,
    createdAt TIMESTAMP NOT NULL,
    isPaid BOOLEAN DEFAULT FALSE, -- FALSE-unpaid; TRUE-paid
    playerId INT NOT NULL,
    courtId INT NULL,

    PRIMARY KEY(bookingId),
	FOREIGN KEY (playerId)
		REFERENCES players(playerId)
		ON DELETE CASCADE,
	FOREIGN KEY(courtId)
		REFERENCES courts (courtId)
		ON DELETE SET NULL
); 

/*
	Get all information about a booking given the booking id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getBookingInfo//
CREATE PROCEDURE getBookingInfo (
    in inBookingId INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

	START TRANSITION;

    IF inBookingId NOT in (SELECT bookingId FROM bookings ) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 410; -- booking not found
	END IF;

	SELECT *
	FROM bookings
	WHERE bookingId = inBookingId;
END//

DELIMITER ;

/*
	Update whether a booking has been paid or not
*/
DELIMITER //

DROP PROCEDURE IF EXISTS updateBookingPaymentStatus//
CREATE PROCEDURE updateBookingPaymentStatus (
    IN inBookingId INT,
	IN inIsPaid BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

	START TRANSACTION;

    IF inBookingId NOT IN (SELECT bookingId FROM bookings) THEN 
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 410; -- booking id does not exists
	END IF;

	UPDATE bookings
	SET isPaid = inIsPaid
	WHERE bookingId = inBookingId;
END//

DELIMITER ;

/*
	Get all information about a staff given the staff id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getStaffInfo//
CREATE PROCEDURE getStaffInfo(
	in inStaffId INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

	START TRANSITION;

	IF inStaffId NOT IN (SELECT staffId FROM staffs) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 479;
	END IF;

	SELECT *
	FROM staffs
	WHERE staffId = inStaffId;
END//

DELIMITER ;

/*
	Change the name of a staff given the staff id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS updateStaffName //
CREATE PROCEDURE updateStaffName (
    in inStaffId INT,
    in inStaffName VARCHAR(50),
    out MYSQL_ERRNO INT 
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

	START TRANSITION;

    IF inStaffId NOT IN (SELECT staffId FROM staffs) THEN 
        SIGNAL SQLSTATE '45000'
            SET MYSQL_ERRNO = 479;
    END IF;

    UPDATE staffs
    SET staffName = inStaffName
    WHERE staffId = inStaffId;
END//

DELIMITER ;

/*
	Change the name of a player given the player id
*/
DELIMTER //

DROP PROCEDURE IF EXISTS updatePlayerName//
CREATE PROCEDURE updatePlayerName (
    IN inPlayerId INT,
    IN inPlayerName VARCHAR(64)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

    START TRANSACTION;

    IF inPlayerId NOT IN (SELECT playerId FROM players) THEN 
        SIGNAL SQLSTATE '45000'
            SET MYSQL_ERRNO = 478; -- player id does not exist
    END IF;

    UPDATE players
    SET playerName = inPlayerName
    WHERE playerId = inPlayerId;
END//
 
 DELIMITER ;

/*
	Cancel a booking given the booking id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS cancelBooking//
CREATE PROCEDURE cancelBooking (
    IN inBookingId INT,
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

	START TRANSITION;

    IF inBookingId NOT IN (SELECT bookingId FROM bookings) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 410; -- booking id does not exists
	END IF;

    IF TIMEDIFF(
		(SELECT (CAST(CONCAT(bookingDate, ' ', bookingStartTime) AS DATETIME))
		FROM bookings
		WHERE bookingId = inBookingId), NOW()) < TIME('24:00:00') THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 412; -- the booking is cancelled in less then 24h before the start time
	END IF;

	DELETE
	FROM bookings
	WHERE bookingId = inBookingId;
END//

DELIMITER ;

/*
	Get all information about a sport center given the sport center id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getSportcenterInfo//
CREATE PROCEDURE getSportcenterInfo (
	in inSportcenterId INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

	START TRANSITION;

	IF inSportcenterId NOT IN (SELECT sportcenterId FROM sportcenters) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 462;
	END IF;

	SELECT *
	FROM sportcenters
	WHERE sportcenterId = inSportcenterId;
END//

DELIMITER ;

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

/*
	Get all information about a player given the player id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getPlayerInfo//
CREATE PROCEDURE getPlayerInfo (
	IN inPlayerId INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

	START TRANSITION;

	IF inPlayerId NOT IN (SELECT playerId FROM players) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 478;
	END IF;

	SELECT *
	FROM players
	WHERE playerId = inPlayerId;
END//

DELIMITER ;

/*
	Get all bookings for a given date and city id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getBookings//
CREATE PROCEDURE getBookings (
    in inCityId INT,
    in inBookingDate DATE,
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

	START TRANSITION;

    IF inCityId NOT in (SELECT cityId FROM cities ) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 461; -- city id does not exist
	END IF;

	SELECT *
	FROM bookings
	NATURAL JOIN courts
	NATURAL JOIN sportcenters
	WHERE cityId = inCityId
		AND bookingDate = inBookingDate ;
END//

DELIMITER ;

/*
	Create a booking given the player id, the court id, 
	date and start/end time of the booking
*/
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

/*
	Get all bookings of the sport center in the given date
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getSportcenterBookings//
CREATE PROCEDURE getSportcenterBookings(
	IN 	inSportcenterId INT,
    IN  inBookingDate DATE
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

	START TRANSITION;

	IF inSportcenterId NOT IN (SELECT sportcenterId FROM sportcenters) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 462; -- sportcenter id does not exist
	END IF;

	IF inBookingDate NOT IN (SELECT bookingDate FROM bookings) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 463; -- no bookings in the given date
	END IF;

	SELECT * 
	FROM bookings
	NATURAL JOIN courts
	WHERE sportcenterId = inSportcenterId
		AND bookingDate = inBookingDate;
END//

DELIMITER ;

/*
	Change the name of a court given the court id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS updateCourtName//
CREATE PROCEDURE updateCourtName (
    IN inCourtId INT,
    IN inCourtName VARCHAR(64)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

	START TRANSITION;

    IF inCourtId NOT IN (SELECT courtId FROM courts) THEN 
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 469; -- test if parameter exist
    END IF;

	UPDATE courts 
	SET courtName = inCourtName
	WHERE courtId = inCourtId;
END//

DELIMITER ;

/*
	Get all information about a court given the court id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getCourtInfo//
CREATE PROCEDURE getCourtInfo(
	in inCourtId INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

	START TRANSITION;

	IF inCourtId NOT IN (SELECT courtId FROM courts) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 469;
	END IF;

	SELECT *
	FROM courts
	WHERE courtId = inCourtId;
END//

DELIMITER ;

/*
	Change the name of a sport center given the sport center id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS updateSportCenterName //
CREATE PROCEDURE updateSportCenterName (
    IN inSportcenterId INT,
    IN inSportcenterName VARCHAR(64),
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

    START TRANSACTION;

    -- test if parameter exist
    IF inSportcenterId NOT IN (SELECT sportcenterId FROM sportcenters) THEN 
        SIGNAL SQLSTATE '45000'
            SET MYSQL_ERRNO = 462;
    END IF;

    UPDATE sportcenters
    SET sportcenterName = inSportCenterName
    WHERE sportcenterId = inSportcenterId;
END//

DELIMITER ;

