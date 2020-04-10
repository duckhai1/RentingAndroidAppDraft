/*
    Test if all information about the sportcenter in the database is returned,
    when all the parameters are correct

    (1st case)
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL getSportcenterInfo('X', 'A');
-- expected: no error code, a relation with 1 element is returned


/*
    Test if all information about the sportcenter in the database is returned,
    when all the parameters are correct

    (2nd case)
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL getSportcenterInfo('Y', 'A');
-- expected: no error code, a relation with 1 element is returned


/*
    Test if all information about the sportcenter in the database is returned,
    when all the parameters are correct

    (3rd case)
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL getSportcenterInfo('Z', 'B');
-- expected: no error code, a relation with 1 element is returned


/*
    Test if an error code is returned when the provided sportcenter id is invalid
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL getSportcenterInfo('@', 'B');
-- expected: error code 461


/*
    Test if an error code is returned when the provided city id is invalid
*/
CALL createCity('A');
CALL createCity('B');
CALL createCityCenter('X', 'A');
CALL createCityCenter('Y', 'A');
CALL createCityCenter('Z', 'B');
CALL getSportcenterInfo('Z', '@');
-- expected: error code 460