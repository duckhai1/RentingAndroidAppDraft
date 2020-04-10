/*
	Get all bookings of the sport center in the given date
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getSportcenterBookings//
CREATE PROCEDURE getSportcenterBookings(
	IN inSportcenterId VARCHAR(100),
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

	IF inSportcenterId NOT IN (
		SELECT sportcenterId
		FROM sportcenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 461; -- sportcenter id does not exist
	END IF;

	IF NOT EXISTS (
		SELECT *
		FROM bookings
		NATURAL JOIN courts
		NATURAL JOIN sportcenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportcenterId = inSportcenterId
			AND bookingDate = inBookingDate
	) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 466; -- no bookings in the given date
	END IF;

	SET statusCode = 200;

	SELECT *
	FROM bookings
	NATURAL JOIN courts
	NATURAL JOIN sportcenters
	NATURAL JOIN cities
	WHERE cityId = inCityId
		AND sportcenterId = inSportcenterId
		AND bookingDate = inBookingDate;
END//

DELIMITER ;