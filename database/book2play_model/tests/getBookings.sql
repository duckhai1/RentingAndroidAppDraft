/*
    Test if the list of all bookings for the given date and city
    is returned, when all the paramters are valid

    (1st case)
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createPlayer('P');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL getBookings('A', DATE_ADD(CURDATE(), INTERVAL 10 DAY));
-- expected: no error code, list with 1 booking is returned


/*
    Test if the list of all bookings for the given date and city
    is returned, when all the paramters are valid

    (2nd case)
*/
CALL createCity('A');
CALL createCityCenter('B',  'A');
CALL createCityCenterCourt('C',  'A', 'B');
CALL createPlayer('P');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL createBooking('2', NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '10:30:00', '12:00:00', 'A', 'B', 'C', 'P');
CALL getBookings('A', DATE_ADD(CURDATE(), INTERVAL 11 DAY));
-- expected: no error code, list with 1 booking is returned


/*
    Test if the list of all bookings for the given date and city
    is returned, when all the paramters are valid

    (3 case)
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('B', 'A');
CALL createCityCenter('C', 'B');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createCityCenterCourt('D', 'B', 'C');
CALL createPlayer('P');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL createBooking('2', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '10:30:00', '12:00:00', 'A', 'B', 'C', 'P');
CALL createBooking('3', NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'B', 'C', 'D', 'P');
CALL getBookings('B', DATE_ADD(CURDATE(), INTERVAL 11 DAY));
-- expected: no error code, list with 1 booking is returned


/*
    Test if the request is rejected if the city id is invalid
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createPlayer('P');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL getBookings('B', DATE_ADD(CURDATE(), INTERVAL 11 DAY));
-- expected: error code 460