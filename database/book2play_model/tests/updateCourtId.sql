/*
    Test if the court id is updated correctly when all the parameters are correct

    (1st case)
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('D', 'A', 'B', @statusCode);
CALL createCityCenterCourt('E', 'A', 'B', @statusCode);
CALL createCityCenterCourt('F', 'A', 'C', @statusCode);
CALL createCityCenterCourt('G', 'A', 'C', @statusCode);
CALL updateCourtId('F', 'C', 'A', 'B', @statusCode);
-- expected: no error code


/*
    Test if the court id is updated correctly when all the parameters are correct

    (2nd case)
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('D', 'A', 'B', @statusCode);
CALL createCityCenterCourt('E', 'A', 'B', @statusCode);
CALL createCityCenterCourt('F', 'A', 'C', @statusCode);
CALL createCityCenterCourt('G', 'A', 'C', @statusCode);
CALL updateCourtId('G', 'E', 'A', 'B', @statusCode);
-- expected: no error code


/*
    Test if the court id is not updated when the new id already exists in the database prior to change

    (1st case)
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('D', 'A', 'B', @statusCode);
CALL createCityCenterCourt('E', 'A', 'B', @statusCode);
CALL createCityCenterCourt('F', 'A', 'C', @statusCode);
CALL createCityCenterCourt('G', 'A', 'C', @statusCode);
CALL updateCourtId('G', 'F', 'A', 'C', @statusCode);
-- expected: error code 404


/*
    Test if the court id is not updated when the new id already exists in the database prior to change

    (2nd case)
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('D', 'A', 'B', @statusCode);
CALL createCityCenterCourt('E', 'A', 'B', @statusCode);
CALL createCityCenterCourt('F', 'A', 'C', @statusCode);
CALL createCityCenterCourt('G', 'A', 'C', @statusCode);
CALL updateCourtId('F', 'F', 'A', 'C', @statusCode);
-- expected: error code 404


/*
    Test if the court id is not updated when the new court id is in invalid format
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('D', 'A', 'B', @statusCode);
CALL createCityCenterCourt('E', 'A', 'B', @statusCode);
CALL createCityCenterCourt('F', 'A', 'C', @statusCode);
CALL createCityCenterCourt('G', 'A', 'C', @statusCode);
CALL updateCourtId('@', 'H', 'A', 'C', @statusCode);
-- expected: error code 462


/*
    Test if the court id is not updated when the old court id does not exist in the database
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('D', 'A', 'B', @statusCode);
CALL createCityCenterCourt('E', 'A', 'B', @statusCode);
CALL createCityCenterCourt('F', 'A', 'C', @statusCode);
CALL createCityCenterCourt('G', 'A', 'C', @statusCode);
CALL updateCourtId('I', 'H', 'A', 'C', @statusCode);
-- expected: error code 462


/*
    Test if the court id is not updated when the city id does not exist in the database
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('D', 'A', 'B', @statusCode);
CALL createCityCenterCourt('E', 'A', 'B', @statusCode);
CALL createCityCenterCourt('F', 'A', 'C', @statusCode);
CALL createCityCenterCourt('G', 'A', 'C', @statusCode);
CALL updateCourtId('H', 'G', 'B', 'C', @statusCode);
-- expected: error code 460


/*
    Test if the court id is not updated when the sportcenter id does not exist in the database
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createCityCenterCourt('D', 'A', 'B', @statusCode);
CALL createCityCenterCourt('E', 'A', 'B', @statusCode);
CALL createCityCenterCourt('F', 'A', 'C', @statusCode);
CALL createCityCenterCourt('G', 'A', 'C', @statusCode);
CALL updateCourtId('H', 'G', 'A', 'K', @statusCode);
-- expected: error code 461