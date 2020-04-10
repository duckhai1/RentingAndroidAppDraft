/*
    Test if a relation containing the expected data is returned,
    when all the provided ids are valid

    (1st case)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL createStaff('S', 'A', 'X', @statusCode);
CALL createStaff('T', 'A', 'X', @statusCode);
CALL createStaff('U', 'A', 'Y', @statusCode);
CALL createStaff('V', 'B', 'Z', @statusCode);
CALL getStaffInfo('S', 'A', 'X', @statusCode);
-- expected: no error code, a relation with 1 element is returned


/*
    Test if a relation containing the expected data is returned,
    when all the provided ids are valid

    (2nd case)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL createStaff('S', 'A', 'X', @statusCode);
CALL createStaff('T', 'A', 'X', @statusCode);
CALL createStaff('U', 'A', 'Y', @statusCode);
CALL createStaff('V', 'B', 'Z', @statusCode);
CALL getStaffInfo('T', 'A', 'X', @statusCode);
-- expected: no error code, a relation with 1 element is returned


/*
    Test if a relation containing the expected data is returned,
    when all the provided ids are valid

    (3rd case)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL createStaff('S', 'A', 'X', @statusCode);
CALL createStaff('T', 'A', 'X', @statusCode);
CALL createStaff('U', 'A', 'Y', @statusCode);
CALL createStaff('V', 'B', 'Z', @statusCode);
CALL getStaffInfo('U', 'A', 'Y', @statusCode);
-- expected: no error code, a relation with 1 element is returned


/*
    Test if a relation containing the expected data is returned,
    when all the provided ids are valid

    (4th case)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL createStaff('S', 'A', 'X', @statusCode);
CALL createStaff('T', 'A', 'X', @statusCode);
CALL createStaff('U', 'A', 'Y', @statusCode);
CALL createStaff('V', 'B', 'Z', @statusCode);
CALL getStaffInfo('V', 'B', 'Z', @statusCode);
-- expected: no error code, a relation with 1 element is returned


/*
    Test if an error code is returned when to provided staff id is invalid
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL createStaff('S', 'A', 'X', @statusCode);
CALL createStaff('T', 'A', 'X', @statusCode);
CALL createStaff('U', 'A', 'Y', @statusCode);
CALL createStaff('V', 'B', 'Z', @statusCode);
CALL getStaffInfo('@', 'A', 'X', @statusCode);
-- expected: error code 463


/*
    Test if an error code is returned when to provided city id is invalid
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL createStaff('S', 'A', 'X', @statusCode);
CALL createStaff('T', 'A', 'X', @statusCode);
CALL createStaff('U', 'A', 'Y', @statusCode);
CALL createStaff('V', 'B', 'Z', @statusCode);
CALL getStaffInfo('S', '@', 'X', @statusCode);
-- expected: error code 460


/*
    Test if an error code is returned when to provided sportcenter id is invalid
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL createStaff('S', 'A', 'X', @statusCode);
CALL createStaff('T', 'A', 'X', @statusCode);
CALL createStaff('U', 'A', 'Y', @statusCode);
CALL createStaff('V', 'B', 'Z', @statusCode);
CALL getStaffInfo('S', 'A', '@', @statusCode);
-- expected: error code 461