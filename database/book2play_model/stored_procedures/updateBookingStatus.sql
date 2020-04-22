/*
The staff member identified by staffPk updates the status of the booking identified by bookingPk.
*/
DELIMITER //

DROP PROCEDURE IF EXISTS updateBookingStatus //
CREATE PROCEDURE updateBookingStatus (
	IN inStatus BOOLEAN,
    IN inBookingId VARCHAR(100),
    IN inCityId VARCHAR(100),
    IN inStaffId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
    IF inCityId NOT IN (SELECT cityId FROM cities) THEN
		SET statusCode = 460; -- invalid city id
	ELSEIF inBookingId NOT IN (SELECT bookingId FROM bookings) THEN 
		SET statusCode = 465; -- invalid booking id
	ELSEIF inSportCenterId NOT IN (
		SELECT sportCenterId
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
	) THEN
		SET statusCode = 461; -- sport center does not exists
	ELSEIF NOT EXISTS (
		SELECT *
		FROM staffs
		NATURAL JOIN sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportCenterId = inSportCenterId
			AND staffId = inStaffId
	) THEN
		SET statusCode = 401; -- unauthorized
	ELSEIF (
		SELECT sportCenterPk
		FROM sportCenters
		NATURAL JOIN cities
		WHERE cityId = inCityId
			AND sportCenterId = inSportCenterId
	) <> (
		SELECT sportCenterPk
		FROM bookings
		NATURAL JOIN courts
		WHERE bookingId = inBookingId
	) THEN
		SET statusCode = 401; -- unauthorized
    ELSE
		SET statusCode = 200;
		UPDATE bookings
		SET isPaid = inStatus
		WHERE bookingId = inBookingId;
	END IF;
END//

DELIMITER ;
