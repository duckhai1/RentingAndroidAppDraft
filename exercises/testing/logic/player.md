Query/Changing player's information
===

`getPlayerInfo`
---
- **Description:** get all information player that sent the request
- **Security/Caller:** `playerId`
- **Request:** `getPlayerInfo()`
- **Response:**
    + *Success:*
        + `successCode`
        + `player` `(firstName, lastName, birthday, phoneNumber)`
    + *Error:*
        + ``errorCode``
- **Tests:**
    + **`testGetPlayerInfo`**: test if the request is accepted and the data in the response is valid when `caller` is valid and all the parameters are valid
        + _Preconditions_:
           + `playerId` exists in the data returned to the logic-tier from the data-tier
        + _Test conditions_:
            + `succesCode` is equal to *201 - SUCCESS WITH DATA*
            + `player` is a tuple `(firstName, lastName, birthday, phoneNumber)` which exists in the data returned to the logic-tier from the data-tier, where:
                + `firstName` and `lastName` contains only lower-case and upper-case alphabetic characters and spaces but not with leading and trailing spaces
                + `birthday` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND** `date` comes after the date when the interface is called
                + `phoneNumber` contains only numberic characters
    + **`testGetPlayerInfoUnauthorized`**:  test if the request is rejected when `caller` is invalid
        + _Preconditions_:
           + `playerId` doest not exist in the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *401 - UNAUTHORIZED*

updatePlayerInfo (`updateFirstName`, `updateLastName`, `updateBirthday`, `updatePhoneNumber`)
---
- **Description:** update the player's information, whose id is equal to the provided provided `playerId`
- **Security/Caller:** `playerId`
- **Request:** `updatePlayerFirstName(firstName)` / `updatePlayerLastName(lastName)` / `updatePlayerBirthday(birthday)` / `updatePlayerPhoneNum(phoneNum)`
- **Response:**
    + *Success:*
        + `successCode`
    + *Error:*
        + `errorCode`
- **Tests:**
    + **`testUpdatePlayerInfoUnauthorized`**: test if the request is rejected when the `caller` is invalid
        + _Preconditions_:
            + `playerId` does no exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *401 - UNAUTHORIZED*
    + **`testUpdatePlayerFirstName`**: test if the request is accepted and the player's first name in the data-tier is changed according to the provided information when the `caller` is valid
        + _Preconditions_:
            + `playerId` exists in the data returned to the logic-tier from the data-tier
            + `firstName` contains only alphabetic characters and spaces
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdatePlayerFirstNameInvalid`**: test if the request is rejected when the `caller` is valid and `firstName` is invalid
        + _Preconditions_:
            + `playerId` exists in the data returned to the logic-tier from the data-tier
            + `firstName` does not contain only alphanumeric characters and spaces
        + _Pass conditions_:
            + ``errorCode`` is equal to *475 - INVALID FIRST NAME*

    + **`testUpdatePlayerLastName`**: test if the request is accepted and the player's last name in the data-tier is changed according to the provided information when the `caller` is valid
        + _Preconditions_:
            + `playerId` exists in the data returned to the logic-tier from the data-tier
            + `lastName` contains only alphabetic characters and spaces
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdatePlayerLastNameInvalid`**: test if the request is rejected when the `caller` is valid and `lastName` is invalid
        + _Preconditions_:
            + `playerId` exists in the data returned to the logic-tier from the data-tier
            + `lastName` does not contain only alphebetic characters and spaces
        + _Pass conditions_:
            + ``errorCode`` is equal to *476 - INVALID LAST NAME*
    + **`testUpdatePlayerBirthDay`**: test if the request is accepted and the player's birth day in the data-tier is changed according to the provided information when the `caller` is valid
        + _Preconditions_:
            + `playerId` exists in the data returned to the logic-tier from the data-tier
            + `birthday` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdatePlayerBirthDayInvalid`**: test if the request is rejected when the `caller` is valid and `birthday` is invalid
        + _Preconditions_:
            + `playerId` exists in the data returned to the logic-tier from the data-tier
            + `birthday` is not formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given
        + _Pass conditions_:
            + ``errorCode`` is equal to *477 - INVALID BIRTHDAY*
    + **`testUpdatePlayerPhoneNumber`**: test if the request is accepted and the player's phone number in the data-tier is changed according to the provided information when the `caller` is valid
        + _Preconditions_:
            + `playerId` exists in the data returned to the logic-tier from the data-tier
            + `phoneNumber` contains only numeric characters
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdatePlayerPhoneNumberInvalid`**: test if the request is rejected when the `caller` is valid and `phoneNumber` is invalid
        + _Preconditions_:
            + `playerId` exists in the data returned to the logic-tier from the data-tier
            + `phoneNumber` does not contain only numeric characters
        + _Pass conditions_:
            + ``errorCode`` is equal to *474 - INVALID PHONE NUMBER*


`getPlayerBooking`
---
- **Description:** get all the bookings that the player have made for a given city and date
- **Security/Caller:** `playerId`
- **Request:** `getPlayerBooking(cityId, date)`
- **Response:**
    + *Success:*
        + `successCode`
        + `Bookings[0..*]`
    + *Error:*
        + `errorCode`
- **Tests:**
    + **`testGetPlayerBooking`**:  test if the request is accepted and the data in the response is valid when `caller` is valid and all the parameters are valid
            + `playerId` exists in the data returned to the logic-tier from the data-tier
            + `cityId` exists in the data returned to the logic-tier from the data-tier
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given
        + _Pass conditions_:
            + `successCode` is equal to *200 - SUCCESS*
            + Number of elements in `Bookings` is equal to the number of bookings for the given sport centre in the data-tier
            + Each element in `Bookings` is a tuple `(bookingId, playerId, sportCentreId, courtId, date, startTime, endTime, state)` which exists in the data returned to the logic-tier from the data-tier, where
                + `bookingId` uniquely identify the booking in the data-tier
                + `playerId` exists in the data returned to the logic-tier from the data-tier
                + `sportCentreId` exists in the data returned to the logic-tier from the data-tier
                + `courtId` exists in the data returned to the logic-tier from the data-tier
                + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given
                + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by ":" *(colon)*,, and ordered as given **AND** `startTime` comes before `endTime`
                + `state` is equal to one of the following: `UPCOMING`, `UNPAID`, `PAID`, `CANCELLED`
    + **`testGetPlayerBookingUnauthorized`**: test if the request is rejected when `caller` is invalid
      + _Preconditions_:
            + `playerId` does not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *401 - UNAUTHORIZED*
    + **`testGetPlayerBookingInvalidCityId`**: test if the request is rejected when `caller` is valid and `cityId` is invalid
        + _Preconditions_:
            + `playerId` exists in the data returned to the logic-tier from the data-tier
            + `cityId` does not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *460 - INVALID CITY ID*
    + **`testGetPlayerBookingInvalidDate`**: test if the request is rejected when `caller` is valid and `date` is invalid
        + _Preconditions_:
            + `playerId` exists in the data returned to the logic-tier from the data-tier
            + `date` is not formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given
        + _Pass conditions_:
            + ``errorCode`` is equal to *466 - INVALID DATE*


These interfaces have not been clearly specified
---

`loginPlayer`
---
- **Description:** authenticate the player by the given playername and password
- **Security/Caller:** anonymous
- **Request:** loginPlayer(playername, password)
- **Response:**
    + *Success:*
        + `successCode`
    + *Error:*
        + `errorCode`
- **Tests:**
    + **`testLoginPlayer`**: test if the player is authenticated under expected preconditions
        + _Preconditions_:
            + The player has an account
            + Valid parameters are provided
        + _Pass conditions_:
            + The response indicates the request is success
            + The player is authenticated
            + The player can access information that he/she is authorized to
    + **`testLoginPlayerUnknown`**: test if the servers behaves as expected when invalid credentials are provided
        + _Preconditions_:
            + Invalid credentials are provided (playername-password pair is not contained in the database)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate a fail login attempt was made
            + The player is not authenticated
    + **`testLoginPlayerInvalidPlayername`**: test if the server behaves as expected when an invalid `playername` is provided
        + _Preconditions_:
            + Invalid `playername` is provided (`playername` is in invalid format)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate an invalid `playername` was provided
            + The player is not authenticated
    + **`testLoginPlayerInvalidPassword`**: test if the server behaves as expected when an invalid `password` is provided
        + _Preconditions_:
            + Invalid `password` is provided (`password` is in invalid format)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate an invalid `password` was provided
            + The player is not authenticated

`logoutPlayer`
---
- **Description:** unauthenticate the player
- **Security/Caller:** playerId
- **Request:** logoutPlayer(playerId)
- **Response:**
    + *Success:*
        + `successCode`
    + *Error:*
        + `errorCode`
- **Tests:**
    + **`testLogoutPlayer`**: test if the player is unauthenticated under expected preconditions
        + _Preconditions_:
            + The player is logged in
            + Valid parameters are provided
        + _Pass conditions_:
            + The response indicates the request is success
            + The player is unauthenticated
    + **`testLogoutPlayerUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The player is not logged in
            + Valid parameters are provided
        + _Pass conditions_:
            + The response contains an error code along with a message indicate the request is unauthorized
    + **`testLogoutPlayerInvalidId`**: test if the server behaves as expected when an invalid `playerId` is provided
        + _Preconditions_:
            + The player is logged in
            + Invalid `playerId` is provided (`playerId` is not contained in the database, or `playerId` is in invalid format) (`playerId` is not contained in the database, or `playerId` is in invalid format)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate an invalid `playerId` was provided
            + The player is not unauthenticated

