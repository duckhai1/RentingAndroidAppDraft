Test cases for createPlayer stored procedure
===

testCreatePlayerOnEmptyDatabase
---
+ _Objective_: test if the player is created successfully on empty database.
+ _Precondition_:
    + `inPlayerId` is an alphanumeric characters sequence.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` is equal to *200 - SUCCESS*


testCreatePlayerOnEmptyDataMultipleIds
---
+ _Objective_: test if multiple players are created on empty database.
+ _Precondition_:
    + `inPlayerId` is an alphanumeric characters sequence.
+ _Step_: Call the stored procedures 3 times with different `inPlayerId`.
    + `statusCode` is equal to *200 - SUCCESS*


testCreatePlayerWithDuplicateId
---
+ _Objective_: test if the request is rejected when `inPlayerId` is duplicated.
+ _Precondition_:
    + `inPlayerId` is an alphanumeric characters sequence.
+ _Step_: Call the stored procedures twice with data that sastifies the preconditions.
+ _Pass condition_:
    + `statusCode` is equal to *405 - PLAYER ID ALREADY EXISTS*


testCreatePlayerWithInvalidIdFormat
---
+ _Objective_: test if the request is rejected when `inPlayerId` is invalid
+ _Precondition_:
    + `inPlayerId` is not an alphanumeric characters sequence.
+ _Step_: Call the stored procedures once with data that sastifies the preconditions
+ _Pass condition_:
    + `statusCode` is equal to *464 - INVALID PLAYER ID*.