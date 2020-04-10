/*  
    Test if the request is accepted when all parameters are valid 
*/
CALL createCity('A');
CALL createCityCenter('B',  'A');
CALL createCityCenterCourt('C',  'A', 'B');
CALL createPlayer('P');
CALL createStaff('S', 'A', 'B');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL updateBookingStatus(1, '1', 'A', 'B', 'S');
-- expected: no error code


/*  
    Test if the request is rejected when all parameters are valid except bookingId
*/
CALL createCity('A');
CALL createCityCenter('B',  'A');
CALL createCityCenterCourt('C',  'A', 'B');
CALL createPlayer('P');
CALL createStaff('S', 'A', 'B');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL updateBookingStatus(1,'2','A','B','S');
-- expected: error code: 465

/*  
    Test if the request is rejected when all parameters are valid except cityId
*/
CALL createCity('A');
CALL createCityCenter('B',  'A');
CALL createCityCenterCourt('C',  'A', 'B');
CALL createPlayer('P');
CALL createStaff('S', 'A', 'B');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL updateBookingStatus(1, '1', '@', 'B', 'S');
-- expected: error code: 460

/*  
    Test if the request is rejected when all parameters are valid except centerId
*/
CALL createCity('A');
CALL createCityCenter('B',  'A');
CALL createCityCenterCourt('C',  'A', 'B');
CALL createPlayer('P');
CALL createStaff('S', 'A', 'B');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL updateBookingStatus(1,'1','A','@','S');
-- expected: error code: 461

/*  
    Test if the request is rejected when all parameters are valid except staffId

    (1st case: wrong staff id)
*/
CALL createCity('A');
CALL createCityCenter('B',  'A');
CALL createCityCenterCourt('C',  'A', 'B');
CALL createPlayer('P');
CALL createStaff('S', 'A', 'B');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL updateBookingStatus(1,'1','A','B','@');
-- expected: error code: 401


/*  
    Test if the request is rejected when all parameters are valid except staffId

    (2nd case: Wrong sportcenter id)
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenter('D', 'A');
CALL createCityCenterCourt('C',  'A', 'B');
CALL createPlayer('P');
CALL createStaff('S', 'A', 'B');
CALL createStaff('S', 'A', 'D');
CALL createBooking('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P');
CALL updateBookingStatus(1,'1','A','D','S');
-- expected: error code: 401