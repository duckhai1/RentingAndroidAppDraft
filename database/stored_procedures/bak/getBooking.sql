-- Get all bookings for a given city and date
DELIMITER //

DROP PROCEDURE IF EXISTS getBookings//

CREATE PROCEDURE getBookings (
    in inCityId INT,
    in inBookingDate DATE,
    out resultCode INT
)
BEGIN
    IF inCityId NOT in (SELECT cityId FROM cities ) THEN
        SET result_code = 461; -- city id does not exist
    ELSE
        SET result_code = 200;
        SELECT *
        FROM bookings
        WHERE cityId = inCityId AND bookingDate = inBookingDate ;
    END IF;
END//

DELIMITER ;
