Test cases for createBooking stored procedure
===

testCancelOneBookingSuccessfully
---
+ _Purpose_: Test if a booking can be cancel successfully when all provided the correct bookingId and playerId
+ _Preconditions_:
	+ `bookingId` uniquely identifies a row in `Booking` relation
	+ `playerId` uniquely identifies a row in `Player` relation
	+ Booking with `bookingId` belongs to the player with `playerId`
	+ The request is made in more than 24 hours before the start time of the booking
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS*


testCancelBookingInLessThen24HoursEarlier
---
+ _Purpose_: test is the request is rejected when the request is made in less than 24 hours before `startTime` of the booking
+ _Preconditions_:
	+ `bookingId` uniquely identifies a row in `Booking` relation
	+ `playerId` uniquely identifies a row in `Player` relation
	+ Booking with `bookingId` belongs to the player with `playerId`
	+ The request is made in less than 24 hours before the start time of the booking
+ _Pass conditions_:
	+ `statusCode` is equal to *411 - BOOKING CANCELLATION REJECTED*

testCancelBookingWithInvalidBookingId
---
+ _Purpose_: Test if the request is rejected when `bookingId` is invalid
+ _Preconditions_:
	+ `bookingId` does not identify a row in `Booking` relation
+ _Pass conditions_:
	+ ``statusCode`` is equal to *465 - INVALID BOOKING ID*

testCancelBookingWithInvalidPlayerId
---
+ _Purpose_: Test if the request is rejected when `playerId` is invalid
+ _Preconditions_:
	+ `bookingId` uniquely identifies a row in `Booking` relation
	+ `playerId` does not identify a row in `Player` relation
	+ The request is made in more than 24 hours before the start time of the booking
+ _Pass conditions_:
	+ `statusCode` is equal to *464 - INVALID BOOKING ID*

testCancelBookingUnauthorized
---
+ _Purpose_: Test if the request is rejected when booking with `bookingId` does not belong to the player with `playerId`
+ _Preconditions_:
	+ `bookingId` uniquely identifies a row in `Booking` relation
	+ `playerId` uniquely identifies a row in `Player` relation
	+ Booking with `bookingId` does not belong to the player with `playerId`
	+ The request is made in more than 24 hours before the start time of the booking
+ _Pass conditions_:
	+ `statusCode` is equal to *401 - UNAUTHORIZED*
