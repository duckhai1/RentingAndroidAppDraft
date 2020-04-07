/*
    Test if the city is created correctly 
*/
CALL createCity('1');
-- expected: no error code


/*
    Test if the city is not created when the id already exists in the database
*/
CALL createCity('1');
CALL createCity('1');
-- expected: error code 402


/* 
    Test if the request is rejected when cityId is invalid
*/
CALL createCity('@');
-- expected: error code 460