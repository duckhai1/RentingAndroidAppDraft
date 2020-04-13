/*
    Test if all information about the sportcenter in the database is returned,
    when all the parameters are correct

    (1st case)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL getSportcenterInfo('X', 'A', @statusCode);
-- expected: no error code, a relation with 1 element is returned


/*
    Test if all information about the sportcenter in the database is returned,
    when all the parameters are correct

    (2nd case)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL getSportcenterInfo('Y', 'A', @statusCode);
-- expected: no error code, a relation with 1 element is returned


/*
    Test if all information about the sportcenter in the database is returned,
    when all the parameters are correct

    (3rd case)
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL getSportcenterInfo('Z', 'B', @statusCode);
-- expected: no error code, a relation with 1 element is returned


/*
    Test if an error code is returned when the provided sportcenter id is invalid
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL getSportcenterInfo('@', 'B', @statusCode);
-- expected: error code 461


/*
    Test if an error code is returned when the provided city id is invalid
*/
CALL createCity('A', @statusCode);
CALL createCity('B', @statusCode);
CALL createCityCenter('X', 'A', @statusCode);
CALL createCityCenter('Y', 'A', @statusCode);
CALL createCityCenter('Z', 'B', @statusCode);
CALL getSportcenterInfo('Z', '@', @statusCode);
-- expected: error code 460