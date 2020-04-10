/* test createCity interface */
/* version 2.0 */ 
/* createCity(cityId) */

/*
    Test if the city is created correctly 
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

CALL createCity('1');
    -- expected success code

/* 
    Test if the request is rejected when cityId is invalid
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

CALL createCity('@');
    -- expected error: 461