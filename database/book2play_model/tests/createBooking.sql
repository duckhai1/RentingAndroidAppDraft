/*
    Test if the booking is created when the player does no have upcomming bookings,
    and all the provided date, times, and ids are correct
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '11:00:00', '12:00:00', 'A', 'B', 'C', 'P', @statusCode);
-- expected: no error code


/*
    Test if the booking is not created because the booking id already exists in the database
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '11:00:00', '12:00:00', 'A', 'B', 'C', 'P', @statusCode);
-- expected: error code 407


/*
    Test if the booking is created when the player has 1 upcomming booking,
    and all the provided date, times, and ids are correct
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 8 DAY), '11:00:00', '12:00:00', 'A', 'B', 'C', 'P', @statusCode);
-- expected: no error code



/*
    Test if the booking is created when the player has 2 upcomming booking,
    and all the provided date, times, and ids are correct
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 8 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 9 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
-- expected: no error code


/*
    Test if the booking is not created when the player has 3 upcomming booking,
    and all the provided date, times, and ids are correct
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 8 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 9 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 10 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
-- expected: error code 410


/*
    Test if the booking is created when the player has 1 booking in the past that were paid,
    and all the provided date, times, and ids are correct
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
INSERT INTO bookings (
    inTimeStamp,
    inDate,
    inBookingStartTime,
    inBookingEndTime,
    inCityId,
    inSportcenterId,
    inCourtId,
    inPlayerId
)
VALUES
    (
        DATE_ADD(NOW(), INTERVAL -17 DAY), DATE_ADD(CURDATE(), INTERVAL -7 DAY), '09:00:00', '10:30:00',
        (
            SELECT cityId 
            FROM cities 
            WHERE cityId = 'A'),
        (
            SELECT sportCenterId 
            FROM sportCenters NATURAL JOIN cities
		    WHERE cityId = 'A'),
        (
            SELECT courtId 
            FROM courts NATURAL JOIN sportCenters NATURAL JOIN cities
            WHERE cityId = 'A' AND sportCenterId = 'B' AND courtId = 'C'),
        (
            SELECT playerId
            FROM players
            WHERE playerId = 'P'
        ),
        (   
            FROM bookings
		    NATURAL JOIN players
		    WHERE playerId = inPlayerId AND isPaid = TRUE
        )
    );
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '11:00:00', '12:00:00', 'A', 'B', 'C', 'P', @statusCode);
-- expected: no error code

/*
    Test if the booking is not created when the player has 1 booking in the past that has not been paid,
    and all the provided date, times, and ids are correct
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
INSERT INTO bookings (
    inTimeStamp,
    inDate,
    inBookingStartTime,
    inBookingEndTime,
    inCityId,
    inSportcenterId,
    inCourtId,
    inPlayerId
)
VALUES
    (
        DATE_ADD(NOW(), INTERVAL -17 DAY), DATE_ADD(CURDATE(), INTERVAL -7 DAY), '09:00:00', '10:30:00',
        (
            SELECT cityId 
            FROM cities 
            WHERE cityId = 'A'),
        (
            SELECT sportCenterId 
            FROM sportCenters NATURAL JOIN cities
		    WHERE cityId = 'A'),
        (
            SELECT courtId 
            FROM courts NATURAL JOIN sportCenters NATURAL JOIN cities
            WHERE cityId = 'A' AND sportCenterId = 'B' AND courtId = 'C'),
        (
            SELECT playerId
            FROM players
            WHERE playerId = 'P'
        ),
        (   
            FROM bookings
		    NATURAL JOIN players
		    WHERE playerId = inPlayerId AND isPaid = FALSE
        )
    );
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '11:00:00', '12:00:00', 'A', 'B', 'C', 'P', @statusCode);
-- expected: error code 412


/*
    Test if two bookings in consecutive time duration can be made,
    and all the provided date, times, and ids are correct

    (test for unexpected behaviors happened with previous implementation)
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('X', @statusCode);
CALL createPlayer('Y', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'X', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '10:30:00', '12:00:00', 'A', 'B', 'C', 'Y', @statusCode);
-- expected: no error code


/*
    Test if three bookings in consecutive time duration can be made,
    and all the provided date, times, and ids are correct

    (test for unexpected behaviors happened with previous implementation)
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('X', @statusCode);
CALL createPlayer('Y', @statusCode);
CALL createPlayer('Z', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'Z', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '12:00:00', '13:30:00', 'A', 'B', 'C', 'Y', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '10:30:00', '12:00:00', 'A', 'B', 'C', 'Z', @statusCode);
-- expected: no error code


/*
    Test if the booking is not created when the time is overlapped with another booking,
    and all the provided date, times, and ids are correct

    (1st case)
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('X', @statusCode);
CALL createPlayer('Y', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'X', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '10:00:00', '11:30:00', 'A', 'B', 'C', 'Y', @statusCode);
-- expected: error code 413


/*
    Test if the booking is not created when the time is overlapped with another booking,
    and all the provided date, times, and ids are correct

    (2nd case)
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createPlayer('X', @statusCode);
CALL createPlayer('Y', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'X', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '08:00:00', '09:30:00', 'A', 'B', 'C', 'Y', @statusCode);
-- expected: error code 413


/*
    Test if the booking is not created when the time is overlapped with another booking,
    and all the provided date, times, and ids are correct

    (3nd case)
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createPlayer('X', @statusCode);
CALL createPlayer('Y', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'X', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '10:00:00', 'A', 'B', 'C', 'Y', @statusCode);
-- expected: error code 413


/*
    Test if the booking is not created when the time is overlapped with another booking,
    and all the provided date, times, and ids are correct

    (4th case)
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B', 'A', @statusCode);
CALL createCityCenterCourt('C', 'A', 'B', @statusCode);
CALL createPlayer('X', @statusCode);
CALL createPlayer('Y', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'X', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:30:00', '10:30:00', 'A', 'B', 'C', 'Y', @statusCode);
-- expected: error code 413


/*
    Test if the booking is not created when the time is overlapped with another booking,
    and all the provided date, times, and ids are correct

    (5th case)
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('X', @statusCode);
CALL createPlayer('Y', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'X', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'Y', @statusCode);
-- expected: error code 413


/*
    Test if the booking is not created when the provided city id does not exist in the database
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '10:30:00', '0', 'B', 'C', 'P', @statusCode);
-- expected: error code 460


/*
    Test if the booking is not created when the provided sport center id does not exist in the database
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '10:30:00', 'A', '0', 'C', 'P', @statusCode);
-- expected: error code 461


/*
    Test if the booking is not created when the provided court id does not exist in the database
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '10:30:00', 'A', 'B', '0', 'P', @statusCode);
-- expected: error code 462


/*
    Test if the booking is not created when the provided player id does not exist in the database
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', '0', @statusCode);
-- expected: error code 464


/*
    Test if the booking is not created when a date in the past is provided
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL -7 DAY), '09:00:00', '10:30:00', 'A', 'B', 'C', 'P', @statusCode);
-- expected: error code 466


/*
    Test if the booking is not created when the provided time duration is not
	45 minutes, 1 hour, 1 hour and 15 minutes, and 1 hour and 30 minutes

    (1st case: 30 minutes)
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '09:30:00', 'A', 'B', 'C', 'P', @statusCode);
-- expected: error code 467


/*
    Test if the booking is not created when the provided time duration is not
	45 minutes, 1 hour, 1 hour and 15 minutes, and 1 hour and 30 minutes

    (2nd case: 2 hours)
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '11:00:00', 'A', 'B', 'C', 'P', @statusCode);
-- expected: error code 467


/*
	Test if the booking is not created when the provided start time is not a factor of 15 minutes
*/

CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:10:00', '10:00:00', 'A', 'B', 'C', 'P', @statusCode);
-- expected: error code 468


/*
	Test if the booking is not created when the provided start time is not in working hours ('07:00:00' to '21:00:00')
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '06:00:00', '07:30:00', 'A', 'B', 'C', 'P', @statusCode);
-- expected: error code 468


/*
Test if the booking is not created when the provided end time is not a factor of 15 minutes
*/

CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '09:00:00', '10:10:00', 'A', 'B', 'C', 'P', @statusCode);
-- expected: error code 469


/*
	Test if the booking is not created when the provided end time is not in working hours ('07:00:00' to '21:00:00')
*/
CALL createCity('A', @statusCode);
CALL createCityCenter('B',  'A', @statusCode);
CALL createCityCenterCourt('C',  'A', 'B', @statusCode);
CALL createPlayer('P', @statusCode);
CALL createBooking(NOW(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '20:00:00', '21:30:00', 'A', 'B', 'C', 'P', @statusCode);
-- expected: error code 469