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