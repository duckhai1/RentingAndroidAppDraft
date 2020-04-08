/*
	Get all information about a booking given the booking id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getBookingInfo//
CREATE PROCEDURE getBookingInfo (
    IN inBookingId VARCHAR(100),
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

    IF inBookingId NOT IN (SELECT bookingId FROM bookings ) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 465; -- invalid booking id
	END IF;

	SELECT *
	FROM bookings
	WHERE bookingId = inBookingId;
    SET statusCode = 200;
END//

DELIMITER ;