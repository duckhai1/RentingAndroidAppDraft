Possible test cases for the interfaces between the presentation-tier and the logic-tier
===

Notes
---
+ The *validity* of each parameter is checked different and **has not** been discussed
+ APIs that require authentication will also be tested, to avoid repetition, those APIs will be tested **similarly** under the following conditions:

`loginUser`
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
		+ _Expected outcome_:
			+ The response indicates the request is success
			+ The user is authenticated
			+ The user can access information that he/she is authorized to
	+ **`testLoginUserUnknown`**: test if the servers behaves as expected when invalid credentials are provided
		+ _Preconditions_:
			+ Invalid credentials are provided (username-password pair is not contained in the database)
		+ _Expected outcome_:
            + The response contains an error code along with an error message indicate a fail login attempt was made
			+ The user is not authenticated
	+ **`testLoginUserInvalidUsername`**: test if the server behaves as expected when an invalid `username` is provided
		+ _Preconditions_:
			+ Invalid `username` is provided (`username` is in invalid format)
		+ _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `username` was provided
			+ The user is not authenticated
	+ **`testLoginUserInvalidPassword`**: test if the server behaves as expected when an invalid `password` is provided
		+ _Preconditions_:
			+ Invalid `password` is provided (`password` is in invalid format)
		+ _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `password` was provided
			+ The user is not authenticated

`logoutUser`
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
		+ _Expected outcome_:
			+ The response indicates the request is success
			+ The user is unauthenticated
    + **`testLogoutUserUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The user is not logged in
            + Valid parameters are provided
        + _Expected outcome_:
            + The response contains an error code along with a message indicate the request is unauthorized
	+ **`testLogoutUserInvalidId`**: test if the server behaves as expected when an invalid `userId` is provided
		+ _Preconditions_:
			+ The user is logged in
			+ Invalid `userId` is provided (`userId` is not contained in the database, or `userId` is in invalid format) (`userId` is not contained in the database, or `userId` is in invalid format)
		+ _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `userId` was provided
			+ The user is not unauthenticated

`loginStaff`
---
- **Description:** authenticate the staff by the given username and password
- **Security/Caller:** anonymous
- **Request:** loginStaff(username, password)
- **Response:**
    + *Success:*
        + SuccessCode
    + *Error:*
        + errorCode
- **Tests:**
	+ **`testLoginStaff`**: test if the staff is authenticated under expected preconditions
		+ _Preconditions_:
			+ The staff has an account
			+ Valid parameters are provided
		+ _Expected outcome_:
			+ The response indicates the request is success
			+ The staff is authenticated
			+ The staff can access information that he/she is authorized to
	+ **`testLoginStaffUnknown`**: test if the server behaves as expected when invalid credentials are provided
		+ _Preconditions_:
			+ Invalid credentials are provided (username-password pair is not contained in the database)
		+ _Expected outcome_:
            + The response contains an error code along with an error message indicate a fail login attempt was made
			+ The staff is not authenticated
	+ **`testLoginStaffInvalidUsername`**: test if the server behaves as expected when an invalid `username` is provided
		+ _Preconditions_:
			+ Invalid `username` is provided (`username` is in invalid format)
		+ _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `username` was provided
			+ The staff is not authenticated
	+ **`testLoginStaffInvalidPassword`**: test if the server behaves as expected when an invalid `password` is provided
		+ _Preconditions_:
			+ Invalid `password` is provided (`password` is in invalid format)
		+ _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `password` was provided
			+ The staff is not authenticated

`logoutStaff`
---
- **Description:** unauthenticate the staff
- **Security/Caller:** staffId
- **Request:** logoutStaff(staffId)
- **Response:**
    + *Success:*
        + SuccessCode
    + *Error:*
        + errorCode
- **Tests:**
	+ **`testLogoutStaff`**: test if the staff is unauthenticated under expected preconditions
		+ _Preconditions_:
			+ The staff is logged in
			+ Valid parameters are provided
		+ _Expected outcome_:
			+ The response indicates the request is success
			+ The staff is unauthenticated
    + **`testLogoutStafUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The staff is not logged in
            + Valid parameters are provided
        + _Expected outcome_:
            + The response contains an error code along with a message indicate the request is unauthorized
	+ **`testLogoutStaffInvalidId`**: test if the server behaves as expected when an invalid `staffId` is provided
		+ _Preconditions_:
			+ The staff is logged in
			+ Invalid `staffId` is provided (`staffId` is not contained in the database, or `userId` is in invalid format)
		+ _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `staffId` was provided
			+ The staff is not unauthenticated

`getCityList`
---
- **Description**: get all city name and ID
- **Security/Caller:**: userId
- **Request**: getCityList()
- **Response**:
    + *Success:*
        + SuccessCode
        + City array (where City structure contains these attributes: (cityId, cityName)
    + *Error:*
        + errorCode
- **Tests:**
	+ **`testGetCityList`**: test if data in the response is valid under expected preconditions
        + _Preconditions_:
            + The user is logged in
		+ _Expected outcome_:
            + The response indicates the request is success
            + The response contains the list of all cities, structured in the correct format
    + **`testGetCityListUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The user is not logged in
            + Valid parameters are provided
        + _Expected outcome_:
            + The response contains an error code along with a message indicate the request is unauthorized

`getAvailableSlots`
---
- **Description:** get all available slot for a given date and cityId
- **Security/Caller:** userId
- **Request:** getAvailableSlots(date, cityId)
- **Response:**
    + *Success:*
        + SuccessCode
        + slotArray (where Slot structure contains these attributes: sportCenterId, courtId, startTime, endTime)
    + *Error:*
        + errorCode
- **Tests:**
	+ **`testGetAvailableSlots`**: test if data in the response is valid under expected preconditions
        + _Preconditions_:
            + The user is logged in
            + Valid parameters are provided
		+ _Expected outcome_:
            + The response indicates the request is success
            + The response contains the list of all the available slots structured in the correct format
            + Each slot contains correct information about itself
    + **`testGetAvailableSlotsUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The user is not logged in
            + Valid parameters are provided
        + _Expected outcome_:
            + The response contains an error code along with a message indicate the request is unauthorized
	+ **`testGetAvailableSlotsInvalidDate`**: test if the server behaves as expected when invalid `date` was provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `date` is provided (`date` has passed, or `date` is in invalid format)
		+ _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `date` was provided
    + **`testGetAvailableSlotsInvalidCity`**: test if the server behaves as expected when invalid `cityId` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `cityId` is provided (`cityId` is not contained in the database, or `cityId` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `cityId` was provided


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
		+ _Expected outcome_:
            + The response indicates the request is success
            + The response contains the list of all the bookings, made by the user, in structured format
            + Each booking contains correct information about itself
    + **`testGetUserBookingUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The user is not logged in
            + Valid parameters are provided
        + _Expected outcome_:
            + The response contains an error code along with a message indicate the request is unauthorized
	+ **`testGetUserBookingInvalidUserId`**: test if the server behaves as expected when an invalid `userId` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `userId` is provided (`userId` is not contained in the database, or `userId` is in invalid format)
		+ _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `userId` was provided
    + **`testGetUserBookingInvalidCityId`**: test if the server behaves as expected when an invalid `cityId` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `cityId` is provided (`cityId` is not contained in the database, or `cityId` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `cityId` was provided
    + **`testGetUserBookingInvalidDate`**: test if the server behaves as expected when an invalid `date` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `date` is provided (`date` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `date` was provided

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
		+ _Expected outcome_:
            + The response indicates the request is success
            + The response contains the list of all the bookings, made by the user with the sport centre, in structured format
            + Each booking contains correct information about itself
    + **`testGetSportCenterBookingUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The staff is not logged in
            + Valid parameters are provided
        + _Expected outcome_:
            + The response contains an error code along with a message indicate the request is unauthorized
    + **`testGetSportCenterBookingInvalidId`**: test if the server behaves as expected when an invalid `sportCenterId` is provided
        + _Preconditions_:
            + The staff is logged in
            + Invalid `sportCenterId` is provided (`sportCenterId` is not contained in the database, or `sportCenterId` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `sportCenterId` was provided
    + **`testGetSportCenterBookingInvalidDate`**: test if the server behaves as expected when an invalid `date` is provided
        + _Preconditions_:
            + The staff is logged in
            + Invalid `date` is provided (`date` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `date` was provided

`getUserInfo`
---
- **Description:** get all information of the user by a given userId
- **Security/Caller:** userId
- **Request:** getUserInfo(userId)
- **Response:**
    + *Success:*
        + SuccessCode
        + userInfo (where userInfo structure contains these attributes: firstName, lastName, dateOfBirth, phoneNum)
    + *Error:*
        + errorCode
- **Tests:**
	+ **`testGetUserInfo`**: test if data in the response is valid under expected preconditions
        + _Preconditions_:
            + The user is logged in
            + Valid parameters are provided
		+ _Expected response_:
            + The response indicates the request is success
            + The response contains requested information about the user
    + **`testGetUserInfoUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The user is not logged in
            + Valid parameters are provided
        + _Expected outcome_:
            + The response contains an error code along with a message indicate the request is unauthorized
	+ **`testGetUserInfoInvalidUserId`**: test if the server behaves as expected when an invalid `userId `is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `userId` is provided (`userId` is not contained in the database, or `userId` is in invalid format)
		+ _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `userId` provided

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
        + _Expected outcome_:
            + The response contains an error code along with a message indicate the request is unauthorized
	+ **`testGetSportCenterInfoInvalidSportCenterId`**: test if the server behaves as expected when an invalid `sportCenterId` is provided
        + _Preconditions_:
            + The staff is logged in
            + Invalid `sportCenterId` is provided (`sportCenterId` is not contained in the database, or `sportCenterId` is in invalid format)
		+ _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `sportCenterId` is provided

`getBookingInfo`
---
- **Description**: display all information of booking by given bookingId
- **Security/Caller:** staffId/userId
- **Request**: getBookingInfo(bookingId)
- **Response**:
    + *Success:*
        + SuccessCode
        + booking (where booking structure contains these attributes userId, sportCenterId, courtId, date startTime, endTime, state)
    - *Error:*
        + errorCode
- **Tests:**
	+ **`testGetBookingInfo`**: test if the data in the the response is valid under expected preconditions
        + _Preconditions_:
            + The staff/user is logged in
            + Valid parameters are provided
		+ _Expected outcome_:
            + The response indicates the request is success
            + The response contains requested information about the booking
    + **`testGetBookingInfoUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The user/staff is not logged in
            + Valid parameters are provided
        + _Expected outcome_:
            + The response contains an error code along with a message indicate the request is unauthorized
	+ **`testGetBookingInfoInvalidBookingId`**: test if the server behaves as expected when an invalid `bookingId` is provided
        + _Preconditions_:
            + The staff/user is logged in
            + Invalid `bookingId` is provided (`bookingId` is not contained in the database, or `bookingId` is in invalid format)
		+ _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `bookingId` is provided

`makeBooking`
---
- **Description:** make a booking by a given userId, courtId, date, startTime, endTime
- **Security/Caller:** userId
- **Request:** makeBooking(userId, courtId, date, startTime, endTime)
- **Response:**
    + *Success:*
        + SuccessCode
    + *Error:*
        + errorCode
- **Tests:**
	+ **`testMakeBooking`**: test if the booking is made under expected preconditions
		+ _Preconditions_:
			+ The user is logged in
            + The user does not have a past pending payment nor 3 future bookings
			+ Valid parameters are provided
		+ _Expected outcome_:
            + A response indicates the request is success
            + A new record containing the booking information is stored in the database
    + **`testMakeBookingUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The user is not logged in
            + Valid parameters are provided
        + _Expected outcome_:
            + The response contains an error code along with a message indicate the request is unauthorized
    + **`testMakeBookingInvalidSlot`**: test if the server behaves as expected when another booking has been made, which affects the available time slots
        + _Preconditions_:
            + The user is logged in
            + The user does not have a past pending payment nor 3 future bookings
            + Valid parameters are provided
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate the time slot is invalid
            + A new record containing the booking information is not stored in the database
	+ **`testMakeBookingPendingPayment`**: test if the server behaves as expected when the user has past unpaid payment
		+ _Preconditions_:
			+ The user is logged in
			+ The user has past pending payment(s)
			+ Valid parameters are provided
		+ _Expected outcome_:
            + A response contains an error code along with an error message indicate the user has past pending payment
            + A new record containing the booking information is not stored in the database
	+ **`testMakeBookingLimitExceed`**: test if the server behaves as expected when the user has already had 3 future bookings
		+ _Preconditions_:
			+ The user is logged in
			+ The use has 3 future bookings
			+ Valid parameters are provided
		+ _Expected outcome_:
            + The response contains an error code along with an error message indicate the user has already had 3 future bookings
            + A new record containing the booking information is not stored in the database
    + **`testMakeBookingInvalidUserId`**: test if the server behaves as expected when the request contains an invalid `userId`
        + _Preconditions_:
            + The user is logged in
            + The user does not have a past pending payment nor 3 future bookings
            + Invalid `userId` is provided (`userId` is not contained in the database, or `userId` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `userId` was provided
            + A new record containing the booking information is not stored in the database
    + **`testMakeBookingInvalidCourId`**: test if the server behaves as expected when the request contains an invalid `courtId`
        + _Preconditions_:
            + The user is logged in
            + The user does not have a past pending payment nor 3 future bookings
            + Invalid `courtId` is provided (`courtId` is not contained in the database, or `courtId` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `courtId` was provided
            + A new record containing the booking information is not stored in the database
    + **`testMakeBookingInvalidDate`**: test if the server behaves as expected when the request contains an invalid `date`
        + _Preconditions_:
            + The user is logged in
            + The user does not have a past pending payment nor 3 future bookings
            + Invalid `date` is provided (`date` has passed, or `date` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `date` was provided
            + A new record containing the booking information is not stored in the database
    + **`testMakeBookingInvalidDuration`**: test if the server behaves as expected when the request contains an invalid duration (`endTime - startTime`)
        + _Preconditions_:
            + The user is logged in
            + The user does not have a past pending payment nor 3 future bookings
            + Invalid time duration provided (time duration is not *45 minutes*, *60 minutes*, *75 minutes*, or *90 minutes*)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `startTime` was provided
            + A new record containing the booking information is not stored in the database
    + **`testMakeBookingInvalidStartTime`**: test if the server behaves as expected when the request contains an invalid `startTime`
        + _Preconditions_:
            + The user is logged in
            + The user does not have a past pending payment nor 3 future bookings
            + Invalid `startTime` provided (`startTime` is not in the operating hours, or `startTime` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `startTime` was provided
            + A new record containing the booking information is not stored in the database
    + **`testMakeBookingInvalidEndTime`**: test if the server behaves as expected when the request contains an invalid `endTime`
        + _Preconditions_:
            + The user is logged in
            + The user does not have a past pending payment nor 3 future bookings
            + Invalid `endTime` provided (`endTime` is not in the operating hours, or `endTime` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `endTime` was provided
            + A new record containing the booking information is not stored in the database


`cancelBooking`
---
- **Description:** cancel a booking by a given bookingId and sportCenterId
- **Security/Caller:** userId
- **Request:** cancelBooking(bookingId)
- **Response:**
    + *Success:*
        + SuccessCode
    + *Error:*
        + errorCode
- **Tests:**
    + **`testCancelBooking`**: test if the cancellation is accepted under expected preconditions
        + _Preconditions_:
            + The user is logged in
            + The user made booking(s) in the past
            + The request is made 24 hours before the start time of the booking
            + Valid parameters are provided
        + _Expected outcome_:
            + The response indicates the request is success
            + The state of the booking in the database is changed to `Cancelled`
    + **`testCancelBookingUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The user is not logged in
            + Valid parameters are provided
        + _Expected outcome_:
            + The response contains an error code along with a message indicate the request is unauthorized
    + **`testCancelBookingLate`**: test if the server behaves as expected when a cancellation is made 24 hours before the start time of the booking
        + _Preconditions_:
            + The user is logged in
            + The user made booking(s) in the past
            + The request is made less than 24 hours before the start time of the booking
            + Valid parameters are provided
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid the booking can not be cancelled
            + The state of the booking in the database is not changed
    + **`testCancelBookingInvalidId`**: test if the server behaves as expected when an invalid `bookingId` is provided
        + _Preconditions_:
            + The user is logged in
            + The user made booking(s) in the past
            + The request is made less than 24 hours before the start time of the booking
            + Invalid `bookingId` is provided (`bookingId` is not contained in the database, or `bookingId` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `bookingId` was provided
            + The state of the booking in the database is not changed

`changeBookingState`
---
- **Description:** change a booking state by a given bookingId
- **Security/Caller:** staffId
- **Request:** changeBookingState(bookingId, state)
- **Response:**
    + *Success:*
        + SuccessCode
    + *Error:*
        + errorCode
+ **Tests:**
    + **`testChangeBookingState`**: test if the booking state in the database is changed under expected preconditions
        + _Preconditions_:
            + The staff is logged in
            + Valid parameters are provided
        + _Expected outcome_:
            + The response indicates the request is success
            + The state of the booking in the database is changed to the provided `state`
    + **`testChangeBookingStateUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The staff is not logged in
            + Valid parameters are provided
        + _Expected outcome_:
            + The response contains an error code along with a message indicate the request is unauthorized
    + **`testChangeBookingStateInvalidBookingId`**: test if the server behaves as expected when an invalid `bookingId` is provided
        + _Preconditions_:
            + The staff is logged in
            + Invalid `bookingId` is provided (`bookingId` is not contained in the database, or `bookingId` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `bookingId` was provided
            + The state of the booking in the database is not changed
    + **`testChangeBookingStateInvalidState`**: test if the server behaves as expected when an invalid `state` is provided
        + _Preconditions_:
            + The staff is logged in
            + Invalid `state` is provided (`state` does not exist, or `state` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `state` was provided
            + The state of the booking in the database is not changed

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
        + _Expected outcome_:
            + The response indicates the request is success
            + The user's information in the data is changed according the newly provided information
    + **`testUpdateUserInfoUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The user is not logged in
            + Valid parameters are provided
        + _Expected outcome_:
            + The response contains an error code along with a message indicate the request is unauthorized
    + **`testUpdateUserInfoInvalidId`**: test if the the server behaves as expected when an invalid `userId` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `userId` is provided (`userId` is not contained in the database, or `userId` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `userId` was provided
            + The information about the user in the database is not changed
    + **`testUpdateUserInfoInvalidFirstname`**: test if the the server behaves as expected when an invalid `firstName` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `firstName` is provided (`firstName` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `firstName` was provided
            + The information about the user in the database is not changed
    + **`testUpdateUserInfoInvalidLastname`**: test if the the server behaves as expected when an invalid `lastName` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `lastName` is provided (`lastName` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `lastName` was provided
            + The information about the user in the database is not changed
    + **`testUpdateUserInfoInvalidBirthday`**: test if the the server behaves as expected when an invalid `birthDay` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `birthDay` is provided (`birthday` is in the future, or `birthday` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `birthDay` was provided
            + The information about the user in the database is not changed
    + **`testUpdateUserInfoInvalidPhoneNumber`**: test if the the server behaves as expected when an invalid `phoneNumber` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `phoneNumber` is provided (`phoneNumber` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `phoneNumber` was provided
            + The information about the user in the database is not changed


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
        + _Expected outcome_:
            + The response indicates the request is success
            + The user's information in the data is changed according the newly provided information
    + **`testUpdateSportCenterInfoUnauthorized`**: test if the server behaves as expected when receives unauthorized request
        + _Preconditions_:
            + The staff is not logged in
            + Valid parameters are provided
        + _Expected outcome_:
            + The response contains an error code along with a message indicate the request is unauthorized
    + **`testUpdateSportCenterInfoInvalidId`**: test if the the server behaves as expected when an invalid `sportCenterId` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `sportCenterId` is provided (`sportCenterId` is not contained in the database, or `sportCenterId` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `sportCenterId` was provided
            + The information about the user in the database is not changed
    + **`testUpdateSportCenterInfoInvalidName`**: test if the the server behaves as expected when an invalid `name` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `name` is provided (`name` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `name` was provided
            + The information about the user in the database is not changed
    + **`testUpdateSportCenterInfoInvalidCity`**: test if the the server behaves as expected when an invalid `cityId` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `cityId` is provided (`cityId` is not contained in the database, or `cityId` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `cityId` was provided
            + The information about the user in the database is not changed
    + **`testUpdateSportCenterInfoInvalidPhoneNumber`**: test if the the server behaves as expected when an invalid `phoneNumber` is provided
        + _Preconditions_:
            + The user is logged in
            + Invalid `phoneNumber` is provided (`phoneNumber` is in invalid format)
        + _Expected outcome_:
            + The response contains an error code along with an error message indicate an invalid `phoneNumber` was provided
            + The information about the user in the database is not changed


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
