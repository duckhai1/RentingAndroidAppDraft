`getSportCenterBooking`
---
- **Description:** get all booking in the sportCenter by a given sportCenterId and date
- **Security/Caller:** staffId
- **Request:** getSportCenterBooking(sportCenterId, date)
- **Response:**
    + *Success:*
        + SuccessCode
        + sportCenterBookingArray (where sportCenterBooking structure contains these attributes: bookingId, userId, courtId, date, startTime, endTime, state)
    + *Error:*
        + errorCode
- **Tests:**
    + **`testGetSportCenterBooking`**: test if data in the response is valid under expected preconditions
        + _Preconditions_:
            + The staff is logged in
            + Valid parameters are provided
        + _Test conditions_:
            + The response indicates the request is success
            + The response contains the list of all the bookings, made by the user with the sport centre, in structured format
            + Each booking contains correct information about itself
    + **`testGetSportCenterBookingUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The staff is not logged in
            + Valid parameters are provided
        + _Test conditions_:
            + The response contains an error code along with a message indicate the request is unauthorized
    + **`testGetSportCenterBookingInvalidId`**: test if the server behaves as expected when an invalid `sportCenterId` is provided
        + _Preconditions_:
            + The staff is logged in
            + Invalid `sportCenterId` is provided (`sportCenterId` is not contained in the database, or `sportCenterId` is in invalid format)
        + _Test conditions_:
            + The response contains an error code along with an error message indicate an invalid `sportCenterId` was provided
    + **`testGetSportCenterBookingInvalidDate`**: test if the server behaves as expected when an invalid `date` is provided
        + _Preconditions_:
            + The staff is logged in
            + Invalid `date` is provided (`date` is in invalid format)
        + _Test conditions_:
            + The response contains an error code along with an error message indicate an invalid `date` was provided


`getSportCenterInfo`
---
- **Description:** get all information of the sportCenter by a given sportCenterId
- **Security/Caller:** staffId
- **Request:** getSportCenterInfo(sportCenterId)
- **Response:**
    + *Success:*
        + SuccessCode
        + sportCenterInfo (where sportCenterInfo structure contains these attributes: name, city, address, phoneNum, courtIdArray)
    + *Error:*
        + errorCode
- **Tests:**
    + **`testGetSportCenterInfo`**: test if data in the response is valid under expected preconditions
        + _Preconditions_:
            + The staff is logged in
            + Valid parameters are provided
        + _Expected response_:
            + The response indicates the request is success
            + The response contains requested information about the sport centre
    + **`testGetSportCenterInfo`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The staff is not logged in
            + Valid parameters are provided
        + _Test conditions_:
            + The response contains an error code along with a message indicate the request is unauthorized
    + **`testGetSportCenterInfoInvalidSportCenterId`**: test if the server behaves as expected when an invalid `sportCenterId` is provided
        + _Preconditions_:
            + The staff is logged in
            + Invalid `sportCenterId` is provided (`sportCenterId` is not contained in the database, or `sportCenterId` is in invalid format)
        + _Test conditions_:
            + The response contains an error code along with an error message indicate an invalid `sportCenterId` is provided



updateSportCenterInfo (`updateName`, `updateCity`, `updatePhoneNumber`)
---
- **Description:** update information of a sport centre base on the corresponding parameter and a given sportCenterId
- **Security/Caller:** staffId
- **Request:** updateName(sportCenterId, name)/ updateCity(sportCenterId, cityId)/ updatePhoneNumber(sportCenterId, phoneNum)
- **Response:**
    + *Success:*
        + SuccessCode
    + *Error:*
        + errorCode
- **Tests:**
    + **`testUpdateSportCenterInfo`**: test if the sport center's information in the database is changed according to the new information under expected preconditions
        + _Preconditions_:
            + The user is logged in
            + Valid parameters are provided
        + _Test conditions_:
            + The response indicates the request is success
            + The user's information in the data is changed according the newly provided information
    + **`testUpdateSportCenterInfoUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The staff is not logged in
            + Valid parameters are provided
        + _Test conditions_:
            + The response contains an error code along with a message indicate the request is unauthorized
    + **`testUpdateSportCenterInfoInvalidId`**: test if the the server behaves as expected when an invalid `sportCenterId` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `sportCenterId` is provided (`sportCenterId` is not contained in the database, or `sportCenterId` is in invalid format)
        + _Test conditions_:
            + The response contains an error code along with an error message indicate an invalid `sportCenterId` was provided
            + The information about the user in the database is not changed
    + **`testUpdateSportCenterInfoInvalidName`**: test if the the server behaves as expected when an invalid `name` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `name` is provided (`name` is in invalid format)
        + _Test conditions_:
            + The response contains an error code along with an error message indicate an invalid `name` was provided
            + The information about the user in the database is not changed
    + **`testUpdateSportCenterInfoInvalidCity`**: test if the the server behaves as expected when an invalid `cityId` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `cityId` is provided (`cityId` is not contained in the database, or `cityId` is in invalid format)
        + _Test conditions_:
            + The response contains an error code along with an error message indicate an invalid `cityId` was provided
            + The information about the user in the database is not changed
    + **`testUpdateSportCenterInfoInvalidPhoneNumber`**: test if the the server behaves as expected when an invalid `phoneNumber` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `phoneNumber` is provided (`phoneNumber` is in invalid format)
        + _Test conditions_:
            + The response contains an error code along with an error message indicate an invalid `phoneNumber` was provided
            + The information about the user in the database is not changed



