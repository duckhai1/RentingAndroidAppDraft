/*  
    Test if the request is accepted when all parameters are valid
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createStaff('S', 'A', 'B', @statusCode);
-- expected: no error code


/*  
    Test if the request is not accepted when staff id already exists in the database
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createStaff('S', 'A', 'B', @statusCode);
CALL createStaff('S', 'A', 'B', @statusCode);
-- expected: error code 406


/*
    Test if the request is rejected when cityId and centerId are valid but the staffId is invalid
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createStaff('@','A','B', @statusCode);
-- expected: error code: 463


/*
    Test if the request is rejected when staffId and centerId are correct but the cityId is invalid
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createStaff('S','@','B', @statusCode);
-- expected: error code: 460


/*
    Test if the request is rejected when staffId and cityId are correct but the centerId is invalid
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createStaff('S','A','@', @statusCode);
-- expected: error code: 461