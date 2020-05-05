Test cases for getCities stored procedure
===

testGetMultipleCities
---
+ _Objective__: Test the stored procedure sets the correct status code and returns the correct relation
+ _Preconditions_:
	+ The `city` relation contains 5 cities
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS*
	+ The returned relation has the schema `R(cityId)`
	+ The returned relation contains 5 rows

testGetNoCity
---
+ _Objective__: Test the stored procedure sets the correct status code and returns the correct relation
+ _Preconditions_:
	+ The `city` relation contains 0 cities
+ _Steps_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass conditions_:
	+ `statusCode` is equal to *200 - SUCCESS*
