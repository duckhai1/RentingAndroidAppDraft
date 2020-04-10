/*
    Test if the booking is removed from the database when cancelBooking is called on a correct bookingId
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createPlayer('P');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL cancelBooking('1', 'P');
-- expected: no error code


/*
    Test if the cancellation request is rejected when cancelBooking is called in less than 24 hours before the starting time
*/
CALL createCity('A');
CALL createCityCenter('B',  'A');
CALL createCityCenterCourt('C',  'A', 'B');
CALL createPlayer('P');
CALL createBooking(
	'1', NOW(),
	(
		SELECT CASE
			WHEN TIMEDIFF(DATE_ADD(DATE_ADD(CURDATE(), INTERVAL 1 DAY), INTERVAL 9 HOUR), NOW()) < TIME('24:00:00')
				THEN DATE_ADD(CURDATE(), INTERVAL 1 DAY)
			ELSE CURDATE()
		END
    ),
	'09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL cancelBooking('1', 'P');
-- expected: error code 411


/*
    Test if the cancellation request is rejected when the given booking id does not exist
*/
CALL createCity('A');
CALL createCityCenter('B',  'A');
CALL createCityCenterCourt('C',  'A', 'B');
CALL createPlayer('P');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL cancelBooking('2', 'P');
-- expected: error code 465


/*
    Test if the cancellation request is rejected when the given player id does not exist
*/
CALL createCity('A');
CALL createCityCenter('B',  'A');
CALL createCityCenterCourt('C',  'A', 'B');
CALL createPlayer('X');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'X');
CALL cancelBooking('2', 'Y');
-- expected: error code 464


/*
    Test if the cancellation request is rejected when the given player id is not the owner of the booking
*/
CALL createCity('A');
CALL createCityCenter('B',  'A');
CALL createCityCenterCourt('C',  'A', 'B');
CALL createPlayer('X');
CALL createPlayer('Y');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'X');
CALL createBooking('2', NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'Y');
CALL cancelBooking('2', 'X');
-- expected: error code 401