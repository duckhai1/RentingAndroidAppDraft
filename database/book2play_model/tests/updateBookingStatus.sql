/*  
    Test if the request is accepted when all parameters are valid 
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createStaff('S', 'A', 'B', @statusCode);
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL updateBookingStatus(1, '1', 'A', 'B', 'S', @statusCode);
-- expected: no error code


/*  
    Test if the request is rejected when all parameters are valid except bookingId
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createStaff('S', 'A', 'B', @statusCode);
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL updateBookingStatus(1,'2','A','B','S', @statusCode);
-- expected: error code: 465

/*  
    Test if the request is rejected when all parameters are valid except cityId
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createStaff('S', 'A', 'B', @statusCode);
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL updateBookingStatus(1, '1', '@', 'B', 'S', @statusCode);
-- expected: error code: 460

/*  
    Test if the request is rejected when all parameters are valid except centerId
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createStaff('S', 'A', 'B', @statusCode);
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL updateBookingStatus(1,'1','A','@','S', @statusCode);
-- expected: error code: 461

/*  
    Test if the request is rejected when all parameters are valid except staffId

    (1st case: wrong staff id)
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createStaff('S', 'A', 'B', @statusCode);
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL updateBookingStatus(1,'1','A','B','@', @statusCode);
-- expected: error code: 401


/*  
    Test if the request is rejected when all parameters are valid except staffId

    (2nd case: Wrong sportcenter id)
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createStaff('S', 'A', 'B', @statusCode);
CALL createStaff('S', 'A', 'D', @statusCode);
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL updateBookingStatus(1,'1','A','D','S', @statusCode);
-- expected: error code: 401