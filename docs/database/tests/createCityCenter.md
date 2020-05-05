Test cases for createCityCenter stored procedure
===

testCreateCityCenterOnEmptyDatabase
---
+ _Objective_:  test if the court is created on empty database, provided the correct `inCityId`.
+ _Precondition_:
    + `inSportCenterId` is an alphanumeric characters sequence.
    + `inCityId`is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *200 - SUCCESS*


testCreateCityCenterWithDuplicate
---
+ _Objective_:  test if the request is rejected when `inSportCenterId` has already been created.
+ _Precondition_:
    + `inSportCenterId` is an alphanumeric characters sequence and `inSportCenterId` already exists in database.
    + `inCityId`is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *403 - SPORTCENTER ID ALREADY EXISTS*



testCreateCityCenterWithInvalidCityId
---
+ _Objective_:  test if the request is rejected when `inCityId` is invalid
+ _Precondition_:
    + `inSportCenterId` is an alphanumeric characters sequence.
    + `inCityId` does not exist in the database.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions.
+ _Pass condition_:
    + `statusCode` equals to *460 - INVALID CITY ID*


testCreateCityCenterInvalidCenterId
---
+ _Objective_: test if the request is rejected when `inSportCenterId` is invalid
+ _Precondition_:
    + `inSportCenterId`  is not an alphanumeric characters sequence.
    + `inCityId`is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *461 - INVALID SPORTCENTER ID*
