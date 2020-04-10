/* test createCityCenterCourt interface */
/* version 2.0 */ 
/* createCityCenterCourt(courtId, cityId, centerId) */

/*
    Test if the request is accepted when all the parameter are all valid 
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

INSERT INTO cities(cityId) VALUES (1);
INSERT INTO cityCenter(centerId, cityId) VALUES ('A',1);

CALL createCityCenterCourt('A',1,'A');
    -- expected success code

/*
    Test if the request is rejected when cityId and centerId are valid but courtId is invalid 
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

INSERT INTO cities(cityId) VALUES (1);
INSERT INTO cityCenter(centerId, cityId) VALUES ('A','1');

CALL createCityCenterCourt('@','1','A');
    -- expected error: 469

/*
    Test if the request is rejected when courId and centerId are valid but cityId is invalid 
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

INSERT INTO cities(cityId) VALUES (1);
INSERT INTO cityCenter(centerId, cityId) VALUES ('A','1');

CALL createCityCenterCourt('A','@','A');
    -- expected error: 461

/*
    Test if the request is rejected when courId and cityId are valid but centerId is invalid 
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

INSERT INTO cities(cityId) VALUES ('1');
INSERT INTO cityCenter(centerId, cityId) VALUES ('A','1');

CALL createCityCenterCourt('A','1','@');
    -- expected error: 462