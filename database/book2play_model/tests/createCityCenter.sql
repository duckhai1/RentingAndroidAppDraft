/*
    Test if the creteCenterCity request is accepted when all parameter are valid 
*/
CALL createCity('A');
CALL createCityCenter('1', 'A');
-- expected: no error code


/*
    Test if the creteCenterCity request is accepted when all parameter are valid 
*/
CALL createCity('A');
CALL createCityCenter('1', 'A');
CALL createCityCenter('1', 'A');
-- expected: error code 403


/*
    Test if the createCityCenter request is rejected when cityId is valid and centerId is in invalid format
*/
CALL createCity('A');
CALL createCityCenter('@', 'A');
-- expected: error code: 461 


/*
    Test if the createCityCenter request is rejected when centerId is valid and cityId is invalid
*/
CALL createCity('A');
CALL createCityCenter('1', 'B');
-- expected: error code: 460