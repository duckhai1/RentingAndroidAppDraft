/*
The staff member identified by staffPk updates the status of the booking identified by bookingPk.
*/
USE book2play_test;
DELIMITER //

DROP PROCEDURE IF EXISTS updateBookingStatus //
CREATE PROCEDURE updateBookingStatus (
	IN inStatus BOOLEAN,
    IN inBookingId VARCHAR(100),
    IN inCityId VARCHAR(100),
    IN inSportcenterId VARCHAR(100),
    IN inStaffId VARCHAR(100)
)
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;
    
    START TRANSACTION;

    IF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 460; -- invalid city id
	END IF;

    IF inBookingId NOT IN (SELECT bookingId FROM bookings) THEN 
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 465; -- invalid booking id
	END IF;

    IF inSportcenterId NOT IN (
		SELECT sportcenterId
		FROM sportcenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 461; -- sport center does not exists
	END IF;

	IF NOT EXISTS (
		SELECT *
		FROM staffs
		NATURAL JOIN sportcenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportcenterId = inSportcenterId
			AND staffId = inStaffId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 401; -- unauthorized
	END IF;
    
	IF (
		SELECT sportcenterPk
		FROM sportcenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportcenterId = inSportcenterId
	) <> (
		SELECT sportcenterPk
		FROM bookings
		NATURAL JOIN courts
		WHERE bookingId = inBookingId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 401; -- unauthorized
	END IF;

   	UPDATE bookings
	SET isPaid = inStatus
	WHERE bookingId = inBookingId;
END//

DELIMITER ;
