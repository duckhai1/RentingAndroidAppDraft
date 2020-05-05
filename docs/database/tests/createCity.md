Test cases for createCity stored procedure
===

testCreateCityOnEmptyDataMultipleIds
---
+ _Objective__: Test if creating multiple cities with different ids is possible
+ _Preconditions_:
	+ `inCityId` is an alphanumeric characters sequence
+ _Steps_: Call the stored procedures multiple times with data that sastifies the preconditions, and each call has a different `inCityId`
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS* for every call

testCreateCityDuplicateId
---
+ _Objective__: Test if the request is rejected when provided an existing city id
+ _Preconditions_:
	+ `city` relation contains a row
	+ `inCityId` is equal to the `cityId` of the existing row
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *402 - CITY ID ALREADY EXISTS*

testCreateCityInvalidCityId
---
+ _Objective__: Test if the request is rejected when provided an invalid city id
+ _Preconditions_:
	+ `inCityId` is not an alphanumeric characters sequence
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *460 - INVALID CITY ID*
