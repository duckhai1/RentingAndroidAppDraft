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
        + `errorCode`
- **Tests:**
    + **`testGetCityList`**: test if data in the response is valid when `caller` is valid
        + _Preconditions_:
            + `userId` exists in the data-tier
        + _Pass conditions_:
            + `successCode` is equal to *200 - SUCCESS*
            + Number of elements in `Cities` is equal to the number of cities in the data-tier
            + Each elements in `Cities`, is a tuple `(cityId, cityName)` which exists in the data-tier
    + **`testGetCityListUnauthorized`**: test if the request is rejected when `caller` is invalid
        + _Preconditions_:
            + `userId` does not exist in the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *- UNAUTHORIZED*

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
        + `errorCode`
- **Tests:**
    + **`testGetAvailableSlots`**: test if data in the response is valid when the `caller` is valid and all the parameters, which are provided, are valid
        + _Preconditions_:
            + `userId` exists in the data-tier
            + `cityId` exists in the data-tier
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND** `date` comes after the date when the interface is called
        + _Pass conditions_:
            + `successCode` is equal to *200 - SUCCESS*
            + Number of elements in `Slots` is equal to the number of slots in the data-tier
            + Each element in `Slots`, is a tuple `(sportCenterId, courtId, startTime, endTime)` which exists in the data-tier
    + **`testGetAvailableSlotsUnauthorized`**: test if the request is rejected when `caller` is invalid
        + _Preconditions_:
            + `userId` does not exist in the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *- UNAUTHORIZED*
    + **`testGetAvailableSlotsInvalidDate`**: test if the request is rejected when `caller` is valid and `date` is invalid
        + _Preconditions_:
            + `userId` exists in the data-tier
            + `date` is not formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND** `date` comes before the date when the interface is called
        + _Pass conditions_:
            + `errorCode` is equal to *- INVALID DATE*
    + **`testGetAvailableSlotsInvalidCity`**: test if the request is rejected when `caller` is valid and `cityId` is invalid
        + _Preconditions_:
            + `userId` exists in the data-tier
            + `cityId` doest not exist in the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *- INVALID CITY ID*

`getBookingInfo`
---
- **Description**: get all information of a booking for a given `bookingId`
- **Security/Caller:** `staffId`/`userId`
- **Request**: `getBookingInfo(bookingId)`
- **Response**:
    + *Success:*
        + `successCode`
        + `Bookings[0..*]`
    - *Error:*
        + `errorCode`
- **Tests:**
    + **`testGetBookingInfo`**: test if the data in the response is valid when `caller` is valid and all the parameters, which are provided, are valid
        + _Preconditions_:
            + `staffId` or `userId` exists in the data-tier
            + `bookingId` exists in the data-tier
        + _Pass conditions_:
            + `successCode` is equal to *200 - SUCCESS*
            + Number of elements in `Bookings` is equal to the number of bookings in the data-tier
            + Each elements in `Bookings`, is a tuple `(userId, sportCenterId, courtId, date, startTime, endTime, state)` which exists in the data-tier
    + **`testGetBookingInfoUnauthorized`**: test if the request is rejected when `caller` is invalid
        + _Preconditions_:
            + `staffId` or `userId` does not exist in the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *- UNAUTHORIZED*
    + **`testGetBookingInfoInvalidBookingId`**: test if the request is rejected when `caller` is valid and `bookingId` is invalid
        + _Preconditions_:
            + `staffId` or `userId` exists in the data-tier
            + `bookingId` does not exist in the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *- INVALID BOOKING ID*

`makeBooking`
---
- **Description:** create a booking from the given `userId`, `courtId`, `date`, `startTime`, and `endTime`
- **Security/Caller:** `userId`
- **Request:** `makeBooking(userId, courtId, date, startTime, endTime)`
- **Response:**
    + *Success:*
        + `successCode`
        + `bookingId`
    + *Error:*
        + `errorCode`
- **Tests:**
    + **`testMakeBooking`**: test if the request is accepted and a booking is created when `caller` is valid and all the parameters, which are provided, are valid
        + _Preconditions_:
            + `userId` exists in the data-tier
            + `courtId` exists in the data-tier
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND**  `date` comes after the date when the interface is called
            + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by "-" *(hyphens)*, and ordered as given **AND** `startTime` comes before `endTime` **AND** `startTime` comes after the time when the interface is called
        + _Pass conditions_:
            + `successCode` is equal to *200 - SUCCESS*
            + `bookingId` is the unique identifier for the booking which was created by the server; this can be used to find details information about the booking
            + *Side-effect:* a tuples and relations related the booking are created in the data-tier
    + **`testMakeBookingInvalidSlot`**: test if the request is rejected when the `caller` is valid, but the *slot* is invalid, i.e., the combination of `courtId`, `date`, `startTime`, and `endTime` is invalid; this can happen when the slot information on the client is staled
        + _Preconditions_:
            + `userId` exists in the data-tier
            + `courtId` exists in the data-tier
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND**  `date` comes after the date when the interface is called
            + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by "-" *(hyphens)*, and ordered as given **AND** `startTime` comes before `endTime` **AND** `startTime` comes after the time when the interface is called
            + The available slots computed by the server does not contains the given slot
        + _Pass conditions_:
            + `errorCode` is equal to *- INVALID SLOT*
    + **`testMakeBookingPendingPayment`**: test if the request is rejected when the `caller` is valid, but the user has past unpaid payment
        + _Preconditions_:
            + `userId` exists in the data-tier
            + `courtId` exists in the data-tier
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND**  `date` comes after the date when the interface is called
            + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by "-" *(hyphens)*, **AND**and ordered as given **AND** `startTime` comes before `endTime` **AND** `startTime` comes after the time when the interface is called
            + The user has a booking in the past that has not been paid
        + _Pass conditions_:
            + `errorCode` is equal to *- UNPAID BOOKING FOUND*
    + **`testMakeBookingLimitExceed`**: test if the server behaves as expected when the user has already had 3 future bookings
        + _Preconditions_:
            + `userId` exists in the data-tier
            + `courtId` exists in the data-tier
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND**  `date` comes after the date when the interface is called
            + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by "-" *(hyphens)*, and ordered as given **AND** `startTime` comes before `endTime` **AND** `startTime` comes after the time when the interface is called
            + The user has already had three upcoming bookings
        + _Pass conditions_:
            + `errorCode` is equal to *- BOOKINGS LIMIT REACHED*
    + **`testMakeBookingUnauthorized`**:  test if the request is rejected when `caller` is invalid
         + _Preconditions_:
            + `userId` does not exist in the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *- UNAUTHORIZED*
    + **`testMakeBookingInvalidCourtId`**:  test if the request is rejected when `caller` is valid, but `courtId` is invalid
        + _Preconditions_:
            + `userId` exists in the data-tier
            + `courtId` doest not exist in the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *- INVALID COURT ID*
    + **`testMakeBookingInvalidDate`**: test if the request is rejected when `caller` is valid, but `date` is invalid
        + _Preconditions_:
            + `userId` exists in the data-tier
            + `date` is not formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, **OR** not ordered as given **OR**  `date` comes after the date when the interface is called
        + _Pass conditions_:
            + `errorCode` is equal to *- INVALID COURT DATE*
    + **`testMakeBookingInvalidDuration`**: test if the server behaves as expected when the request contains an invalid duration (`endTime - startTime`)
        + _Preconditions_:
            + `userId` exists in the data-tier
            + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by "-" *(hyphens)*, and ordered as given **AND** `startTime` comes before `endTime` **AND** `startTime` comes after the time when the interface is called
            + The period between `startTime` and `endTime` is not *45 minutes*, *1 hour*, *1 hour and 15 minutes*, or *1 hour and 30 minutes*
        + _Pass conditions_:
            + `errorCode` is equal to *- INVALID DURATION*
    + **`testMakeBookingInvalidStartTime`**: test if the server behaves as expected when the request contains an invalid `startTime`
        + _Preconditions_:
            + The user is logged in
            + The user does not have a past pending payment nor 3 future bookings
            + `startTime` is not formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by "-" *(hyphens)*, and ordered as given **OR** `startTime` does not come before `endTime` **OR** `startTime` does not come after the time when the interface is called
        + _Pass conditions_:
            + `errorCode` is equal to *- INVALID START TIME*
    + **`testMakeBookingInvalidEndTime`**: test if the server behaves as expected when the request contains an invalid `endTime`
        + _Preconditions_:
            + The user is logged in
            + The user does not have a past pending payment nor 3 future bookings
            + `endTime` is not formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by "-" *(hyphens)*, and ordered as given
        + _Pass conditions_:
            + `errorCode` is equal to *- INVALID END TIME*

`changeBookingState`
---
- **Description:** change the state of the booking with the given `bookingId`
- **Security/Caller:** `staffId`
- **Request:** `changeBookingState(bookingId, state)`
- **Response:**
    + *Success:*
        + `successCode`
    + *Error:*
        + `errorCode`
+ **Tests:**
    + **`testChangeBookingState`**: test if the request is accepted and the state of the booking in the database is changed when `caller` is valid and all the parameters, which are provided, are valid
        + _Preconditions_:
            + `staffId` exists in the data-tier
            + `bookingId` exists in the data-tier
            + `state` is valid
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testChangeBookingStateUnauthorized`**: test if the request is rejected when `caller` is invalid
        + _Preconditions_:
            + `staffId` does not exist in the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *- UNAUTHORIZED*
    + **`testChangeBookingStateInvalidBookingId`**: test if the request is rejected when `caller` is valid, but `bookingId` is invalid
        + _Preconditions_:
            + `staffId` exists in the data-tier
            + `bookingId` does not exist in the data tier
        + _Pass conditions_:
            + `errorCode` is equal to *- INVALID BOOKING ID*
    + **`testChangeBookingStateInvalidState`**: test if the request is rejected when `caller` is valid, but `state` is invalid
        + _Preconditions_:
            + `staffId` exists in the data-tier
            + `state` is invalid
        + _Pass conditions_:
            + `errorCode` is equal to *- INVALID STATE*


`cancelBooking`
---
- **Description:** cancel the booking with the given `bookingId`
- **Security/Caller:** `userId`
- **Request:** `cancelBooking(bookingId)`
- **Response:**
    + *Success:*
        + `successCode`
    + *Error:*
        + `errorCode`
- **Tests:**
    + **`testCancelBooking`**:  test if the request is accepted and booking tuple in the database is removed when `caller` is valid and all the parameters, which are provided, are valid
        + _Preconditions_:
            + `userId` exists in the data-tier
            + `bookingId` exists in the data-tier
            + The request is made in more than 24 hours before the start time of the booking
        + _Pass conditions_:
            + `successCode` is equal to *200 - SUCCESS*
    + **`testCancelBookingUnauthorized`**: test if the request is rejected when `caller` is invalid
        + _Preconditions_:
            + `userId` doest not exist in the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *- UNAUTHORIZED*
    + **`testCancelBookingLate`**: test is the request is rejected when `caller` is valid, but the time instance, when the request is made, is not 24 hours before the `startTime` of the booking
        + _Preconditions_:
            + `userId` exists in the data-tier
            + `bookingId` exists in the data-tier
            + The request is made in less than 24 hours before the start time of the booking
        + _Pass conditions_:
            + `errorCode` is equal to *- LATE CANCELLATION*
    + **`testCancelBookingInvalidId`**: test if the request is rejected when `caller` is valid, but `bookingId` is invalid
        + _Preconditions_:
            + `userId` exists in the data-tier
            + `bookingId` does not exist in the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *- BOOKING NOT FOUND*
