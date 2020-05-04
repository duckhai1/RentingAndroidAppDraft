Test cases for updatePlayerId stored procedure
===

testUpdatePlayerIdSuccessfully
---
+ _Objective_: test if `newPlayerId` is updated successfully.
+ _Precondition_:
    + `newPlayerId` is an alphanumeric characters sequence
    + `inPlayerId` is an alphanumeric characters sequence and uniquely identifies a row in `player` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` is equal to *200 - SUCCESS*.


testUpdatePlayerIdExisted
---
+ _Objective_: test if the request is rejected when `newPlayerId` has already been created.
+ _Precondition_:
    + `newPlayerId` is an alphanumeric characters sequence
	+ `inPlayerId` is an alphanumeric characters sequence and uniquely identifies a row in `player` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` is equal to *405 - PLAYER ID ALREADY EXISTS*.


testUpdatePlayerIdInvalidFormat
---
+ _Objective_: test if the request is rejected when `newPlayerId` is invalid.
+ _Precondition_:
    + `newPlayerId` is not an alphanumeric characters sequence
	+ `inPlayerId` is an alphanumeric characters sequence and uniquely identifies a row in `player` relation.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` is equal to *464 - INVALID PLAYER ID*.
