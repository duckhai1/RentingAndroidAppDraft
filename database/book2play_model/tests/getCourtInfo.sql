/*
    Test if the court information is returned when all the given ids are valid, i.e.,
    they are able to uniquely identify a row in the courts relation
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL getCourtInfo('C', 'A', 'B', @statusCode);
-- expected: no error code, a relation with 1 element is returned


/*
    Test if the court information is not returned when the court id does not exists
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL getCourtInfo('@', 'A', 'B', @statusCode);
-- expected: error code 462


/*
    Test if the court information is not returned when the city id does not exists
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL getCourtInfo('C', '@', 'B', @statusCode);
-- expected: error code 460


/*
    Test if the court information is not returned when the sportcenter id does not exists
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL getCourtInfo('C', 'A', '@', @statusCode);
-- expected: error code 461