Test cases for createCityCenterCourt stored procedure
===

testCreateCourtSuccess
---
+ _Objective_:  test if the court is created provided the correct `inCityId` and `inSportCenterId`.
+ _Precondition_:
    + `inCourtId` is an alphanumeric characters sequence
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
	+ `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_: 
    + `statusCode` is equal to *200 - SUCCESS*


createCityCenterCourtWithDuplicate
---
+ _Objective_: test if the request is rejected when `inCourtId` is duplicated.
+ _Precondition_:
    + `inCourtId` is an alphanumeric characters sequence and `inCourtId` already exists in database. 
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
	+ `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` is equal to *404 - COURT ID ALREADY EXISTS*


createCityCenterCourtWithInvalidCourtId
---
+ _Objective_: test if the request is rejected when `inCourtId` is invalid.
+ _Precondition_: 
    + `inCourtId` is not an alphanumeric characters sequence.
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
	+ `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_: 
    + `statusCode` is equal to *462 - INVALID COURT ID*


createCityCenterCourtWithInvalidinCityId
---
+ _Objective_: test if the request is rejected when `inCityId` is invalid.
+ _Precondition_:
    + `inCourtId` is an alphanumeric characters sequence.
    + `inCityId` does not exist in the database.
	+ `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` is equal to *460 - INVALID CITY ID*


createCityCenterCourtWithInvalidCenterId
---
+ _Objective_: test if the request is rejected when `inSportCenterId` is invalid.
+ _Precondition_: 
    + `inCourtId` is an alphanumeric characters sequence.
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
	+ `inSportCenterId` does not exit in the database.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_: 
    + `statusCode` is equal to *461 - INVALID SPORTCENTER ID*
