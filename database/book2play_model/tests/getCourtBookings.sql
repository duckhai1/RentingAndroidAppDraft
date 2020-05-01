/*
    Test if a relation containing the expected data is returned,
    when all the parameters are correct
    getCourtBooking(courtId, cityId, sportCenterId, date, statusCode)

    (1st case)
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
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(sNOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'B', 'B', 'C', 'P', @statusCode);
CALL getCourtBooking('C', 'A', 'B',  DATE_ADD(CURDATE(), INTERVAL 10 DAY, @statusCode)
-- expected: no error code, a relation with 1 elements is returned

/*
    Test if a relation containing the expected data is returned,
    when all the parameters are correct
    getCourtBooking(courtId, cityId, sportCenterId, date, statusCode)

    (3rd case)
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
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'B', 'B', 'C', 'P', @statusCode);
CALL getCourtBooking('C', 'B', 'B',  DATE_ADD(CURDATE(), INTERVAL 10 DAY, @statusCode)
-- expected: no error code, a relation with 1 elements is returned

/*
    Test if a relation containing the expected data is returned,
    when all the parameters are correct
    getCourtBooking(courtId, cityId, sportCenterId, date, statusCode)

    (3rd case)
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
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 11 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'B', 'B', 'C', 'P', @statusCode);
CALL getCourtBooking('A', 'B', 'C',  DATE_ADD(CURDATE(), INTERVAL 11 DAY, @statusCode)
-- expected: no error code, a relation with 1 elements is returned

/*
    Test if a relation containing the expected data is not returned,
    when the sportcenter id is invalid
    1st case
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('B', 'B', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode); 
CALL createCityCenterCourt('C', 'B', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL getCourtBooking('C', 'A', '@',  DATE_ADD(CURDATE(), INTERVAL 10 DAY, @statusCode)
-- expected: error code 461

/*
    Test if a relation containing the expected data is not returned,
    when the sportcenter id is not found
    2nd case
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('B', 'B', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode); 
CALL createCityCenterCourt('C', 'B', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL getCourtBooking('C', 'A', 'C',  DATE_ADD(CURDATE(), INTERVAL 10 DAY, @statusCode)
-- expected: error code 461


/*
    Test if a relation containing the expected data is not returned,
    when the city id is invalid
    1st case
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('B', 'B', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode); 
CALL createCityCenterCourt('C', 'B', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL getCourtBooking('C', '@', 'B',  DATE_ADD(CURDATE(), INTERVAL 10 DAY, @statusCode)
-- expected: error code 460

/*
    Test if a relation containing the expected data is not returned,
    when the city id is not found
    2nd case
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('B', 'B', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode); 
CALL createCityCenterCourt('C', 'B', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL getCourtBooking('C', 'C', 'B',  DATE_ADD(CURDATE(), INTERVAL 10 DAY, @statusCode)
-- expected: error code 460


/*
    Test if a relation containing the expected data is not returned,
    when the court id is invalid
    1st case
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('B', 'B', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode); 
CALL createCityCenterCourt('C', 'B', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL getCourtBooking('@', 'A', 'B',  DATE_ADD(CURDATE(), INTERVAL 10 DAY, @statusCode)
-- expected: error code 462

/*
    Test if a relation containing the expected data is not returned,
    when the court id is not found
    2nd case
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('B', 'B', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode); 
CALL createCityCenterCourt('C', 'B', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL getCourtBooking('G', 'A', 'B',  DATE_ADD(CURDATE(), INTERVAL 10 DAY, @statusCode)
-- expected: error code 462