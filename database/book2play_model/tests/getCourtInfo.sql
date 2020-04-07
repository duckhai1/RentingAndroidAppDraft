/*
    Test if the court information is returned when all the given ids are valid, i.e.,
    they are able to uniquely identify a row in the courts relation
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenterCourt('C', 'A', 'B');
CALL getCourtInfo('C', 'A', 'B');
-- expected: no error code, a relation with 1 element is returned


/*
    Test if the court information is not returned when the court id does not exists
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenterCourt('C', 'A', 'B');
CALL getCourtInfo('@', 'A', 'B');
-- expected: error code 462


/*
    Test if the court information is not returned when the city id does not exists
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenterCourt('C', 'A', 'B');
CALL getCourtInfo('C', '@', 'B');
-- expected: error code 460


/*
    Test if the court information is not returned when the sportcenter id does not exists
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenterCourt('C', 'A', 'B');
CALL getCourtInfo('C', 'A', '@');
-- expected: error code 461