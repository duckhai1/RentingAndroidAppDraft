Test cases for createBooking stored procedure
===

testCreateMultipleBookingsUptoTheLimit
---
+ _Objective__: Test if it is possible to create bookings upto the limit number of booking
+ _Preconditions_:
	+ `inTimeStamp` is the time instance when the stored procedure is called
	+ `inBookingDate` comes after the date when the interface is called
	+ Duration between `bookingStartTime` and `bookingStartTime` is 45 minutes, 1 hour, 1 hour 15 minutes, or 1 hour 30 minutes
	+ `inCityId` uniquely identifies a row in `city` relation
	+ `inSportCenterId` uniquely identifies a row in `sportCenter` relation
	+ `inCourtId` uniquely identifies a row in `court` relation
	+ `inPlayerId` uniquely identifies a row in `player` relation
	+ The `booking` relation is empty
+ _Steps__: Call the stored procedures 3 times with valid data to create 3 bookings in 3 different date
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS*

	test if the request is accepted and a booking is created when `caller` is valid and all the parameters are valid
    + **`testMakeBookingInvalidBooking`**: test if the request is rejected when the `caller` is valid and the booking is invalid, i.e.,
        + _Preconditions_:
            + `playerId` uniquely identifies a row in `Player` relation
            + `courtId` uniquely identifies a row in `Court` relation
            + `date` is in `DATE` format and `date` comes after the date when the interface is called
            + `dateCreated` is in `DATE` format and is equal to the date when the interface is called
            + `startTime` and `endTime` is in `TIMESTAMP` format, `startTime` comes before `endTime`, and `startTime` comes after the time when the interface is called
            + The player does not have unpaid booking
            + The player does not have three upcomming bookings
            + There exists another booking with the same `courtId` and the same `date`, but its `startTime` and `endTime` overlapping with the new booking
        + _Pass conditions_:
            + ```errorCode``` is equal to *413 - OVERLAPPED BOOKING*
    + **`testMakeBookingPendingPayment`**: test if the request is rejected when the `caller` is valid and the player has past unpaid payment
        + _Preconditions_:
            + `playerId` uniquely identifies a row in `Player` relation
            + `courtId` uniquely identifies a row in `Court` relation
            + `date` is in `DATE` format and `date` comes after the date when the interface is called
            + `dateCreated` is in `DATE` format and is equal to the date when the interface is called
            + `startTime` and `endTime` is in `TIMESTAMP` format, `startTime` comes before `endTime`, and `startTime` comes after the time when the interface is called
            + The player has unpaid booking
            + The player does not have three upcomming bookings
            + There does not exist another booking with the same `courtId` and the same `date`, but its `startTime` and `endTime` overlapping with the new booking
        + _Pass conditions_:
            + ```errorCode``` is equal to *412 - UNPAID BOOKING FOUND*
    + **`testMakeBookingLimitExceed`**: test if the server behaves as expected when the player has already had 3 future bookings
        + _Preconditions_:
            + `playerId` uniquely identifies a row in `Player` relation
            + `courtId` uniquely identifies a row in `Court` relation
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND**  `date` comes after the date when the interface is called
            + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by ":" *(colon)*,, and ordered as given **AND** `startTime` comes before `endTime` **AND** `startTime` comes after the time when the interface is called
            + The player does not have unpaid booking
            + The player already has three upcomming bookings
            + There does not exist another booking with the same `courtId` and the same `date`, but its `startTime` and `endTime` overlapping with the new booking
        + _Pass conditions_:
            + ```errorCode``` is equal to *410 - BOOKINGS LIMIT REACHED*
    + **`testMakeBookingUnauthorized`**:  test if the request is rejected when `playerId` is invalid
         + _Preconditions_:
            + `playerId` does not identify a row in `Player` relation
        + _Pass conditions_:
            + ```errorCode``` is equal to *401 - UNAUTHORIZED*
    + **`testMakeBookingInvalidCourtId`**:  test if the request is rejected `courtId` is invalid
        + _Preconditions_:
            + `courtId` does not identify a row in `Court` relation
        + _Pass conditions_:
            + ```errorCode``` is equal to *462 - INVALID COURT ID*
    + **`testMakeBookingInvalidDate`**: test if the request is rejected when `date` is invalid
        + _Preconditions_:
            + `date` is not in `DATE` format
        + _Pass conditions_:
            + ```errorCode``` is equal to *466 - INVALID DATE*
    + **`testMakeBookingInvalidDuration`**: test if the request is rejected when the duration between `startTime` and `endTime` is invalid
        + _Preconditions_:
            + The period between `startTime` and `endTime` is not *45 minutes*, *1 hour*, *1 hour and 15 minutes*, or *1 hour and 30 minutes*
        + _Pass conditions_:
            + ```errorCode``` is equal to *467 - INVALID DURATION*
    + **`testMakeBookingInvalidStartTime`**: test if the request is rejected when `startTime` is invalid
        + _Preconditions_:
            + `startTime` is not in `TIMESTAMP` format **OR** `startTime` does not come before `endTime` **OR** `startTime` does not come after the time when the interface is called
        + _Pass conditions_:
            + ```errorCode``` is equal to *468 - INVALID START TIME*
    + **`testMakeBookingInvalidEndTime`**: test if the request is rejected when `endTime` is invalid
        + _Preconditions_:
            + `startTime` is not in `TIMESTAMP` format
        + _Pass conditions_:
            + ```errorCode``` is equal to *469 - INVALID END TIME*
