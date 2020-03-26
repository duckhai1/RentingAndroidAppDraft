`contact`
---
- **Description:** contact a sportCenter/user by a given senderId, receiverId and a message
- **Security/Caller:** userId/ staffId
- **Request:** contact(sportCenterId/userId (**senderId**), msg, sportCenterId/userId (**reveiverId**))
- **Response:**
    + *Success:*
        + SuccessCode
    + *Error:*
        + errorCode
+ **Tests:**
    + **`testContact`**: test if the message can be sent under expected preconditions
        + _Preconditions_:
            + Sender is logged in
            + Valid parameters are provided
        + _Expected outcome_:
            + The response indicates the request is success
            + The message is stored in the database
            + The receiver is notified about the message
    + **`testContactUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The sender is not logged in
            + Valid parameters are provided
        + _Expected outcome_:
            + The response contains an error code along with a message indicate the request is unauthorized
    + **`testContactInvalidSenderId`**: test if the the server behaves as expected when an invalid `senderId` is provided
        + _Preconditions_:
            + The sender is logged in
            + Invalid `senderId` is provided (`senderId` is not contained in the database, or `senderId` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `senderId` was provided
            + The message is not stored in the database
            + The receiver is not notified about the message
    + **`testContactInvalidReceiverId`**: test if the the server behaves as expected when an invalid `receiverId` is provided
        + _Preconditions_:
            + The sender is logged in
            + Invalid `receiverId` is provided (`receiverId` is not contained in the database, or `receiverId` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `receiverId` was provided
            + The message is not stored in the database

`report`
---
- **Description:** report a user or a sportCenter by a given reporterId and reporteeId and a message
- **Security/Caller:** userId/ staffId
- **Request:** report(sportCenterId/userId (**reporterId**), msg, sportCenterId/userId (**reporteeId**))
- **Response:**
    + *Success:*
        + SuccessCode
    + *Error:*
        + errorCode
+ **Tests:**
    + **`testReport`**: test if the message can be sent under expected preconditions
        + _Preconditions_:
            + Sender is logged in
            + Valid parameters are provided
        + _Expected outcome_:
            + The response indicates the request is success
            + The report is stored in the database
            + The reportee is notified about the message
    + **`testReportUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The reporter is not logged in
            + Valid parameters are provided
        + _Expected outcome_:
            + The response contains an error code along with a message indicate the request is unauthorized
    + **`testReportInvalidReporterId`**: test if the the server behaves as expected when an invalid `reporterId` is provided
        + _Preconditions_:
            + The reporter is logged in
            + Invalid `reporterId` is provided (`reporterId` is not contained in the database, or `reporterId` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `reporterId` was provided
            + The message is not stored in the database
            + The reportee is not notified about the message
    + **`testReportInvalidReporteeId`**: test if the the server behaves as expected when an invalid `reporteeId` is provided
        + _Preconditions_:
            + The reporter is logged in
            + Invalid `reporteeId` is provided (`reporteeId` is not contained in the database, or `reporteeId` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `reporteeId` was provided
            + The message is not stored in the database
