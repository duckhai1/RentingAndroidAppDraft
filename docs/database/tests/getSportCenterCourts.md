Test cases for getSportCenterCourts stored procedure

===

testGetSportCenterCourtSuccess
---
+ _Objective_: test if a court list can be returned successfully provided the correct `inCityId` and `inSportCenterId`.
+ _Precondition_:
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
	+ `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` is equal to *200 - SUCCESS*


testCityCourtInvalidCityId
---
+ _Objective_: test if the request is rejected when `inCityId` is invalid.
+ _Precondition_:
    + `inCityId` does not exist in the database
	+ `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` is equal to *460 - INVALID CITY ID*


testCityCourtInvalidSportCenterId
---
+ _Objective_: test if the request is rejected when `inSportCenterId` is invalid.
+ _Precondition_:
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
	+ `inSportCenterId` does not exist in the database.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` is equal to *461 - INVALID SPORTCENTER ID*
