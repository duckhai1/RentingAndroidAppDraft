/*
    Test if the correct data from the database is returned,
    when all the provided parameters are valid

    (1st case)
*/


CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('B', 'A');
CALL createCityCenter('C', 'B');
CALL createCityCenter('D', 'A');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createCityCenterCourt('C', 'B', 'C');
CALL createCityCenterCourt('C', 'A', 'D');
CALL createPlayer('P');
CALL createPlayer('Q');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL createBooking('2', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'B', 'C', 'C', 'P');
CALL createBooking('3', NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'Q');
CALL createBooking('4', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'D', 'C', 'P');
CALL getSportcenterBookings('B', 'A', DATE_ADD(CURDATE(), INTERVAL 10 DAY));
-- expected: no error code, a relation with 1 element is returned


/*
    Test if the correct data from the database is returned,
    when all the provided parameters are valid

    (2nd case)
*/


CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('B', 'A');
CALL createCityCenter('C', 'B');
CALL createCityCenter('D', 'A');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createCityCenterCourt('C', 'B', 'C');
CALL createCityCenterCourt('C', 'A', 'D');
CALL createPlayer('P');
CALL createPlayer('Q');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL createBooking('2', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'B', 'C', 'C', 'P');
CALL createBooking('3', NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'Q');
CALL createBooking('4', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'D', 'C', 'P');
CALL getSportcenterBookings('B', 'A', DATE_ADD(CURDATE(), INTERVAL 11 DAY));
-- expected: no error code, a relation with 1 element is returned


/*
    Test if the correct data from the database is returned,
    when all the provided parameters are valid

    (3rd case)
*/


CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('B', 'A');
CALL createCityCenter('C', 'B');
CALL createCityCenter('D', 'A');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createCityCenterCourt('C', 'B', 'C');
CALL createCityCenterCourt('C', 'A', 'D');
CALL createPlayer('P');
CALL createPlayer('Q');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL createBooking('2', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'B', 'C', 'C', 'P');
CALL createBooking('3', NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'Q');
CALL createBooking('4', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'D', 'C', 'P');
CALL getSportcenterBookings('D', 'A', DATE_ADD(CURDATE(), INTERVAL 10 DAY));
-- expected: no error code, a relation with 1 element is returned


/*
    Test if the correct data from the database is returned,
    when all the provided parameters are valid

    (4th case)
*/


CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('B', 'A');
CALL createCityCenter('C', 'B');
CALL createCityCenter('D', 'A');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createCityCenterCourt('C', 'B', 'C');
CALL createCityCenterCourt('C', 'A', 'D');
CALL createPlayer('P');
CALL createPlayer('Q');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL createBooking('2', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'B', 'C', 'C', 'P');
CALL createBooking('3', NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'Q');
CALL createBooking('4', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'D', 'C', 'P');
CALL getSportcenterBookings('C', 'B', DATE_ADD(CURDATE(), INTERVAL 10 DAY));
-- expected: no error code, a relation with 1 element is returned



/*
    Test if an error code is returned, when the provided sportcenter id is invalid
*/


CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('B', 'A');
CALL createCityCenter('C', 'B');
CALL createCityCenter('D', 'A');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createCityCenterCourt('C', 'B', 'C');
CALL createCityCenterCourt('C', 'A', 'D');
CALL createPlayer('P');
CALL createPlayer('Q');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL createBooking('2', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'B', 'C', 'C', 'P');
CALL createBooking('3', NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'Q');
CALL createBooking('4', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'D', 'C', 'P');
CALL getSportcenterBookings('@', 'A', DATE_ADD(CURDATE(), INTERVAL 10 DAY));
-- expected: error code 461


/*
    Test if an error code is returned, when the provided city id is invalid
*/


CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('B', 'A');
CALL createCityCenter('C', 'B');
CALL createCityCenter('D', 'A');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createCityCenterCourt('C', 'B', 'C');
CALL createCityCenterCourt('C', 'A', 'D');
CALL createPlayer('P');
CALL createPlayer('Q');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL createBooking('2', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'B', 'C', 'C', 'P');
CALL createBooking('3', NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'Q');
CALL createBooking('4', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'D', 'C', 'P');
CALL getSportcenterBookings('B', '@', DATE_ADD(CURDATE(), INTERVAL 10 DAY));
-- expected: error code 460


/*
    Test if an error code is returned, when there is no booking in the provided date
*/


CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('B', 'A');
CALL createCityCenter('C', 'B');
CALL createCityCenter('D', 'A');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createCityCenterCourt('C', 'B', 'C');
CALL createCityCenterCourt('C', 'A', 'D');
CALL createPlayer('P');
CALL createPlayer('Q');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL createBooking('2', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'B', 'C', 'C', 'P');
CALL createBooking('3', NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'Q');
CALL createBooking('4', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'D', 'C', 'P');
CALL getSportcenterBookings('B', 'A', DATE_ADD(CURDATE(), INTERVAL 12 DAY));
-- expected: error code 466