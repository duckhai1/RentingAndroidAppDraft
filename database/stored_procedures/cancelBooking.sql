-- cancel booking
DELIMITER //

DROP PROCEDURE IF EXISTS cancelBooking//

CREATE PROCEDURE cancelBooking(
    IN inBookingId INT,
    OUT resultCode INT
)
BEGIN
	DECLARE bookingDatetime DATETIME;
	SELECT (CAST(CONCAT(bookingDate, ' ', bookingStartTime) AS DATETIME))
	INTO bookingDatetime
	FROM booking
	WHERE bookingId = inBookingId;

    IF inBookingId NOT IN (SELECT bookingId FROM bookings) THEN
        SET resultCode = 410; -- booking id does not exists

    ELSEIF TIMEDIFF(bookingDatetime, NOW()) < TIME('24:00:00') THEN
        SET resultCode = 412; -- the booking is cancelled in less then 24h before the start time

    ELSE
        SET resultCode = 200;
        DELETE FROM booking WHERE bookingId = inBookingId; -- cancel the booking
    END IF;
END//

DELIMITER ;
