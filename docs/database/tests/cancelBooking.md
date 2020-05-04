Test cases for createBooking stored procedure
===

testCancelOneBookingSuccessfully
---
+ _Objective_: Test if a booking can be cancel successfully provided the correct `bookingId` and `playerId`
+ _Preconditions_:
	+ `bookingId` is an alphanumeric characters sequence and uniquely identifies a row in `booking` relation
	+ `playerId` is an alphanumeric characters sequence and uniquely identifies a row in `player` relation
	+ Booking with `bookingId` belongs to the player with `playerId`
	+ The request is made in more than 24 hours before the start time of the booking
+ _Steps_: Call the `createBooking` once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS*


testCancelBookingInLessThen24HoursEarlier
---
+ _Objective_: test is the request is rejected when the request is made in less than 24 hours before `startTime` of the booking
+ _Preconditions_:
	+ `bookingId` is an alphanumeric characters sequence and uniquely identifies a row in `Booking` relation
	+ `playerId` is an alphanumeric characters sequence and uniquely identifies a row in `Player` relation
	+ Booking with `bookingId` belongs to the player with `playerId`
	+ The request is made in less than 24 hours before the start time of the booking
+ _Steps_: Call the `createBooking` once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *411 - BOOKING CANCELLATION REJECTED*

testCancelBookingWithInvalidBookingId
---
+ _Objective_: Test if the request is rejected when `bookingId` is invalid
+ _Preconditions_:
	+ `bookingId` does not identify a row in `Booking` relation
+ _Steps_: Call the `createBooking` once with data that sastifies the preconditions
+ _Pass conditions_:
	+ ``statusCode`` is equal to *465 - INVALID BOOKING ID*

testCancelBookingWithInvalidPlayerId
---
+ _Objective_: Test if the request is rejected when `playerId` is invalid
+ _Preconditions_:
	+ `bookingId` is an alphanumeric characters sequence and uniquely identifies a row in `Booking` relation
	+ `playerId` does not identify a row in `Player` relation
	+ The request is made in more than 24 hours before the start time of the booking
+ _Steps_: Call the `createBooking` once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *464 - INVALID BOOKING ID*

testCancelBookingUnauthorized
---
+ _Objective_: Test if the request is rejected when booking with `bookingId` does not belong to the player with `playerId`
+ _Preconditions_:
	+ `bookingId` is an alphanumeric characters sequence and uniquely identifies a row in `Booking` relation
	+ `playerId` is an alphanumeric characters sequence and uniquely identifies a row in `Player` relation
	+ Booking with `bookingId` does not belong to the player with `playerId`
	+ The request is made in more than 24 hours before the start time of the booking
+ _Steps_: Call the `createBooking` once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *401 - UNAUTHORIZED*
