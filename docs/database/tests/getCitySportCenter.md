Test case for getCitySportCenter stored procedure
===

testGetCityCentersSuccess
---
+ _Objective_: test if a sport center list can be returned successfully provided the correct `inCityId`.
+ _Precondition_:
    + `inCityId` is an alphanumeric characters sequence and uniquely identifies a row in `city` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *200 - SUCCESS*


testGetCityInvalidCityId
---
+ _Objective_: test if the request is rejected when `inCityId` is invalid.
    + `inCityId` does not exist in the database.
+ _Precondition_:
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` equals to *460 - INVALID CITY ID*
