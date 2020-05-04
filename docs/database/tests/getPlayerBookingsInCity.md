Test cases for getPlayerBookingsInCity stored procedure
===

testGetPlayerBookingsInCitySucessfullyCase01
---
+ _Objective__: Test the stored procedure sets the correct status code and returns the correct relation
+ _Preconditions_:
	+ Player with id `"Alice"` has 2 bookings in city with id `"HCM"`
	+ Player with id `"Alice"` has 1 booking in city with id `"HaNoi"`
	+ `inCityId` is set to `"HCM"`
	+ `inPlayerId` is set to `"Alice"`
	+ `inBookingDate` is set to the date of the bookings in `"HCM"`
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS*
	+ The returned relation has the schema `R(bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, playerId)`
	+ The returned relation contains 2 rows

testGePlayertBookingsInCitySucessfullyCase02
---
+ _Objective__: Test the stored procedure sets the correct status code and returns the correct relation
+ _Preconditions_:
	+ Player with id `"Alice"` has 2 bookings in the same date in city with id `"HCM"`
	+ Player with id `"Alice"` has 1 booking in city with id `"HaNoi"`
	+ `inCityId` is set to `"HaNoi"`
	+ `inPlayerId` is set to `"Alice"`
	+ `inBookingDate` is set to the date of the bookings in `"HaNoi"`
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS*
	+ The returned relation has the schema `R(bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, playerId)`
	+ The returned relation contains 1 row

testGetPlayerBookingsInCityNoBookingInGivenDate
---
+ _Objective__: Test the stored procedure sets the correct status code
+ _Preconditions_:
	+ Player with id `"Alice"` has 2 bookings in the same date in city with id `"HCM"`
	+ `inCityId` is set to `"HCM"`
	+ `inPlayerId` is set to `"Alice"`
	+ `inBookingDate` is not set to the date of the bookings in `"HCM"`
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS*

testGetPlayerBookingsInCityInvalidCityId
---
+ _Objective__: Test if the request is rejected when provided an invalid city id
+ _Preconditions_:
	+ `inCityId` does not uniquely identify a row in `city` relation
	+ `inPlayerId` is an alphanumeric characters sequence and uniquely identifies a row in `player` relation
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *460 - INVALID CITY ID*

testGetPlayerBookingsInCityInvalidPlayerId
---
+ _Objective__: Test if the request is rejected when provided an invalid player id
+ _Preconditions_:
	+ `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation
	+ `inPlayerId` does not uniquely identify a row in `player` relation
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *464 - INVALID PLAYER ID*
