Test cases for createBooking stored procedure
===

testCreateMultipleBookingsUptoTheLimit
---
+ _Objective__: Test if it is possible to create bookings upto the limit number of booking
+ _Preconditions_:
	+ `inTimeStamp` is the time instance when the stored procedure is called
	+ `inBookingDate` comes after the date when the interface is called
	+ `inBookingStartTime` is a factor of 15 minutes and comes after `"07:00:00"`
	+ `inBookingEndTime` is a factor of 15 minutes and comes before `"21:00:00"`
	+ Duration between `bookingStartTime` and `bookingStartTime` is 45 minutes, 1 hour, 1 hour 15 minutes, or 1 hour 30 minutes
	+ `inCityId` uniquely identifies a row in `city` relation
	+ `inSportCenterId` uniquely identifies a row in `sportCenter` relation
	+ `inCourtId` uniquely identifies a row in `court` relation
	+ `inPlayerId` uniquely identifies a row in `player` relation
	+ The `booking` relation is empty
+ _Steps_: Call the stored procedures 3 times with data that sastifies the preconditions and each call is made with a different `bookingDate`
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS* for every call

testCreateDuplicateBooking
---
+ _Objective__: Test if a request is rejected when it contains identical data to an existing booking
+ _Preconditions_:
	+ There exist a single booking in the database with `bookingDate` comes after the current date when the stored procedure is called
	+ `inTimeStamp` is the time instance when the stored procedure is called
	+ `inBookingDate` equals to the `bookingDate` of the existing booking
	+ `inBookingStartTime` equals to the `bookingStartTime` of the existing booking
	+ `inBookingEndTime` equals to the `bookingEndTime` of the existing booking
	+ `inCityId` equals to the `cityId` of the existing booking
	+ `inSportCenterId` equals to the `sportCenterId` of the existing booking
	+ `inCourtId` equals to the `courtId` of the existing booking
	+ `inPlayerId` equals to the `playerId` of the existing booking
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *407 - BOOKING ALREADY EXISTS*

testCreateBookingWhenBookingsLimitReached
---
+ _Objective__: Test if a request is rejected
+ _Preconditions_:
	+ The player with `playerId` equals to `inPlayerId` has 3 upcomming bookings
	+ `inTimeStamp` is the time instance when the stored procedure is called
	+ `inBookingDate` comes after the date when the interface is called
	+ `inBookingStartTime` is a factor of 15 minutes and comes after `"07:00:00"`
	+ `inBookingEndTime` is a factor of 15 minutes and comes before `"21:00:00"`
	+ Duration between `bookingStartTime` and `bookingStartTime` is 45 minutes, 1 hour, 1 hour 15 minutes, or 1 hour 30 minutes
	+ `inCityId` uniquely identifies a row in `city` relation
	+ `inSportCenterId` uniquely identifies a row in `sportCenter` relation
	+ `inCourtId` uniquely identifies a row in `court` relation
	+ `inPlayerId` uniquely identifies a row in `player` relation
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *410 - BOOKINGS LIMIT REACHED*

testCreateBookingOverlappingTimeCase01
---
+ _Objective__: Test if a request is rejected when the booking overalaped with another existing booking
+ _Preconditions_:
	+ The `booking` relation contains a booking with `bookingStartTime = "09:00:00"` and `bookingEndTime = "10:30:00"`
	+ `inTimeStamp` is the time instance when the stored procedure is called
	+ `inBookingDate` comes after the date when the interface is called
	+ `inBookingStartTime` is set to `"10:00:00"`
	+ `inBookingEndTime` is set to `"11:30:00"`
	+ `inCityId` equals to the `cityId` of the existing booking
	+ `inSportCenterId` equals to the `sportCenterId` of the existing booking
	+ `inCourtId` equals to the `courtId` of the existing booking
	+ `inPlayerId` uniquely identifies a row in `player` relation
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *413 - OVERLAPPED BOOKING FOUND*

testCreateBookingOverlappingTimeCase02
---
+ _Objective__: Test if a request is rejected when the booking overalaped with another existing booking
+ _Preconditions_:
	+ The `booking` relation contains a booking with `bookingStartTime = "09:00:00"` and `bookingEndTime = "10:30:00"`
	+ `inTimeStamp` is the time instance when the stored procedure is called
	+ `inBookingDate` comes after the date when the interface is called
	+ `inBookingStartTime` is set to `"08:30:00"`
	+ `inBookingEndTime` is set to `"10:00:00"`
	+ `inCityId` equals to the `cityId` of the existing booking
	+ `inSportCenterId` equals to the `sportCenterId` of the existing booking
	+ `inCourtId` equals to the `courtId` of the existing booking
	+ `inPlayerId` uniquely identifies a row in `player` relation
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *413 - OVERLAPPED BOOKING FOUND*

testCreateBookingOverlappingTimeCase03
---
+ _Objective__: Test if a request is rejected when the booking overalaped with another existing booking
+ _Preconditions_:
	+ The `booking` relation contains a booking with `bookingStartTime = "09:00:00"` and `bookingEndTime = "10:30:00"`
	+ `inTimeStamp` is the time instance when the stored procedure is called
	+ `inBookingDate` comes after the date when the interface is called
	+ `inBookingStartTime` is set to `"09:00:00"`
	+ `inBookingEndTime` is set to `"10:00:00"`
	+ `inCityId` equals to the `cityId` of the existing booking
	+ `inSportCenterId` equals to the `sportCenterId` of the existing booking
	+ `inCourtId` equals to the `courtId` of the existing booking
	+ `inPlayerId` uniquely identifies a row in `player` relation
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *413 - OVERLAPPED BOOKING FOUND*

testCreateBookingOverlappingTimeCase04
---
+ _Objective__: Test if a request is rejected when the booking overalaped with another existing booking
+ _Preconditions_:
	+ The `booking` relation contains a booking with `bookingStartTime = "09:00:00"` and `bookingEndTime = "10:30:00"`
	+ `inTimeStamp` is the time instance when the stored procedure is called
	+ `inBookingDate` comes after the date when the interface is called
	+ `inBookingStartTime` is set to `"09:30:00"`
	+ `inBookingEndTime` is set to `"10:30:00"`
	+ `inCityId` equals to the `cityId` of the existing booking
	+ `inSportCenterId` equals to the `sportCenterId` of the existing booking
	+ `inCourtId` equals to the `courtId` of the existing booking
	+ `inPlayerId` uniquely identifies a row in `player` relation
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *413 - OVERLAPPED BOOKING FOUND*

testCreateBookingWithInvalidCityId
---
+ _Objective__: Test if a request is rejected when an invalid city id is provided
+ _Preconditions_:
	+ `inTimeStamp` is the time instance when the stored procedure is called
	+ `inBookingDate` comes after the date when the interface is called
	+ `inBookingStartTime` is a factor of 15 minutes and comes after `"07:00:00"`
	+ `inBookingEndTime` is a factor of 15 minutes and comes before `"21:00:00"`
	+ Duration between `bookingStartTime` and `bookingStartTime` is 45 minutes, 1 hour, 1 hour 15 minutes, or 1 hour 30 minutes
	+ `inCityId` does not uniquely identify a row in `city` relation
	+ `inSportCenterId` uniquely identifies a row in `sportCenter` relation
	+ `inCourtId` uniquely identifies a row in `court` relation
	+ `inPlayerId` uniquely identifies a row in `player` relation
	+ The `booking` relation is empty
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *460 - INVALID CITY ID*

testCreateBookingWithInvalidSportCenterId
---
+ _Objective__: Test if a request is rejected when an invalid sport center id is provided
+ _Preconditions_:
	+ `inTimeStamp` is the time instance when the stored procedure is called
	+ `inBookingDate` comes after the date when the interface is called
	+ `inBookingStartTime` is a factor of 15 minutes and comes after `"07:00:00"`
	+ `inBookingEndTime` is a factor of 15 minutes and comes before `"21:00:00"`
	+ Duration between `bookingStartTime` and `bookingStartTime` is 45 minutes, 1 hour, 1 hour 15 minutes, or 1 hour 30 minutes
	+ `inCityId` uniquely identifies a row in `city` relation
	+ `inSportCenterId` does not uniquely identify a row in `sportCenter` relation
	+ `inCourtId` uniquely identifies a row in `court` relation
	+ `inPlayerId` uniquely identifies a row in `player` relation
	+ The `booking` relation is empty
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *461 - INVALID SPORT CENTER ID*

testCreateBookingWithInvalidCourtId
---
+ _Objective__: Test if a request is rejected when an invalid court id is provided
+ _Preconditions_:
	+ `inTimeStamp` is the time instance when the stored procedure is called
	+ `inBookingDate` comes after the date when the interface is called
	+ `inBookingStartTime` is a factor of 15 minutes and comes after `"07:00:00"`
	+ `inBookingEndTime` is a factor of 15 minutes and comes before `"21:00:00"`
	+ Duration between `bookingStartTime` and `bookingStartTime` is 45 minutes, 1 hour, 1 hour 15 minutes, or 1 hour 30 minutes
	+ `inCityId` uniquely identifies a row in `city` relation
	+ `inSportCenterId` uniquely identifies a row in `sportCenter` relation
	+ `inCourtId` does not uniquely identify a row in `court` relation
	+ `inPlayerId` uniquely identifies a row in `player` relation
	+ The `booking` relation is empty
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *462 - INVALID COURT ID*

testCreateBookingWithInvalidPlayerId
---
+ _Objective__: Test if a request is rejected when an invalid player id is provided
+ _Preconditions_:
	+ `inTimeStamp` is the time instance when the stored procedure is called
	+ `inBookingDate` comes after the date when the interface is called
	+ `inBookingStartTime` is a factor of 15 minutes and comes after `"07:00:00"`
	+ `inBookingEndTime` is a factor of 15 minutes and comes before `"21:00:00"`
	+ Duration between `bookingStartTime` and `bookingStartTime` is 45 minutes, 1 hour, 1 hour 15 minutes, or 1 hour 30 minutes
	+ `inCityId` uniquely identifies a row in `city` relation
	+ `inSportCenterId` uniquely identifies a row in `sportCenter` relation
	+ `inCourtId` uniquely identifies a row in `court` relation
	+ `inPlayerId` does not uniquely identify a row in `player` relation
	+ The `booking` relation is empty
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *464 - INVALID PLAYER ID*

testCreateBookingWithInvalidDate
---
+ _Objective__: Test if a request is rejected when an invalid date is provided
+ _Preconditions_:
	+ `inTimeStamp` is the time instance when the stored procedure is called
	+ `inBookingDate` comes before the date when the interface is called
	+ `inBookingStartTime` is a factor of 15 minutes and comes after `"07:00:00"`
	+ `inBookingEndTime` is a factor of 15 minutes and comes before `"21:00:00"`
	+ Duration between `bookingStartTime` and `bookingStartTime` is 45 minutes, 1 hour, 1 hour 15 minutes, or 1 hour 30 minutes
	+ `inCityId` uniquely identifies a row in `city` relation
	+ `inSportCenterId` uniquely identifies a row in `sportCenter` relation
	+ `inCourtId` uniquely identifies a row in `court` relation
	+ `inPlayerId` uniquely identifies a row in `player` relation
	+ The `booking` relation is empty
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *466 - INVALID DATE*

testCreateBookingWithInvalidDuration
---
+ _Objective__: Test if a request is rejected when an invalid duration is provided
+ _Preconditions_:
	+ `inTimeStamp` is the time instance when the stored procedure is called
	+ `inBookingDate` comes after the date when the interface is called
	+ `inBookingStartTime` is a factor of 15 minutes and comes after `"07:00:00"`
	+ `inBookingEndTime` is a factor of 15 minutes and comes before `"21:00:00"`
	+ Duration between `bookingStartTime` and `bookingStartTime` is not 45 minutes, 1 hour, 1 hour 15 minutes, or 1 hour 30 minutes
	+ `inCityId` uniquely identifies a row in `city` relation
	+ `inSportCenterId` uniquely identifies a row in `sportCenter` relation
	+ `inCourtId` uniquely identifies a row in `court` relation
	+ `inPlayerId` uniquely identifies a row in `player` relation
	+ The `booking` relation is empty
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *467 - INVALID DURATION*

testCreateBookingStartTimeNotFactorOf15Min
---
+ _Objective__: Test if a request is rejected when the provided start time is not a factor of 15 minutes
+ _Preconditions_:
	+ `inTimeStamp` is the time instance when the stored procedure is called
	+ `inBookingDate` comes after the date when the interface is called
	+ `bookingStartTime` is not a factor of 15 minutes
	+ `inBookingStartTime` is a factor of 15 minutes and comes after `"07:00:00"`
	+ `inBookingEndTime` is a factor of 15 minutes and comes before `"21:00:00"`
	+ `inSportCenterId` uniquely identifies a row in `sportCenter` relation
	+ `inCourtId` uniquely identifies a row in `court` relation
	+ `inPlayerId` uniquely identifies a row in `player` relation
	+ The `booking` relation is empty
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *468 - INVALID START TIME*

testCreateBookingEndTimeNotFactorOf15Min
---
+ _Objective__: Test if a request is rejected when the provided end time is not a factor of 15 minutes
+ _Preconditions_:
	+ `inTimeStamp` is the time instance when the stored procedure is called
	+ `inBookingDate` comes after the date when the interface is called
	+ `inBookingStartTime` is a factor of 15 minutes and comes after `"07:00:00"`
	+ `inBookingEndTime` is a factor of 15 minutes and comes before `"21:00:00"`
	+ `inCityId` uniquely identifies a row in `city` relation
	+ `inSportCenterId` uniquely identifies a row in `sportCenter` relation
	+ `inCourtId` uniquely identifies a row in `court` relation
	+ `inPlayerId` uniquely identifies a row in `player` relation
	+ The `booking` relation is empty
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *469 - INVALID END TIME*

testCreateBookingStartTimeNotInWorkingHours
---
+ _Objective__: Test if a request is rejected when the provided start time is not a factor of 15 minutes
+ _Preconditions_:
	+ `inTimeStamp` is the time instance when the stored procedure is called
	+ `inBookingDate` comes after the date when the interface is called
	+ `inBookingStartTime` is a factor of 15 minutes and comes before `"07:00:00"`
	+ `inBookingEndTime` is a factor of 15 minutes and comes before `"21:00:00"`
	+ `inCityId` uniquely identifies a row in `city` relation
	+ `inSportCenterId` uniquely identifies a row in `sportCenter` relation
	+ `inCourtId` uniquely identifies a row in `court` relation
	+ `inPlayerId` uniquely identifies a row in `player` relation
	+ The `booking` relation is empty
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *468 - INVALID START TIME*

testCreateBookingEndTimeNotInWorkingHours
---
+ _Objective__: Test if a request is rejected when the provided end time is not a factor of 15 minutes
+ _Preconditions_:
	+ `inTimeStamp` is the time instance when the stored procedure is called
	+ `inBookingDate` comes after the date when the interface is called
	+ `inBookingStartTime` is a factor of 15 minutes and comes after `"07:00:00"`
	+ `inBookingEndTime` is a factor of 15 minutes and comes after `"21:00:00"`
	+ `inCityId` uniquely identifies a row in `city` relation
	+ `inSportCenterId` uniquely identifies a row in `sportCenter` relation
	+ `inCourtId` uniquely identifies a row in `court` relation
	+ `inPlayerId` uniquely identifies a row in `player` relation
	+ The `booking` relation is empty
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *469 - INVALID END TIME*
