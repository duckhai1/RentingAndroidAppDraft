-- cancel booking
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
