Test cases for getCityCourts stored procedure
===

testGetCityCourtSuccess
---
+ _Objective_: test if a court list can be returned successfully provided the correct `inCityId`.
+ _Precondition_:
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` is equal to *200 - SUCCESS*


testCityCourtInvalidCityId
---
+ _Objective_: test if the request is rejected when `inCityId` is invalid.
+ _Precondition_:
    + `inCityId` is not an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` is equal to *460 - INVALID CITY ID*
