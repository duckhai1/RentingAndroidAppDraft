/*
    Test if a relation containing the expected data is returned,
    when all the parameters are correct
*/
CALL createCity('A',@statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'A', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenterCourt('A', 'A', 'B', @statusCode); 
CALL createCityCenterCourt('B', 'A', 'B', @statusCode); 
CALL createCityCenterCourt('C', 'A', 'C', @statusCode); 
CALL createCityCenterCourt('D', 'A', 'C', @statusCode); 
CALL createCityCenterCourt('E', 'A', 'D', @statusCode); 
CALL getSportCenterCourts('B', 'A', @statusCode);
-- expected no error code

/*
    Test if a relation containing the expected data is not returned,
    when the city id is invalid
    1st case
*/
CALL createCity('A',@statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'A', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenterCourt('A', 'A', 'B', @statusCode); 
CALL createCityCenterCourt('B', 'A', 'B', @statusCode); 
CALL createCityCenterCourt('C', 'A', 'C', @statusCode); 
CALL createCityCenterCourt('D', 'A', 'C', @statusCode); 
CALL createCityCenterCourt('E', 'A', 'D', @statusCode); 
CALL getSportCenterCourts('B', '@', @statusCode);
-- expected error code 460

/*
    Test if a relation containing the expected data is not returned,
    when the city id is not found
    2nd case
*/
CALL createCity('A',@statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'A', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenterCourt('A', 'A', 'B', @statusCode); 
CALL createCityCenterCourt('B', 'A', 'B', @statusCode); 
CALL createCityCenterCourt('C', 'A', 'C', @statusCode); 
CALL createCityCenterCourt('D', 'A', 'C', @statusCode); 
CALL createCityCenterCourt('E', 'A', 'D', @statusCode); 
CALL getSportCenterCourts('B', 'B', @statusCode);
-- expected error code 460


/*
    Test if a relation containing the expected data is not returned,
    when the sport center id is invalid
    1st case
*/
CALL createCity('A',@statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'A', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenterCourt('A', 'A', 'B', @statusCode); 
CALL createCityCenterCourt('B', 'A', 'B', @statusCode); 
CALL createCityCenterCourt('C', 'A', 'C', @statusCode); 
CALL createCityCenterCourt('D', 'A', 'C', @statusCode); 
CALL createCityCenterCourt('E', 'A', 'D', @statusCode); 
CALL getSportCenterCourts('@', 'A', @statusCode);
-- expected error code 461

/*
    Test if a relation containing the expected data is not returned,
    when the sport center id is not found
    2nd case
*/
CALL createCity('A',@statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenter('C', 'A', @statusCode);
CALL createCityCenter('D', 'A', @statusCode);
CALL createCityCenterCourt('A', 'A', 'B', @statusCode); 
CALL createCityCenterCourt('B', 'A', 'B', @statusCode); 
CALL createCityCenterCourt('C', 'A', 'C', @statusCode); 
CALL createCityCenterCourt('D', 'A', 'C', @statusCode); 
CALL createCityCenterCourt('E', 'A', 'D', @statusCode); 
CALL getSportCenterCourts('E', 'A', @statusCode);
-- expected error code 461
