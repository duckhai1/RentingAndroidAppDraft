Test cases for updateBookingStatus stored procedure
===

testUpdateBookingStatusSuccessfully
---
+ _Objective__: Test the stored procedure sets the correct status code and returns the correct relation
+ _Preconditions_:
	+ `inBookingId` is an alphanumeric characters sequence and uniquely identifies a row in `booking` relation
	+ `inCityId` is an alphanumeric characters sequence and identifies a row in `city` relation
	+ `inSportCenterId` is an alphanumeric characters sequence and identifies a row in `sportCenterId` relation
	+ `inStaffId` is an alphanumeric characters sequence and identifies a row in `staff` relation
	+ The booking with `inBookingId` is made with the sport center with `sportCenterId`
	+ The staff with `inStaffId` works for sport center with `sportCenterId`
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS*

testUpdateBookingStatusInvalidBookingId
---
+ _Objective__: Test if the request is rejected when an invalid booking id is provided
+ _Preconditions_:
	+ `inBookingId` does not uniquely identify a row in `booking` relation
	+ `inCityId` is an alphanumeric characters sequence and identifies a row in `city` relation
	+ `inSportCenterId` is an alphanumeric characters sequence and identifies a row in `sportCenterId` relation
	+ `inStaffId` is an alphanumeric characters sequence and identifies a row in `staff` relation
	+ The staff with `inStaffId` works for sport center with `sportCenterId`
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *465 - INVALID BOOKING ID*

testUpdateBookingStatusInvalidCityId
---
+ _Objective__: Test if the request is rejected when an invalid city id is provided
+ _Preconditions_:
	+ `inBookingId` is an alphanumeric characters sequence and uniquely identifies a row in `booking` relation
	+ `inCityId` does not uniquely identify a row in `city` relation
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *460 - INVALID CITY ID*

testUpdateBookingStatusInvalidSportCenterId
---
+ _Objective__: Test if the request is rejected when an invalid sport center id is provided
+ _Preconditions_:
	+ `inBookingId` is an alphanumeric characters sequence and uniquely identifies a row in `booking` relation
	+ `inCityId` is an alphanumeric characters sequence and identifies a row in `city` relation
	+ `inSportCenterId` does not uniquely identify a row in `sportCenter` relation
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *461 - INVALID SPORT CENTER ID*

testUpdateBookingStatusInvalidStaffId
---
+ _Objective__: Test if the request is rejected when an invalid staff id is provided
+ _Preconditions_:
	+ `inBookingId` is an alphanumeric characters sequence and uniquely identifies a row in `booking` relation
	+ `inCityId` is an alphanumeric characters sequence and identifies a row in `city` relation
	+ `inSportCenterId` is an alphanumeric characters sequence and identifies a row in `sportCenterId` relation
	+ `inStaffId` does not uniquely identify a row in `staff` relation
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *463 - INVALID STAFF ID*

testUpdateBookingStatusUnauthorizedSportCenter
---
+ _Objective__: Test if the request is rejected when the booking and sport center does not match
+ _Preconditions_:
	+ `inBookingId` is an alphanumeric characters sequence and uniquely identifies a row in `booking` relation
	+ `inCityId` is an alphanumeric characters sequence and identifies a row in `city` relation
	+ `inSportCenterId` is an alphanumeric characters sequence and identifies a row in `sportCenterId` relation
	+ `inStaffId` is an alphanumeric characters sequence and identifies a row in `staff` relation
	+ The booking with `inBookingId` is not made with the sport center with `sportCenterId`
	+ The staff with `inStaffId` works for sport center with `sportCenterId`
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *401 - UNAUTHORIZED*
