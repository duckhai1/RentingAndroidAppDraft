-- Get sport center's bookings for a specific date
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
