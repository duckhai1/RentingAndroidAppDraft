/*
    Test if the creteCenterCity request is accepted when all parameter are valid 
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('1', 'A', @statusCode);
-- expected: no error code


/*
    Test if the creteCenterCity request is accepted when all parameter are valid 
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('1', 'A', @statusCode);
CALL createCityCenter('1', 'A', @statusCode);
-- expected: error code 403


/*
    Test if the createCityCenter request is rejected when cityId is valid and centerId is in invalid format
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('@', 'A', @statusCode);
-- expected: error code: 461 


/*
    Test if the createCityCenter request is rejected when centerId is valid and cityId is invalid
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('1', 'B', @statusCode);
-- expected: error code: 460