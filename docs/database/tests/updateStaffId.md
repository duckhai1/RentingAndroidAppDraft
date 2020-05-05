Test case for updateStaffId stored procedure
===

testUpdateStaffIdSuccess
---
+ _Objective_: test if `newStaffId` is updated successful, when all the given parameters are valid.
+ _Precondition_:
    + `newStaffId` is an alphanumeric characters sequence
    + `inStaffId` is an alphanumeric characters sequence and uniquely identifies a row in `staff` relation.
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
    + `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *200 - SUCCESS*


testGetStaffInfoInvalidStaffId
---
+ _Objective_: test if the request is rejected when `inStaffId` is invalid.
+ _Precondition_:
    + `inStaffId` is not an alphanumeric characters sequence and uniquely identifies a row in `staff` relation.
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
    + `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *463 - INVALID STAFF ID*


testUpdateStaffInvalidNewStaffId
---
+ _Objective_: test if the request is rejected when `newStaffId` is invalid.
+ _Precondition_:
    + `newStaffId` is not an alphanumeric characters sequence.
    + `inStaffId` is an alphanumeric characters sequence and uniquely identifies a row in `staff` relation.
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
    + `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *463 - INVALID STAFF ID*


testGetStaffInfoInvalidCityId
---
+ _Objective_: test if the request is rejected when `inCityId` is invalid.
+ _Precondition_:
    + `newStaffId` is an alphanumeric characters sequence.
    + `inStaffId` is an alphanumeric characters sequence and uniquely identifies a row in `staff` relation.
    + `inCityId` is not alphanumeric characters sequence and uniquely identifies a row in `city` relation.
    + `inSportCenterId` is an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *460 - INVALID CITY ID*


testUpdateStaffInvalidCitySportCenterId
---
+ _Objective_: test if the request is rejected when `inSportCenter` is invalid.
+ _Precondition_:
    + `newStaffId` is an alphanumeric characters sequence.
    + `inStaffId` is an alphanumeric characters sequence and uniquely identifies a row in `staff` relation.
    + `inCityId` is alphanumeric characters sequence and uniquely identifies a row in `city` relation.
    + `inSportCenterId` is not an alphanumeric characters sequence and uniquely identifies a row in `sportCenter` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *461 - INVALID SPORTCENTER ID*