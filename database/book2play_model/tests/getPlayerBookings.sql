/*
    Test if a relation containing the expected data is returned,
    when all the parameters are correct

    (1st cast)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('B', 'B', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('C', 'B', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createPlayer('Q', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '14:00:00', '15:30:00', 'A', 'B', 'C', 'Q', @statusCode);
CALL getPlayerBookings('P', @statusCode);
-- expected: no error code, a relation with 1 elements is returned

/*
    Test if a relation containing the expected data is returned,
    when all the parameters are correct

    (2nd cast)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('B', 'B', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('C', 'B', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createPlayer('Q', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '14:00:00', '15:30:00', 'A', 'B', 'C', 'Q', @statusCode);
CALL getPlayerBookings('Q', @statusCode);
-- expected: no error code, a relation with 1 elements is returned



/*
    Test if a relation containing the expected data is not returned,
    when the player id is invalid
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('B', 'B', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('C', 'B', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL getPlayerBookings('@', @statusCode);
-- expected: error code 464

/*
    Test if a relation containing the expected data is not returned,
    when the player id is not found
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('B', 'B', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('C', 'B', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL getPlayerBookings('T', @statusCode);
-- expected: error code 464

