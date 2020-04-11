/* test updateBookingStatus interface */
/* version 1.0 */
/* updateBookingStatus(status, bookingId, cityId, centerId, staffId) */

/*  
    Test if the request is accepted when all parameters are valid 
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

INSERT INTO cities (cityId) VALUES ('A');
INSERT INTO cityCenters (centerId, cityId) VALUES ('B', 'A');
INSERT INTO cityCenterCourts (courtId, cityId, centerId) VALUES ('C', 'A', 'B');
INSERT INTO players (playerId) VALUES ('P');
INSERT INTO staffs (staffId, cityId, centerId) VALUES ('staff1','A','B');
INSERT INTO bookings(
    bookingId,
    createdAt,
    bookingDate,
    bookingStartTime,
    bookingEndTime,
    cityID,
    sportcenterId,
    courtId,
    playerId
)
VALUES
    ('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), "09:00:00", "10:30:00" 'A', 'B', 'C', 'P');

CALL updateBookingStatus(1,'1','A','B','staff1');
    -- expected success code

/*  
    Test if the request is rejected when all parameters are valid except status 
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

INSERT INTO cities (cityId) VALUES ('A');
INSERT INTO cityCenters (centerId, cityId) VALUES ('B', 'A');
INSERT INTO cityCenterCourts (courtId, cityId, centerId) VALUES ('C', 'A', 'B');
INSERT INTO players (playerId) VALUES ('P');
INSERT INTO staffs (staffId, cityId, centerId) VALUES ('staff1','A','B');
INSERT INTO bookings(
    bookingId,
    createdAt,
    bookingDate,
    bookingStartTime,
    bookingEndTime,
    cityID,
    sportcenterId,
    courtId,
    playerId
)
VALUES
    ('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), "09:00:00", "10:30:00" 'A', 'B', 'C', 'P');

CALL updateBookingStatus('a','1','A','B','staff1');
    -- expected error: 4xx

/*  
    Test if the request is rejected when all parameters are valid except bookingId
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

INSERT INTO cities (cityId) VALUES ('A');
INSERT INTO cityCenters (centerId, cityId) VALUES ('B', 'A');
INSERT INTO cityCenterCourts (courtId, cityId, centerId) VALUES ('C', 'A', 'B');
INSERT INTO players (playerId) VALUES ('P');
INSERT INTO staffs (staffId, cityId, centerId) VALUES ('staff1','A','B');
INSERT INTO bookings(
    bookingId,
    createdAt,
    bookingDate,
    bookingStartTime,
    bookingEndTime,
    cityID,
    sportcenterId,
    courtId,
    playerId
)
VALUES
    ('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), "09:00:00", "10:30:00" 'A', 'B', 'C', 'P');

CALL updateBookingStatus(1,'A','A','B','staff1');
    -- expected error: 410

/*  
    Test if the request is rejected when all parameters are valid except cityId
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

INSERT INTO cities (cityId) VALUES ('A');
INSERT INTO cityCenters (centerId, cityId) VALUES ('B', 'A');
INSERT INTO cityCenterCourts (courtId, cityId, centerId) VALUES ('C', 'A', 'B');
INSERT INTO players (playerId) VALUES ('P');
INSERT INTO staffs (staffId, cityId, centerId) VALUES ('staff1','A','B');
INSERT INTO bookings(
    bookingId,
    createdAt,
    bookingDate,
    bookingStartTime,
    bookingEndTime,
    cityID,
    sportcenterId,
    courtId,
    playerId
)
VALUES
    ('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), "09:00:00", "10:30:00" 'A', 'B', 'C', 'P');

CALL updateBookingStatus(1,'1','@','B','staff1');
    -- expected error: 461

/*  
    Test if the request is rejected when all parameters are valid except centerId
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

INSERT INTO cities (cityId) VALUES ('A');
INSERT INTO cityCenters (centerId, cityId) VALUES ('B', 'A');
INSERT INTO cityCenterCourts (courtId, cityId, centerId) VALUES ('C', 'A', 'B');
INSERT INTO players (playerId) VALUES ('P');
INSERT INTO staffs (staffId, cityId, centerId) VALUES ('staff1','A','B');
INSERT INTO bookings(
    bookingId,
    createdAt,
    bookingDate,
    bookingStartTime,
    bookingEndTime,
    cityID,
    sportcenterId,
    courtId,
    playerId
)
VALUES
    ('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), "09:00:00", "10:30:00" 'A', 'B', 'C', 'P');

CALL updateBookingStatus(1,'1','A','@','staff1');
    -- expected error: 462

/*  
    Test if the request is rejected when all parameters are valid except staffId
*/
DELETE FROM cities;
DELETE FROM cityCenters;
DELETE FROM cityCenterCourts;
DELETE FROM staffs;
DELETE FROM players;
DELETE FROM bookings;

INSERT INTO cities (cityId) VALUES ('A');
INSERT INTO cityCenters (centerId, cityId) VALUES ('B', 'A');
INSERT INTO cityCenterCourts (courtId, cityId, centerId) VALUES ('C', 'A', 'B');
INSERT INTO players (playerId) VALUES ('P');
INSERT INTO staffs (staffId, cityId, centerId) VALUES ('staff1','A','B');
INSERT INTO bookings(
    bookingId,
    createdAt,
    bookingDate,
    bookingStartTime,
    bookingEndTime,
    cityID,
    sportcenterId,
    courtId,
    playerId
)
VALUES
    ('1', NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), "09:00:00", "10:30:00" 'A', 'B', 'C', 'P');

CALL updateBookingStatus(1,'1','A','@','A');
    -- expected error: 4xx

