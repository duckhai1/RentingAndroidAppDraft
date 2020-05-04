Test cases for getPlayerBookings stored procedure
===

testGetPlayerBookingsSucessfullyCase01
---
+ _Objective__: Test the stored procedure sets the correct status code and returns the correct relation
+ _Preconditions_:
	+ Player with id `"Alice"` has 2 bookings
	+ Player with id `"Bob"` has 1 bookings
	+ `inPlayerId` is set to `"Alice"`
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS*
	+ The returned relation has the schema `R(bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, playerId)`
	+ The returned relation contains 2 rows

testGetPlayerBookingsSucessfullyCase01
---
+ _Objective__: Test the stored procedure sets the correct status code and returns the correct relation
+ _Preconditions_:
	+ Player with id `"Alice"` has 2 bookings
	+ Player with id `"Bob"` has 1 bookings
	+ `inPlayerId` is set to `"Bob"`
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS*
	+ The returned relation has the schema `R(bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, playerId)`
	+ The returned relation contains 2 rows

testGetPlayerBookingsInvalidPlayerId
---
+ _Objective__: Test if the request is rejected when provided an invalid player id
+ _Preconditions_:
	+ `inPlayerId` does not uniquely identify a row in the `player` relation
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *464 - INVALID PLAYER ID*
