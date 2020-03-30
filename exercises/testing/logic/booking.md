`getCityList`
---
- **Description**: get all city names and IDs found in the data-tier
- **Security/Caller:**: `userId`
- **Request**: `getCityList()`
- **Response**:
    + *On success:*
        + `successCode`
        + `Cities[0..*]`
    + *On error:*
        + ``errorCode``
- **Tests:**
    + **`testGetCityList`**: test if the request is accepted and the data in the response is valid when `caller` is valid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
        + _Pass conditions_:
            + `successCode` is equal to *200 - SUCCESS*
            + Number of elements in `Cities` is equal to the number of cities in the data-tier
            + Each elements in `Cities`, is a tuple `(cityId, cityName)` which exists in the data returned to the logic-tier from the data-tier, where:
                + `cityName` contains only alphabetic characters and spaces
    + **`testGetCityListUnauthorized`**: test if the request is rejected when `caller` is invalid
        + _Preconditions_:
            + `userId` does not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *401 - UNAUTHORIZED*

`getAvailableSlots`
---
- **Description:** get all available slots, computed from the data found in the data-tier, for a given `date` and `cityId`
- **Security/Caller:** `userId`
- **Request:** `getAvailableSlots(cityId, date)`
- **Response:**
    + *Success:*
        + `successCode`
        + `Slots[0..*]`
    + *Error:*
        + ``errorCode``
- **Tests:**
    + **`testGetAvailableSlots`**: test if the request is accepted and the data in the response is valid when the `caller` is valid and all the parameters are valid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `cityId` exists in the data returned to the logic-tier from the data-tier
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND** `date` comes after the date when the interface is called
        + _Pass conditions_:
            + `successCode` is equal to *200 - SUCCESS*
            + Number of elements in `Slots` is equal to the number of slots in the data-tier
            + Each element in `Slots`, is a tuple `(sportCentreId, courtId, startTime, endTime)` which exists in the data returned to the logic-tier from the data-tier, where:
                + `sportCentreId` exists in the data returned to the logic-tier from the data-tier
                + `courtId` exists in the data returned to the logic-tier from the data-tier
                `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by ":" *(colon)*,, and ordered as given **AND** `startTime` comes before `endTime` **AND** `startTime` comes after the time when the interface is called
    + **`testGetAvailableSlotsUnauthorized`**: test if the request is rejected when `caller` is invalid
        + _Preconditions_:
            + `userId` does not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *401 - UNAUTHORIZED*
    + **`testGetAvailableSlotsInvalidDate`**: test if the request is rejected when `caller` is valid and `date` is invalid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `date` is not formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **OR** `date` comes before the date when the interface is called
        + _Pass conditions_:
            + ``errorCode`` is equal to *463 - INVALID DATE*
    + **`testGetAvailableSlotsInvalidCity`**: test if the request is rejected when `caller` is valid and `cityId` is invalid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `cityId` doest not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *461 - INVALID CITY ID*

`getBookingInfo`
---
- **Description**: get all information of a booking for a given `bookingId`
- **Security/Caller:** `staffId`/`userId`
- **Request**: `getBookingInfo(bookingId)`
- **Response**:
    + *Success:*
        + `successCode`
        + `booking`
    - *Error:*
        + ``errorCode``
- **Tests:**
    + **`testGetBookingInfo`**: test if the request is accepted and the data in the response is valid when `caller` is valid and all the parameters are valid
        + _Preconditions_:
            + `staffId` or `userId` exists in the data returned to the logic-tier from the data-tier
            + `bookingId` exists in the data returned to the logic-tier from the data-tier
        + _Pass conditions_:
            + `successCode` is equal to *200 - SUCCESS*
            + `booking` is a tuple `(bookingId, userId, sportCentreId, courtId, date, startTime, endTime, state)` which exists in the data returned to the logic-tier from the data-tier, where:
                + `bookingId` uniquely identify the booking in the data-tier
                + `userId` exists in the data returned to the logic-tier from the data-tier
                + `sportCentreId` exists in the data returned to the logic-tier from the data-tier
                + `courtId` exists in the data returned to the logic-tier from the data-tier
                + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given
                + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by ":" *(colon)*,, and ordered as given **AND** `startTime` comes before `endTime`
                + `state` is equal to one of the following: `UPCOMING`, `UNPAID`, `PAID`, `CANCELLED`
    + **`testGetBookingInfoUnauthorized`**: test if the request is rejected when `caller` is invalid
        + _Preconditions_:
            + `staffId` or `userId` does not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *401 - UNAUTHORIZED*
    + **`testGetBookingInfoInvalidBookingId`**: test if the request is rejected when `caller` is valid and `bookingId` is invalid
        + _Preconditions_:
            + `staffId` or `userId` exists in the data returned to the logic-tier from the data-tier
            + `bookingId` does not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *410 - BOOKING NOT FOUND*

`makeBooking`
---
- **Description:** create a booking from the given `userId`, `courtId`, `date`, `startTime`, and `endTime`
- **Security/Caller:** `userId`
- **Request:** `makeBooking(courtId, date, startTime, endTime)`
- **Response:**
    + *Success:*
        + `successCode`
        + `bookingId`
    + *Error:*
        + ``errorCode``
- **Tests:**
    + **`testMakeBooking`**: test if the request is accepted and a booking is created when `caller` is valid and all the parameters are valid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `courtId` exists in the data returned to the logic-tier from the data-tier
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND**  `date` comes after the date when the interface is called
            + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by ":" *(colon)*,, and ordered as given **AND** `startTime` comes before `endTime` **AND** `startTime` comes after the time when the interface is called
        + _Pass conditions_:
            + `successCode` is equal to *202 - CREATED*
            + `bookingId` is the unique identifier for the booking which was created by the server; this can be used to find details information about the booking
            + *Side-effect:* a tuples and relations related the booking are created in the data-tier
    + **`testMakeBookingInvalidSlot`**: test if the request is rejected when the `caller` is valid and the *slot* is invalid, i.e., the combination of `courtId`, `date`, `startTime`, and `endTime` is invalid; this can happen when the slot information on the client is staled
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `courtId` exists in the data returned to the logic-tier from the data-tier
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND**  `date` comes after the date when the interface is called
            + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by ":" *(colon)*,, and ordered as given **AND** `startTime` comes before `endTime` **AND** `startTime` comes after the time when the interface is called
            + The available slots computed by the server does not contains the given slot
        + _Pass conditions_:
            + ``errorCode`` is equal to *464 - INVALID SLOT*
    + **`testMakeBookingPendingPayment`**: test if the request is rejected when the `caller` is valid and the user has past unpaid payment
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `courtId` exists in the data returned to the logic-tier from the data-tier
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND**  `date` comes after the date when the interface is called
            + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by ":" *(colon)*,, **AND**and ordered as given **AND** `startTime` comes before `endTime` **AND** `startTime` comes after the time when the interface is called
            + The user has a booking in the past that has not been paid
        + _Pass conditions_:
            + ``errorCode`` is equal to *413 - UNPAID BOOKING FOUND*
    + **`testMakeBookingLimitExceed`**: test if the server behaves as expected when the user has already had 3 future bookings
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `courtId` exists in the data returned to the logic-tier from the data-tier
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND**  `date` comes after the date when the interface is called
            + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by ":" *(colon)*,, and ordered as given **AND** `startTime` comes before `endTime` **AND** `startTime` comes after the time when the interface is called
            + The user has already had three upcoming bookings
        + _Pass conditions_:
            + ``errorCode`` is equal to *- BOOKINGS LIMIT REACHED*
    + **`testMakeBookingUnauthorized`**:  test if the request is rejected when `caller` is invalid
         + _Preconditions_:
            + `userId` does not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *401 - UNAUTHORIZED*
    + **`testMakeBookingInvalidCourtId`**:  test if the request is rejected when `caller` is valid and `courtId` is invalid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `courtId` doest not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *- INVALID COURT ID*
    + **`testMakeBookingInvalidDate`**: test if the request is rejected when `caller` is valid and `date` is invalid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `date` is not formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, **OR** not ordered as given **OR**  `date` comes after the date when the interface is called
        + _Pass conditions_:
            + ``errorCode`` is equal to *463 - INVALID DATE*
    + **`testMakeBookingInvalidDuration`**: test if the server behaves as expected when the request contains an invalid duration (`endTime - startTime`)
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by ":" *(colon)*,, and ordered as given **AND** `startTime` comes before `endTime` **AND** `startTime` comes after the time when the interface is called
            + The period between `startTime` and `endTime` is not *45 minutes*, *1 hour*, *1 hour and 15 minutes*, or *1 hour and 30 minutes*
        + _Pass conditions_:
            + ``errorCode`` is equal to *465 - INVALID DURATION*
    + **`testMakeBookingInvalidStartTime`**: test if the server behaves as expected when the request contains an invalid `startTime`
        + _Preconditions_:
            + The user is logged in
            + The user does not have a past pending payment nor 3 future bookings
            + `startTime` is not formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by "-" *(hyphens)*, and ordered as given **OR** `startTime` does not come before `endTime` **OR** `startTime` does not come after the time when the interface is called
        + _Pass conditions_:
            + ``errorCode`` is equal to *470 - INVALID START TIME*
    + **`testMakeBookingInvalidEndTime`**: test if the server behaves as expected when the request contains an invalid `endTime`
        + _Preconditions_:
            + The user is logged in
            + The user does not have a past pending payment nor 3 future bookings
            + `endTime` is not formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by "-" *(hyphens)*, and ordered as given
        + _Pass conditions_:
            + ``errorCode`` is equal to *471 - INVALID END TIME*

`changeBookingState`
---
- **Description:** change the state of the booking with the given `bookingId`
- **Security/Caller:** `staffId`
- **Request:** `changeBookingState(bookingId, state)`
- **Response:**
    + *Success:*
        + `successCode`
    + *Error:*
        + ``errorCode``
+ **Tests:**
    + **`testChangeBookingState`**: test if the request is accepted and the state of the booking in the database is changed when `caller` is valid and all the parameters are valid
        + _Preconditions_:
            + `staffId` exists in the data returned to the logic-tier from the data-tier
            + `bookingId` exists in the data returned to the logic-tier from the data-tier
            + `state` is valid
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testChangeBookingStateUnauthorized`**: test if the request is rejected when `caller` is invalid
        + _Preconditions_:
            + `staffId` does not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *401 - UNAUTHORIZED*
    + **`testChangeBookingStateInvalidBookingId`**: test if the request is rejected when `caller` is valid and `bookingId` is invalid
        + _Preconditions_:
            + `staffId` exists in the data returned to the logic-tier from the data-tier
            + `bookingId` does not exist in the data tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *410 - BOOKING NOT FOUND*
    + **`testChangeBookingStateInvalidState`**: test if the request is rejected when `caller` is valid and `state` is invalid
        + _Preconditions_:
            + `staffId` exists in the data returned to the logic-tier from the data-tier
            + `state` is invalid
        + _Pass conditions_:
            + ``errorCode`` is equal to *466 - INVALID STATE*


`cancelBooking`
---
- **Description:** cancel the booking with the given `bookingId`
- **Security/Caller:** `userId`
- **Request:** `cancelBooking(bookingId)`
- **Response:**
    + *Success:*
        + `successCode`
    + *Error:*
        + ``errorCode``
- **Tests:**
    + **`testCancelBooking`**:  test if the request is accepted and booking tuple in the database is removed when `caller` is valid and all the parameters are valid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `bookingId` exists in the data returned to the logic-tier from the data-tier
            + The request is made in more than 24 hours before the start time of the booking
        + _Pass conditions_:
            + `successCode` is equal to *200 - SUCCESS*
    + **`testCancelBookingUnauthorized`**: test if the request is rejected when `caller` is invalid
        + _Preconditions_:
            + `userId` doest not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *401 - UNAUTHORIZED*
    + **`testCancelBookingLate`**: test is the request is rejected when `caller` is valid and the time instance, when the request is made, is not 24 hours before the `startTime` of the booking
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `bookingId` exists in the data returned to the logic-tier from the data-tier
            + The request is made in less than 24 hours before the start time of the booking
        + _Pass conditions_:
            + ``errorCode`` is equal to *412 - BOOKING CANCELLATION REJECTED*
    + **`testCancelBookingInvalidId`**: test if the request is rejected when `caller` is valid and `bookingId` is invalid
        + _Preconditions_:
            + `userId` exists in the data returned to the logic-tier from the data-tier
            + `bookingId` does not exist in the data-tier
        + _Pass conditions_:
            + ``errorCode`` is equal to *410 - BOOKING NOT FOUND*
