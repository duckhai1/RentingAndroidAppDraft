/* test createPlayer interface */
/* version 2.0 */ 
/* createPlayer(playerId) */ 

/*
    Test if the request is accepted when the playerId is valid
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

CALL createPlayer('phat1');
    -- expected success code

/* 
    Test if the request is rejected when the playerId is invalid
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

CALL createPlayer('@@');
    -- expected error: 478
