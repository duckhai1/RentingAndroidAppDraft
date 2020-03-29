These interfaces have not been clearly specified
---

`loginStaff`
---
- **Description:** authenticate the staff by the given username and password
- **Security/Caller:** anonymous
- **Request:** loginStaff(username, password)
- **Response:**
    + *Success:*
        + `successCode`
    + *Error:*
        + `errorCode`
- **Tests:**
    + **`testLoginStaff`**: test if the staff is authenticated under expected preconditions
        + _Preconditions_:
            + The staff has an account
            + Valid parameters are provided
        + _Pass conditions_:
            + The response indicates the request is success
            + The staff is authenticated
            + The staff can access information that he/she is authorized to
    + **`testLoginStaffUnknown`**: test if the server behaves as expected when invalid credentials are provided
        + _Preconditions_:
            + Invalid credentials are provided (username-password pair is not contained in the database)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate a fail login attempt was made
            + The staff is not authenticated
    + **`testLoginStaffInvalidUsername`**: test if the server behaves as expected when an invalid `username` is provided
        + _Preconditions_:
            + Invalid `username` is provided (`username` is in invalid format)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate an invalid `username` was provided
            + The staff is not authenticated
    + **`testLoginStaffInvalidPassword`**: test if the server behaves as expected when an invalid `password` is provided
        + _Preconditions_:
            + Invalid `password` is provided (`password` is in invalid format)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate an invalid `password` was provided
            + The staff is not authenticated

`logoutStaff`
---
- **Description:** unauthenticate the staff
- **Security/Caller:** staffId
- **Request:** logoutStaff(staffId)
- **Response:**
    + *Success:*
        + `successCode`
    + *Error:*
        + `errorCode`
- **Tests:**
    + **`testLogoutStaff`**: test if the staff is unauthenticated under expected preconditions
        + _Preconditions_:
            + The staff is logged in
            + Valid parameters are provided
        + _Pass conditions_:
            + The response indicates the request is success
            + The staff is unauthenticated
    + **`testLogoutStafUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The staff is not logged in
            + Valid parameters are provided
        + _Pass conditions_:
            + The response contains an error code along with a message indicate the request is unauthorized
    + **`testLogoutStaffInvalidId`**: test if the server behaves as expected when an invalid `staffId` is provided
        + _Preconditions_:
            + The staff is logged in
            + Invalid `staffId` is provided (`staffId` is not contained in the database, or `userId` is in invalid format)
        + _Pass conditions_:
            + The response contains an error code along with an error message indicate an invalid `staffId` was provided
            + The staff is not unauthenticated
