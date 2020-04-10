/*
    Test if a relation containing the expected data is returned,
    when all the provided ids are valid

    (1st case)
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL createStaff('S', 'A', 'X');
CALL createStaff('T', 'A', 'X');
CALL createStaff('U', 'A', 'Y');
CALL createStaff('V', 'B', 'Z');
CALL getStaffInfo('S', 'A', 'X');
-- expected: no error code, a relation with 1 element is returned


/*
    Test if a relation containing the expected data is returned,
    when all the provided ids are valid

    (2nd case)
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL createStaff('S', 'A', 'X');
CALL createStaff('T', 'A', 'X');
CALL createStaff('U', 'A', 'Y');
CALL createStaff('V', 'B', 'Z');
CALL getStaffInfo('T', 'A', 'X');
-- expected: no error code, a relation with 1 element is returned


/*
    Test if a relation containing the expected data is returned,
    when all the provided ids are valid

    (3rd case)
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL createStaff('S', 'A', 'X');
CALL createStaff('T', 'A', 'X');
CALL createStaff('U', 'A', 'Y');
CALL createStaff('V', 'B', 'Z');
CALL getStaffInfo('U', 'A', 'Y');
-- expected: no error code, a relation with 1 element is returned


/*
    Test if a relation containing the expected data is returned,
    when all the provided ids are valid

    (4th case)
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL createStaff('S', 'A', 'X');
CALL createStaff('T', 'A', 'X');
CALL createStaff('U', 'A', 'Y');
CALL createStaff('V', 'B', 'Z');
CALL getStaffInfo('V', 'B', 'Z');
-- expected: no error code, a relation with 1 element is returned


/*
    Test if an error code is returned when to provided staff id is invalid
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL createStaff('S', 'A', 'X');
CALL createStaff('T', 'A', 'X');
CALL createStaff('U', 'A', 'Y');
CALL createStaff('V', 'B', 'Z');
CALL getStaffInfo('@', 'A', 'X');
-- expected: error code 463


/*
    Test if an error code is returned when to provided city id is invalid
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL createStaff('S', 'A', 'X');
CALL createStaff('T', 'A', 'X');
CALL createStaff('U', 'A', 'Y');
CALL createStaff('V', 'B', 'Z');
CALL getStaffInfo('S', '@', 'X');
-- expected: error code 460


/*
    Test if an error code is returned when to provided sportcenter id is invalid
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL createStaff('S', 'A', 'X');
CALL createStaff('T', 'A', 'X');
CALL createStaff('U', 'A', 'Y');
CALL createStaff('V', 'B', 'Z');
CALL getStaffInfo('S', 'A', '@');
-- expected: error code 461