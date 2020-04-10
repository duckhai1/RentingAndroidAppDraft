/*
    Test if the request is accepted when all the parameter are all valid 
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenterCourt('C', 'A', 'B');
-- expected: no error code


/*
    Test if the court is not created when the id already exists in the database
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createCityCenterCourt('C', 'A', 'B');
-- expected: error code 404


/*
    Test if the request is rejected when cityId and centerId are valid but courtId is invalid 
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenterCourt('@', 'A', 'B');
-- expected: error code: 462

/*
    Test if the request is rejected when courId and centerId are valid but cityId is invalid 
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenterCourt('C', '@', 'B');
-- expected: error code: 460

/*
    Test if the request is rejected when courId and cityId are valid but centerId is invalid 
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenterCourt('C', 'A', '@');
-- expected: error code: 461