`contact`
---
- **Description:** transfer messages between staff and player
- **Security/Caller:** `playerId/staffId`
- **Request:** `contact(playerId/sportCentreId, msg)`
- **Response:**
    + *Success:*
        + `successCode`
    + *Error:*
        + `errorCode`
+ **Tests:**
    + **`testContact`**: test if the request is accepted and the message is sent when `caller` is valid and all the parameters are valid
        + _Preconditions_:
            + `caller` (`playerId/staffId`) exists in the data returned to the logic-tier from the data-tier
            + `sportCentreId/sportCentreId` exists in the data returned to the logic-tier from the data-tier
        + _Pass conditions_:
            + `successCode` is equal to *202 - CREATED*
            + *Side effect*:  tuples and relations needed for the message are created in the data tier
    + **`testContactUnauthorized`**: test if the request is rejected when `caller` is invalid
        + _Preconditions_:
            + `caller` (`playerId/staffId`) doest not exist in the data-tier invalid
        + _Pass conditions_:
            + `errorCode` is equal to *401 - UNAUTHORIZED*
    + **`testContactInvalidReceiverId`**: test if the request is rejected when `caller` is valid and `receiver` is invalid
        + _Preconditions_:
            + `caller` (`playerId/staffId`) exists in the data returned to the logic-tier from the data-tier
            + `playerId/sportCentreId` does not exists in the data returned to the logic-tier from the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *468 - INVALID RECEIVER ID*

`report`
---
- **Description:** create a report message for a staff or a player
- **Security/Caller:** `playerId/staffId`
- **Request:** `report(playerId/sportCentreId, msg)`
- **Response:**
    + *Success:*
        + `successCode`
    + *Error:*
        + `errorCode`
+ **Tests:**
    + **`testReport`**: test if the request is accepted and a report is created when `caller` is valid and all the paramters are valid
        + _Preconditions_:
            + `caller` (`playerId/staffId`) exists in the data returned to the logic-tier from the data-tier
            + `sportCentreId/sportCentreId` exists in the data returned to the logic-tier from the data-tier
        + _Pass conditions_:
            + `successCode` is equal to *202 - CREATED*
            + *Side effect*:  tuples and relations needed for the report are created in the data tier
    + **`testReportUnauthorized`**: test if the request is rejected when `caller` is invalid
        + _Preconditions_:
            + `caller` (`playerId/staffId`) does not exist in the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *401 - UNAUTHORIZED*
    + **`testReportInvalidReporteeId`**: test if the request is rejected when `caller` is valid and `reportee` is invalid
        + _Preconditions_:
            + `caller` (`playerId/staffId`) exists in the data returned to the logic-tier from the data-tier
            + `playerId/sportCentreId` does not exists in the data returned to the logic-tier from the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *467 - INVALID REPORTEE ID*
