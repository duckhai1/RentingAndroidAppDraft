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