/*
    Test if a relation containing the expected data is returned,
    when all the parameters are correct
*/
CALL createCity('A',@statusCode);
CALL createCity('B', @statusCode)
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'A', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenter('D', 'B', @statusCode);
CALL createCityCenter('D', 'B', @statusCode);
CALL createCityCenter('D', 'B', @statusCode);
CALL getCitySportCenters('A', @statusCode);
-- expected no error code

/*
    Test if a relation containing the expected data is not returned,
    when the city id is invalid
    1st case
*/
CALL createCity('A',@statusCode);
CALL createCity('B', @statusCode)
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'A', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenter('D', 'B', @statusCode);
CALL createCityCenter('D', 'B', @statusCode);
CALL createCityCenter('D', 'B', @statusCode);
CALL getCitySportCenters('@', @statusCode);
-- expected error code: 460

/*
    Test if a relation containing the expected data is not returned,
    when the city id is not found
    2nd case
*/
CALL createCity('A',@statusCode);
CALL createCity('B', @statusCode)
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'A', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenter('D', 'B', @statusCode);
CALL createCityCenter('D', 'B', @statusCode);
CALL createCityCenter('D', 'B', @statusCode);
CALL getCitySportCenters('C', @statusCode);
-- expected error code: 460
