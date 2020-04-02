DELIMITER //

DROP PROCEDURE IF EXISTS getPlayerBookings //
	CREATE PROCEDURE getPlayerBookings (
	IN inPlayerId INT,
    IN inCityName INT,
    IN inBookingDate DATE,
    OUT resultCode INT
)
BEGIN
	IF inPlayerId NOT in(SELECT playerId FROM bookings) THEN
		SET resultCode = 478;
	ELSEIF inCityName NOT in(SELECT cityName
					            FROM cities
                                NATURAL JOIN sportcenters 
                                NATURAL JOIN courts
                                NATURAL JOIN bookings
	) THEN
		SET resultCode = 461;
	ELSEIF inBookingDate NOT in (SELECT bookingDate FROM bookings) THEN 
        SET resultCode = 463;
    ELSE
        SET resultCode = 200;
        SELECT * 
        FROM bookings
        WHERE playerId = inPlayerId 
            AND cityName = inCityName 
            AND bookingDate = inBookingDate;
    END IF;
END//

DELIMITER ;
