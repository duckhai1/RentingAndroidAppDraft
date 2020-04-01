-- cancel booking
DELIMITER //

DROP PROCEDURE IF EXISTS cancelBooking//

CREATE PROCEDURE cancelBooking(
    IN inBookingId INT,
    OUT resultCode INT
)
BEGIN
    IF inBookingId NOT IN (SELECT bookingId FROM bookings) THEN
        SET resultCode = 410; -- booking id does not exists
    ELSEIF TIMEDIFF(
		(SELECT (CAST(CONCAT(bookingDate, ' ', bookingStartTime) AS DATETIME))
		FROM bookings
		WHERE bookingId = inBookingId), NOW()) < TIME('24:00:00') THEN
        SET resultCode = 412; -- the booking is cancelled in less then 24h before the start time
    ELSE
		DELETE FROM bookings 
		WHERE bookingId = inBookingId;
        SET resultCode = 200;
    END IF;
END//

DELIMITER ;
