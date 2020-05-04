Test case for updateSportCenterId stored procedure
===

testUpdateSportCenterIdSuccessfully
---
+ _Objective_: test if `newSportCenterId` is updated successful, when all the given parameters are valid.
+ _Precondition_:
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
    + `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
	+ `newSportCenterId` is an alphanumeric characters sequence.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *200 - SUCCESS*


testUpdateSportCenterIdExists
---
+ _Objective_: test if the request is rejected when `newSportCenterId` already exists.
+ _Precondition_:
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
    + `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
    + `newSportCenterId` is an alphanumeric characters sequence.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *403 - SPORTCENTER ID ALREADY EXISTS*


testUpdateSportCenterInvalidNewId
---
+ _Objective_: test if the request is rejected when `newSportCenterId` is invalid.
+ _Precondition_:
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
    + `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
    + `newSportCenterId` is not an alphanumeric characters sequence.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *461 - INVALID SPORTCENTER ID*


testUpdateSportCenterIdIdInvalidInputId
---
+ _Objective_: test if the request is rejected when `inSportCenterId` is invalid.
+ _Precondition_:
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
    + `inSportCenterId` does not exist in the database.
    + `newSportCenterId` is an alphanumeric characters sequence.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *461 - INVALID SPORTCENTER ID*


testUpdateSportCenterIdIdInvalidCityId
---
+ _Objective_: test if the request is rejected when `inCityId` is invalid.
+ _Precondition_:
    + `inCityId` does not exist in the database.
    + `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
    + `newSportCenterId` is an alphanumeric characters sequence.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *461 - INVALID SPORTCENTER ID*