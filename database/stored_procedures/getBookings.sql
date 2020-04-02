-- Get all bookings for a given city and date
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
