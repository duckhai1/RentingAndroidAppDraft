/*
	Get all bookings for a given date and city id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getBookings//
CREATE PROCEDURE getBookings (
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
			SET MYSQL_ERRNO = 460; -- invalid city id
	END IF;

    SET statusCode = 200;

	SELECT *
	FROM bookings
	NATURAL JOIN courts
	NATURAL JOIN sportcenters
	NATURAL JOIN cities
	WHERE cityId = inCityId
		AND bookingDate = inBookingDate ;
END//

DELIMITER ;
