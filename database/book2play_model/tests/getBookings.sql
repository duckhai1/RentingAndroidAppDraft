/*
    Test if the list of all bookings for the given date and city
    is returned, when all the paramters are valid

    (1st case)
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL getBookings('A', DATE_ADD(CURDATE(), INTERVAL 10 DAY), @statusCode);
-- expected: no error code, list with 1 booking is returned


/*
    Test if the list of all bookings for the given date and city
    is returned, when all the paramters are valid

    (2nd case)
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking('2', NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '10:30:00', '12:00:00', 'A', 'B', 'C', 'P', @statusCode);
CALL getBookings('A', DATE_ADD(CURDATE(), INTERVAL 11 DAY), @statusCode);
-- expected: no error code, list with 1 booking is returned


/*
    Test if the list of all bookings for the given date and city
    is returned, when all the paramters are valid

    (3 case)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'B', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('D', 'B', 'C', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking('2', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '10:30:00', '12:00:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking('3', NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'B', 'C', 'D', 'P', @statusCode);
CALL getBookings('B', DATE_ADD(CURDATE(), INTERVAL 11 DAY), @statusCode);
-- expected: no error code, list with 1 booking is returned


/*
    Test if the request is rejected if the city id is invalid
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL getBookings('B', DATE_ADD(CURDATE(), INTERVAL 11 DAY), @statusCode);
-- expected: error code 460