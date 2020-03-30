Query/Changing user's information
===

`getUserInfo`
---
- **Description:** get all information user that sent the request
- **Security/Caller:** `userId`
- **Request:** `getUserInfo()`
- **Response:**
    + *Success:*
        + `successCode`
        + `user` `(firstName, lastName, birthday, phoneNumber)`
    + *Error:*
        + ``errorCode``
- **Tests:**
    + **`testGetUserInfo`**: test if the request is accepted and the data in the response is valid when `caller` is valid and all the parameters are valid
        + _Preconditions_:
           + `userId` exists in the data returned to the logic-tier from the data-tier
        + _Test conditions_:
            + `succesCode` is equal to *201 - SUCCESS WITH DATA*
            + `user` is a tuple `(firstName, lastName, birthday, phoneNumber)` which exists in the data returned to the logic-tier from the data-tier, where:
                + `firstName` and `lastName` contains only lower-case and upper-case alphabetic characters and spaces but not with leading and trailing spaces
                + `birthday` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND** `date` comes after the date when the interface is called
                + `phoneNumber` contains only numberic characters
    + **`testGetUserInfoUnauthorized`**:  test if the request is rejected when `caller` is invalid
        + _Preconditions_:
           + `userId` doest not exist in the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *401 - UNAUTHORIZED*

updateUserInfo (`updateFirstName`, `updateLastName`, `updateBirthday`, `updatePhoneNumber`)
---
- **Description:** update the user's information, whose id is equal to the provided provided `userId`
- **Security/Caller:** `userId`
- **Request:** `updateUserFirstName(firstName)` / `updateUserLastName(lastName)` / `updateUserBirthday(birthday)` / `updateUserPhoneNum(phoneNum)`
- **Response:**
    + *Success:*
        + `successCode`
    + *Error:*
        + `errorCode`
- **Tests:**
    + **`testUpdateUserInfoUnauthorized`**: test if the request is rejected when the `caller` is invalid
        + _Preconditions_:
            + `userId` does no exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *401 - UNAUTHORIZED*
    + **`testUpdateUserFirstName`**: test if the request is accepted and the user's first name in the data-tier is changed according to the provided information when the `caller` is valid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `firstName` contains only alphabetic characters and spaces
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdateUserFirstNameInvalid`**: test if the request is rejected when the `caller` is valid and `firstName` is invalid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `firstName` does not contain only alphanumeric characters and spaces
        + _Pass conditions_:
            + ``errorCode`` is equal to *475 - INVALID FIRST NAME*

    + **`testUpdateUserLastName`**: test if the request is accepted and the user's last name in the data-tier is changed according to the provided information when the `caller` is valid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `lastName` contains only alphabetic characters and spaces
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdateUserLastNameInvalid`**: test if the request is rejected when the `caller` is valid and `lastName` is invalid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `lastName` does not contain only alphebetic characters and spaces
        + _Pass conditions_:
            + ``errorCode`` is equal to *476 - INVALID LAST NAME*
    + **`testUpdateUserBirthDay`**: test if the request is accepted and the user's birth day in the data-tier is changed according to the provided information when the `caller` is valid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `birthday` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdateUserBirthDayInvalid`**: test if the request is rejected when the `caller` is valid and `birthday` is invalid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `birthday` is not formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given
        + _Pass conditions_:
            + ``errorCode`` is equal to *477 - INVALID BIRTHDAY*
    + **`testUpdateUserPhoneNumber`**: test if the request is accepted and the user's phone number in the data-tier is changed according to the provided information when the `caller` is valid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `phoneNumber` contains only numeric characters
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdateUserPhoneNumberInvalid`**: test if the request is rejected when the `caller` is valid and `phoneNumber` is invalid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `phoneNumber` does not contain only numeric characters
        + _Pass conditions_:
            + ``errorCode`` is equal to *474 - INVALID PHONE NUMBER*


`getUserBooking`
---
- **Description:** get all the bookings that the user have made for a given city and date
- **Security/Caller:** `userId`
- **Request:** `getUserBooking(cityId, date)`
- **Response:**
    + *Success:*
        + `successCode`
        + `Bookings[0..*]`
    + *Error:*
        + `errorCode`
- **Tests:**
    + **`testGetUserBooking`**:  test if the request is accepted and the data in the response is valid when `caller` is valid and all the parameters are valid
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `cityId` exists in the data returned to the logic-tier from the data-tier
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given
        + _Pass conditions_:
            + `successCode` is equal to *200 - SUCCESS*
            + Number of elements in `Bookings` is equal to the number of bookings for the given sport centre in the data-tier
            + Each element in `Bookings` is a tuple `(bookingId, userId, sportCentreId, courtId, date, startTime, endTime, state)` which exists in the data returned to the logic-tier from the data-tier, where
                + `bookingId` uniquely identify the booking in the data-tier
                + `userId` exists in the data returned to the logic-tier from the data-tier
                + `sportCentreId` exists in the data returned to the logic-tier from the data-tier
                + `courtId` exists in the data returned to the logic-tier from the data-tier
                + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given
                + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by ":" *(colon)*,, and ordered as given **AND** `startTime` comes before `endTime`
                + `state` is equal to one of the following: `UPCOMING`, `UNPAID`, `PAID`, `CANCELLED`
    + **`testGetUserBookingUnauthorized`**: test if the request is rejected when `caller` is invalid
      + _Preconditions_:
            + `userId` does not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *401 - UNAUTHORIZED*
    + **`testGetUserBookingInvalidCityId`**: test if the request is rejected when `caller` is valid and `cityId` is invalid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `cityId` does not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *461 - INVALID CITY ID*
    + **`testGetUserBookingInvalidDate`**: test if the request is rejected when `caller` is valid and `date` is invalid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `date` is not formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given
        + _Pass conditions_:
            + ``errorCode`` is equal to *463 - INVALID DATE*


These interfaces have not been clearly specified
---

`loginUser`
---
- **Description:** authenticate the user by the given username and password
- **Security/Caller:** anonymous
- **Request:** loginUser(username, password)
- **Response:**
    + *Success:*
        + `successCode`
    + *Error:*
        + `errorCode`
- **Tests:**
    + **`testLoginUser`**: test if the user is authenticated under expected preconditions
        + _Preconditions_:
            + The user has an account
            + Valid parameters are provided
        + _Pass conditions_:
            + The response indicates the request is success
            + The user is authenticated
            + The user can access information that he/she is authorized to
    + **`testLoginUserUnknown`**: test if the servers behaves as expected when invalid credentials are provided
        + _Preconditions_:
            + Invalid credentials are provided (username-password pair is not contained in the database)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate a fail login attempt was made
            + The user is not authenticated
    + **`testLoginUserInvalidUsername`**: test if the server behaves as expected when an invalid `username` is provided
        + _Preconditions_:
            + Invalid `username` is provided (`username` is in invalid format)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate an invalid `username` was provided
            + The user is not authenticated
    + **`testLoginUserInvalidPassword`**: test if the server behaves as expected when an invalid `password` is provided
        + _Preconditions_:
            + Invalid `password` is provided (`password` is in invalid format)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate an invalid `password` was provided
            + The user is not authenticated

`logoutUser`
---
- **Description:** unauthenticate the user
- **Security/Caller:** userId
- **Request:** logoutUser(userId)
- **Response:**
    + *Success:*
        + `successCode`
    + *Error:*
        + `errorCode`
- **Tests:**
    + **`testLogoutUser`**: test if the user is unauthenticated under expected preconditions
        + _Preconditions_:
            + The user is logged in
            + Valid parameters are provided
        + _Pass conditions_:
            + The response indicates the request is success
            + The user is unauthenticated
    + **`testLogoutUserUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The user is not logged in
            + Valid parameters are provided
        + _Pass conditions_:
            + The response contains an error code along with a message indicate the request is unauthorized
    + **`testLogoutUserInvalidId`**: test if the server behaves as expected when an invalid `userId` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `userId` is provided (`userId` is not contained in the database, or `userId` is in invalid format) (`userId` is not contained in the database, or `userId` is in invalid format)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate an invalid `userId` was provided
            + The user is not unauthenticated

