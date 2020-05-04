/*
    Test if the booking is removed from the database when cancelBooking is called on a correct bookingId
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL cancelBooking('1', 'P', @statusCode);
-- expected: no error code


/*
    Test if the cancellation request is rejected when cancelBooking is called in less than 24 hours before the starting time
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(
	NOW(),
	(
		SELECT CASE
			WHEN TIMEDIFF(DATE_ADD(DATE_ADD(CURDATE(), INTERVAL 1 DAY), INTERVAL 9 HOUR), NOW()) < TIME('24:00:00')
				THEN DATE_ADD(CURDATE(), INTERVAL 1 DAY)
			ELSE CURDATE()
		END
    ),
	'09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL cancelBooking('1', 'P', @statusCode);
-- expected: error code 411


/*
    Test if the cancellation request is rejected when the given booking id does not exist
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL cancelBooking('2', 'P', @statusCode);
-- expected: error code 465


/*
    Test if the cancellation request is rejected when the given player id does not exist
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('X', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'X', @statusCode);
CALL cancelBooking('2', 'Y', @statusCode);
-- expected: error code 464


/*
    Test if the cancellation request is rejected when the given player id is not the owner of the booking
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('X', @statusCode);
CALL createPlayer('Y', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'X', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'Y', @statusCode);
CALL cancelBooking('2', 'X', @statusCode);
-- expected: error code 401