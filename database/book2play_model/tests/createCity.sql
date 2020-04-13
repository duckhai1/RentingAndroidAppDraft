/*
    Test if the city is created correctly 
*/
CALL createCity('1', @statusCode);
-- expected: no error code


/*
    Test if the city is not created when the id already exists in the database
*/
CALL createCity('1', @statusCode);
CALL createCity('1', @statusCode);
-- expected: error code 402


/* 
    Test if the request is rejected when cityId is invalid
*/
CALL createCity('@', @statusCode);
-- expected: error code 460