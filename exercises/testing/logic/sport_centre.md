`getSportCentreBooking`
---
- **Description:** get all bookings that have been made with the sport centre for a given date
- **Security/Caller:** `staffId`
- **Request:** `getSportCentreBooking(sportCentreId, date)`
- **Response:**
    + *Success:*
        + `successCode`
        + `Bookings[0..*]` (each element in `Bookings` is a structure contains )
    + *Error:*
        + `errorCode`
- **Tests:**
    + **`testGetSportCentreBooking`**:  test if the request is accepted and the data in the response is valid when `caller` is valid and all the parameters are valid
            + `staffId` exists in the data returned to the logic-tier from the data-tier
            + `sportCentreId` exists in the data returned to the logic-tier from the data-tier
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given
        + _Pass conditions_:
            + `successCode` is equal to *200 - SUCCESS*
            + Number of elements in `Bookings` is equal to the number of bookings for the given sport centre in the data-tier
            + Each element in `Bookings`, is a tuple `(bookingId, userId, sportCenterId, courtId, date, startTime, endTime, state)` which exists in the data returned to the logic-tier from the data-tier, where
                + `bookingId` uniquely identify the booking in the data-tier
                + `userId` exists in the data returned to the logic-tier from the data-tier
                + `sportCentreId` exists in the data returned to the logic-tier from the data-tier
                + `courtId` exists in the data returned to the logic-tier from the data-tier
                + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given
                + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by ":" *(colon)*,, and ordered as given **AND** `startTime` comes before `endTime`
                + `state` is equal to one of the following: `UPCOMING`, `UNPAID`, `PAID`, `CANCELLED`
    + **`testGetSportCentreBookingUnauthorized`**: test if the request is rejected when `caller` is invalid
      + _Preconditions_:
            + `staffId` does not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *401 - UNAUTHORIZED*
    + **`testGetSportCentreBookingInvalidId`**: test if the request is rejected when `caller` is valid and `sportCentreId` is invalid
        + _Preconditions_:
            + `staffId` exists in the data returned to the logic-tier from the data-tier
            + `sportCentreId` does not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *462 - INVALID SPORT CENTRE ID*
    + **`testGetSportCentreBookingInvalidDate`**: test if the request is rejected when `caller` is valid and `date` is invalid
        + _Preconditions_:
            + `staffId` exists in the data returned to the logic-tier from the data-tier
            + `date` is not formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given
        + _Pass conditions_:
            + ``errorCode`` is equal to *463 - INVALID DATE*


`getSportCentreInfo`
---
- **Description:** get all information about a sport centre
- **Security/Caller:** `staffId`
- **Request:** `getSportCentreInfo(sportCentreId)`
- **Response:**
    + *Success:*
        + `successCode`
        + `sportCentre`
    + *Error:*
        + `errorCode`
- **Tests:**
    + **`testGetSportCentreInfo`**: test if the request is accepted and a booking is created when `caller` is valid and all the parameters are valid
        + _Preconditions_:
            + `staffId` exists in the data returned to the logic-tier from the data-tier
            + `sportCentreId` exists in the data returned to the logic-tier from the data-tier
        + _Pass conditions_:
            + `successCode` is equal to *200 - SUCCESS*
            + `sportCentre` is a tuple `(name, city, address, phoneNum)` which exists in the data returned to the logic-tier from the data-tier, where:
                + `name` contains only alphanumeric characters and spaces
                + `city` contains only alphabetic characters and spaces
                + `address` contains only alphanumetic characters, spaces, and the following special characters `-/,`
                + `phoneNumber` contains only numeric characters
    + **`testGetSportCentreInfoUnauthorized`**: test if the request is rejected when `caller` is invalid
        + _Preconditions_:
            + `staffId` does not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *401 - UNAUTHORIZED*
    + **`testGetSportCentreInfoInvalidSportCentreId`**: test if the request is rejected when `caller` is valid and `sportCentreId` is invalid
        + _Preconditions_:
            + `staffId` exists in the data returned to the logic-tier from the data-tier
            + `sportCentreId` doest not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *462 - INVALID SPORT CENTRE ID*



updateSportCentreInfo (`updateName`, `updateCity`, `updatePhoneNumber`)
---
- **Description:** update information of a sport centre base on the corresponding parameter and a given sportCentreId
- **Security/Caller:** staffId
- **Request:** `updateName(sportCentreId, name)` / `updateCity(sportCentreId, cityId)` / `updatePhoneNumber(sportCentreId, phoneNum)`
- **Response:**
    + *Success:*
        + `successCode`
    + *Error:*
        + ``errorCode``
- **Tests:**
    + **`testUpdateSportCentreInfoUnauthorized`**: test if the request is rejected when the `caller` is invalid
        + _Preconditions_:
            + `staffId` does no exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *401 - UNAUTHORIZED*
    + **`testSportCentreInfoInvalidSportCentreId`**: test if the request is rejected when the `caller` is valid and `sportCentreId` is invalid
        + _Preconditions_:
            + `staffId` exists in the data returned to the logic-tier from the data-tier
            + `sportCentreId` doest not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *462 - INVALID SPORT CENTRE ID*
    + **`testUpdateName`**: test if the request is accepted and the sport centre's name in the data-tier is changed according to the provided information when the `caller` is valid
        + _Preconditions_:
            + `staffId` exists in the data returned to the logic-tier from the data-tier
            + `sportCentreId` exists in the data returned to the logic-tier from the data-tier
            + `name` contains only alphanumeric characters and spaces
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdateNameInvalid`**: test if the request is rejected when the `caller` is valid and `name` is invalid
        + _Preconditions_:
            + `staffId` exists in the data returned to the logic-tier from the data-tier
            + `sportCentreId` exists in the data returned to the logic-tier from the data-tier
            + `name` does not contain only alphanumeric characters and spaces
        + _Pass conditions_:
            + ``errorCode`` is equal to *472 - INVALID SPORT CENTRE NAME*
    + **`testUpdateCity`**: test if the request is accepted and the sport centre's `cityId` in the data-tier is changed according to the provided information when the `caller` is valid
        + _Preconditions_:
            + `staffId` exists in the data returned to the logic-tier from the data-tier
            + `sportCentreId` exists in the data returned to the logic-tier from the data-tier
            + `cityId` exists in the data returned to the logic-tier from the data-tier
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdateCityInvalid`**: test if the request is rejected when the `caller` is valid and `cityId` is invalid
        + _Preconditions_:
            + `staffId` exists in the data returned to the logic-tier from the data-tier
            + `sportCentreId` exists in the data returned to the logic-tier from the data-tier
            + `cityId` does not not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *461 - INVALID CITY ID*
    + **`testUpdateAddress`**: test if the request is accepted and the sport centre's `address` in the data-tier is changed according to the provided information when the `caller` is valid
        + _Preconditions_:
            + `staffId` exists in the data returned to the logic-tier from the data-tier
            + `sportCentreId` exists in the data returned to the logic-tier from the data-tier
            + `address` contains only alphanumetic characters, spaces, and the following special characters `-/,`
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdateCityInvalid`**: test if the request is rejected when the `caller` is valid and `cityId` is invalid
        + _Preconditions_:
            + `staffId` exists in the data returned to the logic-tier from the data-tier
            + `sportCentreId` exists in the data returned to the logic-tier from the data-tier
            + `address` does not contain only alphanumetic characters, spaces, and the following special characters `-/,`
        + _Pass conditions_:
            + ``errorCode`` is equal to *473 - INVALID ADDRESS*
    + **`testUpdatePhoneNumber`**: test if the request is accepted and the sport centre's phone number in the data-tier is changed according to the provided information when the `caller` is valid
        + _Preconditions_:
            + `staffId` exists in the data returned to the logic-tier from the data-tier
            + `sportCentreId` exists in the data returned to the logic-tier from the data-tier
            + `phonerNumber` contains only numeric characters
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdatePhoneNumberInvalid`**: test if the request is rejected when the `caller` is valid and `phoneNumber` is invalid
        + _Preconditions_:
            + `staffId` exists in the data returned to the logic-tier from the data-tier
            + `sportCentreId` exists in the data returned to the logic-tier from the data-tier
            + `phonerNumber` does not contain only numeric characters
        + _Pass conditions_:
            + ``errorCode`` is equal to *474 - INVALID PHONE NUMBER*