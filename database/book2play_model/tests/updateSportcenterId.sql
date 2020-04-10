/*
    Test if the sportcenter id id is updated, when all the provided parameters are valid
    
    (1st case)
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL updateSportcenterId('Z', 'X', 'A');
-- expected: no error code


/*
    Test if the sportcenter id is updated, when all the provided parameters are valid
    
    (2nd case)
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL updateSportcenterId('Z', 'Y', 'A');
-- expected: no error code


/*
    Test if the sportcenter id is updated, when all the provided parameters are valid
    
    (3rd case)
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL updateSportcenterId('X', 'Z', 'B');
-- expected: no error code


/*
    Test if the sportcenter id is not updated and an error code is returned,
    when the new sportcenter id already exists in the database prior to the change

    (1st case)
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL updateSportcenterId('Y', 'X', 'A');
-- expected: error code 403


/*
    Test if the sportcenter id is not updated and an error code is returned,
    when the new sportcenter id already exists in the database prior to the change

    (2nd case)
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL updateSportcenterId('X', 'Y', 'A');
-- expected: error code 403

/*
    Test if the sportcenter id is not updated and an error code is returned,
    when the new sportcenter id is in invalid format
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL updateSportcenterId('@', 'Z', 'B');
-- expected: error code 461


/*
    Test if the sportcenter id is not updated and an error code is returned,
    when the old sportcenter id is invalid
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL updateSportcenterId('X', '@', 'B');
-- expected: error code 461


/*
    Test if the sportcenter id is not updated and an error code is returned,
    when the city id is in invalid format
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL updateSportcenterId('X', 'Z', '@');
-- expected: error code 460