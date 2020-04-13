/*
    Test if the sportcenter id id is updated, when all the provided parameters are valid
    
    (1st case)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL updateSportcenterId('Z', 'X', 'A', @statusCode);
-- expected: no error code


/*
    Test if the sportcenter id is updated, when all the provided parameters are valid
    
    (2nd case)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL updateSportcenterId('Z', 'Y', 'A', @statusCode);
-- expected: no error code


/*
    Test if the sportcenter id is updated, when all the provided parameters are valid
    
    (3rd case)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL updateSportcenterId('X', 'Z', 'B', @statusCode);
-- expected: no error code


/*
    Test if the sportcenter id is not updated and an error code is returned,
    when the new sportcenter id already exists in the database prior to the change

    (1st case)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL updateSportcenterId('Y', 'X', 'A', @statusCode);
-- expected: error code 403


/*
    Test if the sportcenter id is not updated and an error code is returned,
    when the new sportcenter id already exists in the database prior to the change

    (2nd case)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL updateSportcenterId('X', 'Y', 'A', @statusCode);
-- expected: error code 403

/*
    Test if the sportcenter id is not updated and an error code is returned,
    when the new sportcenter id is in invalid format
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL updateSportcenterId('@', 'Z', 'B', @statusCode);
-- expected: error code 461


/*
    Test if the sportcenter id is not updated and an error code is returned,
    when the old sportcenter id is invalid
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL updateSportcenterId('X', '@', 'B', @statusCode);
-- expected: error code 461


/*
    Test if the sportcenter id is not updated and an error code is returned,
    when the city id is in invalid format
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL updateSportcenterId('X', 'Z', '@', @statusCode);
-- expected: error code 460