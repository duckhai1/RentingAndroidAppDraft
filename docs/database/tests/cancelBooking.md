Test cases for createBooking stored procedure
===

testCancelOneBookingSuccessfully
---
+ _Objective_: Test if a booking can be cancel successfully provided the correct `inBookingId` and `inPlayerId`
+ _Preconditions_:
	+ `inBookingId` is an alphanumeric characters sequence and uniquely identifies a row in `booking` relation
	+ `inPlayerId` is an alphanumeric characters sequence and uniquely identifies a row in `player` relation
	+ Booking with `inBookingId` belongs to the player with `inPlayerId`
	+ The request is made in more than 24 hours before the start time of the booking
+ _Steps_: Call the `createBooking` once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS*


testCancelBookingInLessThen24HoursEarlier
---
+ _Objective_: test is the request is rejected when the request is made in less than 24 hours before `startTime` of the booking
+ _Preconditions_:
	+ `inBookingId` is an alphanumeric characters sequence and uniquely identifies a row in `Booking` relation
	+ `inPlayerId` is an alphanumeric characters sequence and uniquely identifies a row in `Player` relation
	+ Booking with `inBookingId` belongs to the player with `inPlayerId`
	+ The request is made in less than 24 hours before the start time of the booking
+ _Steps_: Call the `createBooking` once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *411 - BOOKING CANCELLATION REJECTED*

testCancelBookingWithInvalidBookingId
---
+ _Objective_: Test if the request is rejected when `inBookingId` is invalid
+ _Preconditions_:
	+ `inBookingId` does not identify a row in `Booking` relation
+ _Steps_: Call the `createBooking` once with data that sastifies the preconditions
+ _Pass conditions_:
	+ ``statusCode`` is equal to *465 - INVALID BOOKING ID*

testCancelBookingWithInvalidPlayerId
---
+ _Objective_: Test if the request is rejected when `inPlayerId` is invalid
+ _Preconditions_:
	+ `inBookingId` is an alphanumeric characters sequence and uniquely identifies a row in `Booking` relation
	+ `inPlayerId` does not identify a row in `Player` relation
	+ The request is made in more than 24 hours before the start time of the booking
+ _Steps_: Call the `createBooking` once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *464 - INVALID BOOKING ID*

testCancelBookingUnauthorized
---
+ _Objective_: Test if the request is rejected when booking with `inBookingId` does not belong to the player with `inPlayerId`
+ _Preconditions_:
	+ `inBookingId` is an alphanumeric characters sequence and uniquely identifies a row in `Booking` relation
	+ `inPlayerId` is an alphanumeric characters sequence and uniquely identifies a row in `Player` relation
	+ Booking with `inBookingId` does not belong to the player with `inPlayerId`
	+ The request is made in more than 24 hours before the start time of the booking
+ _Steps_: Call the `createBooking` once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *401 - UNAUTHORIZED*
