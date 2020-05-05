Test cases for getCourtBookings stored procedure
===

testGetBookingsSucessfullyCase01
---
+ _Objective__: Test the stored procedure sets the correct status code and returns the correct relation
+ _Preconditions_:
	+ The `court` relation contains: `Court("Court1", "HCM", "Q1")` and `Court("Court2", "HCM", "Q1")`
	+ There are 2 bookings in the same date made with `"Court1"` and 1 booking made with `"Court2"` in the `booking` relation
	+ `inCityId` is set to `"HCM"`
	+ `inSportCenterId` is set to `"Q1"`
	+ `inCourtId` is set to `"Court1"`
	+ `inBookingDate` is set to the date of the bookings for `"Court1"`
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS*
	+ The returned relation has the schema `R(bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, playerId)`
	+ The returned relation contains 2 rows

testGetBookingsSucessfullyCase02
---
+ _Objective__: Test the stored procedure sets the correct status code and returns the correct relation
+ _Preconditions_:
	+ The `court` relation contains: `Court("Court1", "HCM", "Q1")` and `Court("Court2", "HCM", "Q1")`
	+ There are 2 bookings made with `"Court1"` and 1 booking made with `"Court2"` in the `booking` relation
	+ `inCityId` is set to `"HCM"`
	+ `inSportCenterId` is set to `"Q1"`
	+ `inCourtId` is set to `"Court2"`
	+ `inBookingDate` is set to the date of the bookings for `"Court2"`
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS*
	+ The returned relation has the schema `R(bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, playerId)`
	+ The returned relation contains 1 row

testGetBookingsInvalidCourtId
---
+ _Objective__: Test the request is rejected when the given court id is invalid
+ _Preconditions_:
	+ `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation
	+ `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation
	+ `inCourtId` does not identify a row in `court` relation
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *462 - INVALID COURT ID*

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
+ _Objective__: Test the stored procedure sets the correct status code
+ _Preconditions_:
	+ The `court` relation contains: `Court("Court1", "HCM", "Q1")` and `Court("Court2", "HCM", "Q1")`
	+ There are 2 bookings made with `"Court1"` and 1 booking made with `"Court2"` in the `booking` relation
	+ `inCityId` is set to `"HCM"`
	+ `inSportCenterId` is set to `"Q1"`
	+ `inCourtId` is set to `"Court2"`
	+ `inBookingDate` is not set to the date of the bookings for `"Court2"`
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS*
