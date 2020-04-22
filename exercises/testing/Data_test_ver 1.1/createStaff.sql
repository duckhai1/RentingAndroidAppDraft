/* test createStaff interface */
/* version 2.0 */
/* createStaff(staffId, cityId, centerId) */ 

/*  
    Test if the request is accepted when all parameters are valid
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

INSERT INTO cities(cityId) VALUES ('1');
INSERT INTO cityCenter(centerId, cityId) VALUES ('A','1');

CALL createStaff('staff1','1','A')
    -- expected success code

/*
    Test if the request is rejected when cityId and centerId are valid but the staffId is invalid
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

INSERT INTO cities(cityId) VALUES ('1');
INSERT INTO cityCenter(centerId, cityId) VALUES ('A','1');

CALL createStaff('@@','1','A')
    -- expected error: 

/*
    Test if the request is rejected when staffid and centerId are correct but the cityId is invalid
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

INSERT INTO cities(cityId) VALUES ('1');
INSERT INTO cityCenter(centerId, cityId) VALUES ('A','1');

CALL createStaff('Staff1','@','A')
    -- expected error: 461

/*
    Test if the request is rejected when staffid and cityId are correct but the centerId is invalid
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

INSERT INTO cities(cityId) VALUES ('1');
INSERT INTO cityCenter(centerId, cityId) VALUES ('A','1');

CALL createStaff('Staff1','1','@@')
    -- expected error: 462
