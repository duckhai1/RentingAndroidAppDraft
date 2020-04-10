/* test createCenterCity interface */
/* version 2.0 */ 
/* createCityCenter(centerId, cityId) */

/*
    Test if the creteCenterCity request is accepted when all parameter are valid 
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

INSERT INTO cites(cityId) VALUES ('1');

CALL createCenterCity('1','1');
    -- expected success code
/*
    Test if the createCenterCity request is rejected when cityId is valid and centerId is invalid
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

INSERT INTO cites(cityId) VALUES ('1');

CALL createCenterCity('@','1');
    -- expected error: 462 

/*
    Test if the createCenterCity request is rejected when centerId is valid and cityId is invalid
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

INSERT INTO cites(cityId) VALUES ('1');

CALL createCenterCity('1','@');
    -- expected error: 461