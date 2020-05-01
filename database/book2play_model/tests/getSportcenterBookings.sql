/*
    Test if the correct data from the database is returned,
    when all the provided parameters are valid

    (1st case)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'B', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('C', 'B', 'C', @statusCode);
CALL createCityCenterCourt('C', 'A', 'D', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createPlayer('Q', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'B', 'C', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'Q', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'D', 'C', 'P', @statusCode);
CALL getSportCenterBookings('B', 'A', DATE_ADD(CURDATE(), INTERVAL 10 DAY), @statusCode);
-- expected: no error code, a relation with 1 element is returned


/*
    Test if the correct data from the database is returned,
    when all the provided parameters are valid

    (2nd case)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'B', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('C', 'B', 'C', @statusCode);
CALL createCityCenterCourt('C', 'A', 'D', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createPlayer('Q', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'B', 'C', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'Q', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'D', 'C', 'P', @statusCode);
CALL getSportCenterBookings('B', 'A', DATE_ADD(CURDATE(), INTERVAL 11 DAY), @statusCode);
-- expected: no error code, a relation with 1 element is returned


/*
    Test if the correct data from the database is returned,
    when all the provided parameters are valid

    (3rd case)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'B', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('C', 'B', 'C', @statusCode);
CALL createCityCenterCourt('C', 'A', 'D', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createPlayer('Q', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'B', 'C', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'Q', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'D', 'C', 'P', @statusCode);
CALL getSportCenterBookings('D', 'A', DATE_ADD(CURDATE(), INTERVAL 10 DAY), @statusCode);
-- expected: no error code, a relation with 1 element is returned


/*
    Test if the correct data from the database is returned,
    when all the provided parameters are valid

    (4th case)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'B', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('C', 'B', 'C', @statusCode);
CALL createCityCenterCourt('C', 'A', 'D', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createPlayer('Q', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'B', 'C', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'Q', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'D', 'C', 'P', @statusCode);
CALL getSportCenterBookings('C', 'B', DATE_ADD(CURDATE(), INTERVAL 10 DAY), @statusCode);
-- expected: no error code, a relation with 1 element is returned



/*
    Test if an error code is returned, when the provided sportCenter id is invalid
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'B', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('C', 'B', 'C', @statusCode);
CALL createCityCenterCourt('C', 'A', 'D', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createPlayer('Q', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'B', 'C', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'Q', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'D', 'C', 'P', @statusCode);
CALL getSportCenterBookings('@', 'A', DATE_ADD(CURDATE(), INTERVAL 10 DAY), @statusCode);
-- expected: error code 461


/*
    Test if an error code is returned, when the provided city id is invalid
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'B', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('C', 'B', 'C', @statusCode);
CALL createCityCenterCourt('C', 'A', 'D', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createPlayer('Q', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'B', 'C', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'Q', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'D', 'C', 'P', @statusCode);
CALL getSportCenterBookings('B', '@', DATE_ADD(CURDATE(), INTERVAL 10 DAY), @statusCode);
-- expected: error code 460


/*
    Test if an error code is returned, when there is no booking in the provided date
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'B', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('C', 'B', 'C', @statusCode);
CALL createCityCenterCourt('C', 'A', 'D', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createPlayer('Q', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'B', 'C', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'Q', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'D', 'C', 'P', @statusCode);
CALL getSportCenterBookings('B', 'A', DATE_ADD(CURDATE(), INTERVAL 12 DAY), @statusCode);
-- expected: error code 466