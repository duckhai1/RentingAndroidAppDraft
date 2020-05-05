Test cases for updateCourtId stored procedure
===

testUpdateCourtIdSuccess
---
+ _Objective_: test if `inCourtId` is updated successful, when all the given parameters are valid.
+ _Precondition_:
    + `newCourtId` is an alphanumeric characters sequence.
    + `inCourtId` is an alphanumeric characters sequence and uniquely identifies a row in `court` relation.
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.	
    + `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` is equal to *200 - SUCCESS*


testGetCourtInfoInvalidCourtId
---
+ _Objective_: test if the request is rejected when `inCourtId` is invalid.
+ _Precondition_:
    + `inCourtId` is not an alphanumeric characters sequence and uniquely identifies a row in `court` relation.
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
	+ `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` is equal to *462 - INVALID COURT ID*


testGetCourtInfoInvalidCityId
---
+ _Objective_: test if the request is rejected when `inCityId` is invalid.
+ _Precondition_:
    + `inCityId` is not an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
    + `inCourtId` is an alphanumeric characters sequence and uniquely identifies a row in `court` relation.
	+ `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` is equal to *460 - INVALID CITY ID*.


testGetCourtInfoInvalidSportCenterId
---
+ _Objective_: test if the request is rejected when `inSportCenterId` is invalid.
+ _Precondition_:
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
    + `inCourtId` is an alphanumeric characters sequence and uniquely identifies a row in `court` relation.
	+ `inSportCenterId` is not an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` is equal to *461 - INVALID SPORTCENTER ID*.


testUpdateCourtInvalidNewCourtId
---
+ _Objective_: test if the request is rejected when `newCourtId` is invalid.
+ _Precondition_:
    + `newCourtId` is an not alphanumeric characters sequence 
    + `inCourtId` is an alphanumeric characters sequence and uniquely identifies a row in `court` relation.
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
	+ `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` is equal to *462 - INVALID COURT ID*.


testUpdateCourtInvalidCitySportCenterId
---
+ _Objective_:  test if the request is rejected when `inSportCenterId` is invalid.
+ _Precondition_:
    + `newCourtId` is an not alphanumeric characters sequence 
    + `inCourtId` is an alphanumeric characters sequence and uniquely identifies a row in `court` relation.
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
    + `inSportCenterId` does not identify a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` is equal to *461 - INVALID SPORTCENTER ID*.
