Query/Changing user's information
===

`getUserInfo`
---
- **Description:** get all information of the user by a given userId
- **Security/Caller:** `userId`
- **Request:** `getUserInfo(userId)`
- **Response:**
    + *Success:*
        + `successCode`
        + `userInfo` (containing the attributes: `firstName`, `lastName`, `dateOfBirth`, `phoneNumber`)
    + *Error:*
        + `errorCode`
- **Tests:**
    + **`testGetUserInfo`**: test if data in the response is valid under expected preconditions
        + _Preconditions_:
            + The user is logged in
            + Valid parameters are provided
        + _Test conditions:
            + The response contains successCode `200`
			+ `firstName`
				+ *Only contains lower-case and upper-case alphabetic characters
				+ *Contains correct user's first name*
			+ `lastName`
				+ *Only contains lower-case and upper-case alphabetic characters*
    + **`testGetUserInfoUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The user is not logged in
            + Valid parameters are provided
        + _Pass conditions_:
            + The response contains an error code along with a message indicate the request is unauthorized
    + **`testGetUserInfoInvalidUserId`**: test if the server behaves as expected when an invalid `userId `is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `userId` is provided (`userId` is not contained in the database, or `userId` is in invalid format)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate an invalid `userId` provided

updateUserInfo (`updateFirstName`, `updateLastName`, `updateBirthday`, `updatePhoneNumber`)
---
- **Description:** update information of a user base on the corresponding parameter and a given userId
- **Security/Caller:** userId
- **Request:** updateFirstName(userId, firstName)/ updateLastName(userId, lastName)/ updateBirthday(userId, birthday)/ updatePhoneNum(userId, phoneNum)
- **Response:**
    + *Success:*
        + SuccessCode
    + *Error:*
        + errorCode
- **Tests:**
    + **`testUpdateUserInfo`**: test if the user's information in the database is changed according to the new information under expected preconditions
        + _Preconditions_:
            + The user is logged in
            + Valid parameters are provided
        + _Pass conditions_:
            + The response indicates the request is success
            + The user's information in the data is changed according the newly provided information
    + **`testUpdateUserInfoUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The user is not logged in
            + Valid parameters are provided
        + _Pass conditions_:
            + The response contains an error code along with a message indicate the request is unauthorized
    + **`testUpdateUserInfoInvalidId`**: test if the the server behaves as expected when an invalid `userId` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `userId` is provided (`userId` is not contained in the database, or `userId` is in invalid format)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate an invalid `userId` was provided
            + The information about the user in the database is not changed
    + **`testUpdateUserInfoInvalidFirstname`**: test if the the server behaves as expected when an invalid `firstName` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `firstName` is provided (`firstName` is in invalid format)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate an invalid `firstName` was provided
            + The information about the user in the database is not changed
    + **`testUpdateUserInfoInvalidLastname`**: test if the the server behaves as expected when an invalid `lastName` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `lastName` is provided (`lastName` is in invalid format)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate an invalid `lastName` was provided
            + The information about the user in the database is not changed
    + **`testUpdateUserInfoInvalidBirthday`**: test if the the server behaves as expected when an invalid `birthDay` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `birthDay` is provided (`birthday` is in the future, or `birthday` is in invalid format)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate an invalid `birthDay` was provided
            + The information about the user in the database is not changed
    + **`testUpdateUserInfoInvalidPhoneNumber`**: test if the the server behaves as expected when an invalid `phoneNumber` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `phoneNumber` is provided (`phoneNumber` is in invalid format)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate an invalid `phoneNumber` was provided
            + The information about the user in the database is not changed

`getUserBooking`
---
- **Description:** get all booking of the user by a given userId in particular cityId and date
- **Security/Caller:** userId
- **Request:** getUserBookingInCity(userId, cityId, date)
- **Response:**
    + *Success:*
        + SuccessCode
        + userBookingArray (where userBooking structure contains these attributes: bookingId, courtId, startTime, endTime, state)
    + *Error:*
        + errorCode
- **Tests:**
    + **`testGetUserBooking`**: test if data in the response is valid under expected preconditions
        + _Preconditions_:
            + The user is logged in
            + Valid parameters are provided
        + _Pass conditions_:
            + The response indicates the request is success
            + The response contains the list of all the bookings, made by the user, in structured format
            + Each booking contains correct information about itself
    + **`testGetUserBookingUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The user is not logged in
            + Valid parameters are provided
        + _Pass conditions_:
            + The response contains an error code along with a message indicate the request is unauthorized
    + **`testGetUserBookingInvalidUserId`**: test if the server behaves as expected when an invalid `userId` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `userId` is provided (`userId` is not contained in the database, or `userId` is in invalid format)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate an invalid `userId` was provided
    + **`testGetUserBookingInvalidCityId`**: test if the server behaves as expected when an invalid `cityId` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `cityId` is provided (`cityId` is not contained in the database, or `cityId` is in invalid format)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate an invalid `cityId` was provided
    + **`testGetUserBookingInvalidDate`**: test if the server behaves as expected when an invalid `date` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `date` is provided (`date` is in invalid format)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate an invalid `date` was provided

`loginUser` (NO SPECS. YET)
---
- **Description:** authenticate the user by the given username and password
- **Security/Caller:** anonymous
- **Request:** loginUser(username, password)
- **Response:**
    + *Success:*
        + SuccessCode
    + *Error:*
        + errorCode
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

`logoutUser` (NO SPECS. YET)
---
- **Description:** unauthenticate the user
- **Security/Caller:** userId
- **Request:** logoutUser(userId)
- **Response:**
    + *Success:*
        + SuccessCode
    + *Error:*
        + errorCode
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

