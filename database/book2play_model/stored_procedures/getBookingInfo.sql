/*
	Get all information about a booking given the booking id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getBookingInfo//
CREATE PROCEDURE getBookingInfo (
    IN inBookingId INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

	START TRANSACTION;

    IF inBookingId NOT IN (SELECT bookingId FROM bookings ) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 410; -- booking not found
	END IF;

	SELECT *
	FROM bookings
	WHERE bookingId = inBookingId;
END//

DELIMITER ;