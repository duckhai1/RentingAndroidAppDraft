/*  
    Test if the request is accepted when all parameters are valid
*/
CALL createCity('1');
CALL createCityCenter('A', '1');
CALL createStaff('S', '1', 'A');
-- expected: no error code


/*  
    Test if the request is not accepted when staff id already exists in the database
*/
CALL createCity('1');
CALL createCityCenter('A', '1');
CALL createStaff('S', '1', 'A');
CALL createStaff('S', '1', 'A');
-- expected: error code 406


/*
    Test if the request is rejected when cityId and centerId are valid but the staffId is invalid
*/
CALL createCity('1');
CALL createCityCenter('A', '1');
CALL createStaff('@','1','A');
-- expected: error code: 463


/*
    Test if the request is rejected when staffId and centerId are correct but the cityId is invalid
*/
CALL createCity('1');
CALL createCityCenter('A', '1');
CALL createStaff('S','@','A');
-- expected: error code: 460


/*
    Test if the request is rejected when staffId and cityId are correct but the centerId is invalid
*/
CALL createCity('1');
CALL createCityCenter('A', '1');
CALL createStaff('S','1','@');
-- expected: error code: 461