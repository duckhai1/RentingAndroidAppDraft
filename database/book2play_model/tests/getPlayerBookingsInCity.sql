/*
    Test if a relation containing the expected data is returned,
    when all the parameters are correct

*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL getPlayerBookingsInCity('P', 'A',  DATE_ADD(CURDATE(), INTERVAL 10 DAY),  @statusCode);
-- expected: no error code, a relation with 1 elements is returned

/*
    Test if a relation containing the expected data is not returned,
    when the city id is invalid
    1st case
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL getPlayerBookingsInCity('P', '@',  DATE_ADD(CURDATE(), INTERVAL 10 DAY),  @statusCode);
-- expected error code: 460

/*
    Test if a relation containing the expected data is not returned,
    when the city id is not found
    2nd case
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL getPlayerBookingsInCity('P', 'F',  DATE_ADD(CURDATE(), INTERVAL 10 DAY),  @statusCode);
-- expected error code: 460


/*
    Test if a relation containing the expected data is not returned,
    when the player id is invalid
    1st case
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL getPlayerBookingsInCity('@', 'A',  DATE_ADD(CURDATE(), INTERVAL 10 DAY),  @statusCode);
-- expected error code: 460

/*
    Test if a relation containing the expected data is not returned,
    when the player id is not found
    2nd case
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL getPlayerBookingsInCity('T', 'A',  DATE_ADD(CURDATE(), INTERVAL 10 DAY),  @statusCode);
-- expected error code: 460
