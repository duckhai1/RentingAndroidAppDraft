Test case for createStaff stored procedure
===

testCreateStaffSuccess
---
+ _Objective_:  test if the staff is created successfully on empty database.
+ _Precondition_:
    + `inStaffId` is an alphanumeric characters sequence
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
    + `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *200 - SUCCESS*


createStaffWithDuplicate
---
+ _Objective_: test if request is rejected when `inStaffId` already exists.
+ _Precondition_:
    + `inStaffId` is an alphanumeric characters sequence and `inStaffId` already exists in database
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
    + `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *406 - STAFF ID ALREADY EXISTS*


createStaffWithInvalidStaffId
---
+ _Objective_: test if request is rejected when `inStaffId` is invalid.
+ _Precondition_:
    + `inStaffId` is not an alphanumeric characters sequence
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
    + `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *463 - INVALID STAFF ID*


createStaffWithInvalidCityId
---
+ _Objective_: test if request is rejected when `inCityId` is invalid.
+ _Precondition_:
    + `inStaffId` is an alphanumeric characters sequence
    + `inCityId` does not exist in the database.
    + `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *460 - INVALID CITY ID*


createStaffWithInvalidCenterId
---
+ _Objective_: test if request is rejected when `inSportCenterId` is invalid.
+ _Precondition_:
    + `inStaffId` is an alphanumeric characters sequence
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
    + `inSportCenterId` does not exit in the database.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *461 - INVALID SPORTCENTER ID*
