/*
    Test if a relation containing the expected data is returned,
    when all the parameters are correct

    (1st cast)
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('B', 'A');
CALL createCityCenter('B', 'B');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createCityCenterCourt('C', 'B', 'B');
CALL createPlayer('P');
CALL createPlayer('Q');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL createBooking('2', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'A', 'B', 'C', 'Q');
CALL createBooking('3', NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL createBooking('4', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'B', 'B', 'C', 'P');
CALL getPlayerBookings('P', 'A', DATE_ADD(CURDATE(), INTERVAL 10 DAY));
-- expected: no error code, a relation with 1 elements is returned


/*
    Test if a relation containing the expected data is returned,
    when all the parameters are correct

    (2nd case)
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('B', 'A');
CALL createCityCenter('B', 'B');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createCityCenterCourt('C', 'B', 'B');
CALL createPlayer('P');
CALL createPlayer('Q');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL createBooking('2', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'A', 'B', 'C', 'Q');
CALL createBooking('3', NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL createBooking('4', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'B', 'B', 'C', 'P');
CALL getPlayerBookings('P', 'B', DATE_ADD(CURDATE(), INTERVAL 10 DAY));
-- expected: no error code, a relation with 1 elements is returned


/*
    Test if a relation containing the expected data is returned,
    when all the parameters are correct

    (3rd case)
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('B', 'A');
CALL createCityCenter('B', 'B');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createCityCenterCourt('C', 'B', 'B');
CALL createPlayer('P');
CALL createPlayer('Q');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL createBooking('2', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'A', 'B', 'C', 'Q');
CALL createBooking('3', NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL createBooking('4', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'B', 'B', 'C', 'P');
CALL getPlayerBookings('P', 'A', DATE_ADD(CURDATE(), INTERVAL 11 DAY));
-- expected: no error code, a relation with 1 elements is returned


/*
    Test if a relation containing the expected data is returned,
    when all the parameters are correct

    (4th case)
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('B', 'A');
CALL createCityCenter('B', 'B');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createCityCenterCourt('C', 'B', 'B');
CALL createPlayer('P');
CALL createPlayer('Q');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL createBooking('2', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'A', 'B', 'C', 'Q');
CALL createBooking('3', NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL createBooking('4', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'B', 'B', 'C', 'P');
CALL getPlayerBookings('Q', 'A', DATE_ADD(CURDATE(), INTERVAL 10 DAY));
-- expected: no error code, a relation with 1 elements is returned



/*
    Test if a relation containing the expected data is not returned,
    when the player id is invalid
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('B', 'A');
CALL createCityCenter('B', 'B');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createCityCenterCourt('C', 'B', 'B');
CALL createPlayer('P');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL getPlayerBookings('@', 'A', DATE_ADD(CURDATE(), INTERVAL 11 DAY));
-- expected: error code 464


/*
    Test if a relation containing the expected data is not returned,
    when the city id is invalid
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('B', 'A');
CALL createCityCenter('B', 'B');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createCityCenterCourt('C', 'B', 'B');
CALL createPlayer('P');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL getPlayerBookings('P', '@', DATE_ADD(CURDATE(), INTERVAL 11 DAY));
-- expected: error code 460


/*
    Test if a relation containing the expected data is not returned,
    when there is no booking in the provided date
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('B', 'A');
CALL createCityCenter('B', 'B');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createCityCenterCourt('C', 'B', 'B');
CALL createPlayer('P');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL getPlayerBookings('P', 'A', DATE_ADD(CURDATE(), INTERVAL 12 DAY));
-- expected: error code 466