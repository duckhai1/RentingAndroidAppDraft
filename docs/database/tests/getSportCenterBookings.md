Test cases for getSportCenterBookings stored procedure
===

testGetBookingsSucessfullyCase01
---
+ _Objective__: Test the stored procedure sets the correct status code and returns the correct relation
+ _Preconditions_:
	+ The `sportCenter` relation contains: `SportCenter("Q1", "HCM")` and `SportCenter("Q2", "HCM")`
	+ There are 2 bookings in the same date made with `"Q1"` and 1 booking made with `"Q2"` in the `booking` relation
	+ `inCityId` is set to `"HCM"`
	+ `inSportCenterId` is set to `"Q1"`
	+ `inBookingDate` is set to the date of the bookings for `"Q1"`
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS*
	+ The returned relation has the schema `R(bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, playerId)`
	+ The returned relation contains 2 rows

testGetBookingsSucessfullyCase01
---
+ _Objective__: Test the stored procedure sets the correct status code and returns the correct relation
+ _Preconditions_:
	+ The `sportCenter` relation contains: `SportCenter("Q1", "HCM")` and `SportCenter("Q2", "HCM")`
	+ There are 2 bookings in the same date made with `"Q1"` and 1 booking made with `"Q2"` in the `booking` relation
	+ `inCityId` is set to `"HCM"`
	+ `inSportCenterId` is set to `"Q2"`
	+ `inBookingDate` is set to the date of the bookings for `"Q2"`
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS*
	+ The returned relation has the schema `R(bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, playerId)`
	+ The returned relation contains 1 row

testGetBookingsInvalidSportCenterId
---
+ _Objective__: Test the request is rejected when the given sport center id is invalid
+ _Preconditions_:
	+ `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation
	+ `inSportCenterId` does not identify a row in `sportCenter` relation
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *461 - INVALID SPORT CENTER ID*

testGetBookingsInvalidCityId
---
+ _Objective__: Test the request is rejected when the given city id is invalid
+ _Preconditions_:
	+ `inCityId` does not identify a row in `city` relation
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *460 - INVALID COURT ID*

testGetBookingsNoBookingsInGivenDate
---
+ _Objective__: Test the stored procedure sets the correct status code and returns the correct relation
+ _Preconditions_:
	+ The `sportCenter` relation contains: `SportCenter("Q1", "HCM")` and `SportCenter("Q2", "HCM")`
	+ There are 2 bookings in the same date made with `"Q1"` and 1 booking made with `"Q2"` in the `booking` relation
	+ `inCityId` is set to `"HCM"`
	+ `inSportCenterId` is set to `"Q2"`
	+ `inBookingDate` is not set to the date of the bookings for `"Q2"`
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS*
