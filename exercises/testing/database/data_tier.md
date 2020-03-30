#### Agreement Presentation-Database
- Every cityId, sportCenterId, courtId, bookingId is unique globally
- Each booking will have 3 state code:
    - Pending(0): Already Booking but unpaid
    - Paid(1): Paid booking
    - Cancelled(2): Cancelled booking
- Every list or array will be ordered in either Alphabetical order or Numerical order. Booking list will be ordered by time order of startTime

## LOGIC-DATABASE INTERFACES
### LOGIN-LOGOUT:
#### login
- **Description:** request database for a given userName and hashed password, check whether it is correct or not
- **Request:** loginStaff(staffName, password) / loginUser(userName, password)
- **Response:**
    + **Success:**
        + `successCode`
    + **Error:**
        + `errorCode`

#### logout
- **Description:** User logout from a server
- **Request:** logout(userId/staffId)
- **Response:**
    + **Success:**
        + `successCode`
    + **Error:**
        + `errorCode`

### DISPLAY INFORMATION:
#### getBooking
- **Description:** request database for all booking for a given city and a given date (**logic layer will extract from that data all available slot**)
- **Request:** `getBooking(cityId, date)`
- **Response:**
    + **Success:**
        + `successCode`
        + bookingTable (contains these columns: bookingId, sportCenterId, courtId, startTime, endTime)
    + **Error:**
        + `errorCode`
- **Tests:**
    + **`testGetBooking`**: test if the returned relation is correct, when all the parameters are valid
        + _Preconditions_:
            + `cityId` uniquely identifies a row in the `City` relation
            + `date` is in `DATE` format
        + _Pass conditions_:
            + `successCode` is equal to *200 - SUCCESS*
            + `Booking` is a relation `(bookingId, userId, sportCentreId, courtId, date, startTime, endTime, state)`, each row in this relation has:
                + Primary key: `bookingId`
                + Foreign keys: `userId`, `sportCentreId`, `courtId`
                + `cityId` is equal to the `cityId` given as parameter
                + `date` is is equal to the `date` given as parameter
                + `startTime` and `endTime` is in `TIMESTAMP` format
                + There does not exists in the relation any pair of rows A and B, where `A.courtId == B.courtID && (A.startTime < B.startTime and A.endTime > B.startTime) or (A.startTime > B.startTime and B.endTime > A.startTime)`, i.e., there does not exist any two rows with the same `courtId` with overlapping `startTime` and `endTime`
    + **`testGetUserBookingInvalidCityId`**: test if the request is rejected when `cityId` is invalid
        + _Preconditions_:
            + `cityId` does not identify a row in the `User` relation
        + _Pass conditions_:
            + `errorCode` is equal to *461 - INVALID CITY ID*
    + **`testGetUserBookingInvalidDate`**: test if the request `date` is invalid
        + _Preconditions_:
            + `date` is not in `DATE` format
        + _Pass conditions_:
            + `errorCode` is equal to *463 - INVALID DATE*



#### getUserBooking
 - **Description:** request database for all booking of a user by a given city and date
 - **Request:** `getUserBooking(userId, cityId, date)`
 - **Response:**
     + **Success:**
         + `successCode`
         + `Booking`
     + **Error:**
         + `errorCode`
- **Tests:**
    + **`testGetUserBooking`**: test if the returned relation is correct, when all the parameters are valid
            + `userId` uniquely identifies a row in the `User` relation
            + `cityId` uniquely identifies a row in the `City` relation
            + `date` is in `DATE` format
        + _Pass conditions_:
            + `successCode` is equal to *200 - SUCCESS*
            + `Booking` is a relation `(bookingId, userId, sportCentreId, courtId, date, startTime, endTime, state)`, each row in this relation has:
                + Primary key: `bookingId`
                + Foreign keys: `userId`, `sportCentreId`, `courtId`
                + `userId` is equal to the `userId` given as parameter
                + `cityId` is equal to the `cityId` given as parameter
                + `date` is is equal to the `date` given as parameter
                + `startTime` and `endTime` is in `TIMESTAMP` format
                + There does not exists in the relation any pair of rows A and B, where `A.courtId == B.courtID && A.sportCentreId == B.sportCentreId && (A.startTime < B.startTime and A.endTime > B.startTime) or (A.startTime > B.startTime and B.endTime > A.startTime)`, i.e., there does not exist any two rows with the same `courtId` and the same `sportCentreId` with overlapping `startTime` and `endTime`
    + **`testGetUserBookingInvalidUserId`**: test if the request is rejected when `userId` is invalid
      + _Preconditions_:
            + `userId` does not identify a row in the `User` relation
        + _Pass conditions_:
            + `errorCode` is equal to *- INVALID USER ID*
    + **`testGetUserBookingInvalidCityId`**: test if the request is rejected when `cityId` is invalid
        + _Preconditions_:
            + `cityId` does not identify a row in the `User` relation
        + _Pass conditions_:
            + `errorCode` is equal to *461 - INVALID CITY ID*
    + **`testGetUserBookingInvalidDate`**: test if the request `date` is invalid
        + _Preconditions_:
            + `date` is not in `DATE` format
        + _Pass conditions_:
            + `errorCode` is equal to *463 - INVALID DATE*

#### getSportCenterBooking
- **Description:** request database for all booking of a sportCenter by a given date
- **Request:** getSportCenterBooking(sportCentreId, date)
- **Response:**
    + **Success:**
        + `successCode`
        + `Booking`
    + **Error:**
        + `errorCode`
- **Tests:**
    + **`testGetSportCentreBooking`**:  test if the request is accepted and the data in the response is valid when all the parameters are valid
            + `sportCentreId` uniquely identifies a row in the `SportCentre` relation
            + `date` is in `DATE` format
        + _Pass conditions_:
            + `successCode` is equal to *200 - SUCCESS*
            + Number of elements in `Bookings` is equal to the number of bookings for the given sport centre in the data-tier
            + `Booking` is a relation `(bookingId, userId, sportCentreId, courtId, date, startTime, endTime, state)`, each row in this relation has:
                + Primary key: `bookingId`
                + Foreign keys: `userId`, `sportCentreId`, `courtId`
                + `sportCentreId` is equal to the `sportCentreId` given as parameter
                + `date` is is equal to the `date` given as parameter
                + `startTime` and `endTime` is in `TIMESTAMP` format
                + There does not exists in the relation any pair of rows A and B, where `A.courtId == B.courtID && (A.startTime < B.startTime and A.endTime > B.startTime) or (A.startTime > B.startTime and B.endTime > A.startTime)`, i.e., there does not exist any two rows with the same `courtId` with overlapping `startTime` and `endTime`
    + **`testGetSportCentreBookingInvalidId`**: test if the request is rejected when `sportCentreId` is invalid
        + _Preconditions_:
            + `sportCentreId` does not identify a row in the `SportCentre` relation
        + _Pass conditions_:
            + ```errorCode``` is equal to *462 - INVALID SPORT CENTRE ID*
    + **`testGetSportCentreBookingInvalidDate`**: test if the request is rejected when `date` is invalid
        + _Preconditions_:
            + `date` is not in `DATE` format
        + _Pass conditions_:
            + ```errorCode``` is equal to *463 - INVALID DATE*

#### getUserInfo
- **Description:** request database for all information of a user by a given userId
- **Request:** getUserInfo(userId)
- **Response:**
    + **Success:**
        + `successCode`
        + `User`
    + **Error:**
        + `errorCode`
- **Tests:**
    + **`testGetUserInfo`**: test if the request is accepted and the data in the response is valid when all the parameters are valid
        + _Preconditions_:
           + `userId` uniquely identifies a row in `User` relation
        + _Test conditions_:
            + `succesCode` is equal to *201 - SUCCESS WITH DATA*
            + `User` is a relation `(userId, firstName, lastName, birthday, phoneNumber)` that has only one row, where:
                + Primary key: `userId`
                + `userId` is is equal to the `userId` given as parameter
                + `firstName` is in `VARCHAR(32)` format and contains only alphabetic characters and spaces and has no leading/trailing spaces
                + `lastName` is in `VARCHAR(32)` format and contains only alphabetic characters and spaces and has no leading/trailing spaces
                + `birthday` is in `DATE` format and contains only alphabetic characters and spaces
                + `phoneNumber` is in `VARCHAR(16)` format and contains only numeric characters
    + **`testGetUserInfoInvalidUserId`**:  test if the request is rejected when `userId` is invalid
        + _Preconditions_:
           + `userId` does not identify a row in `User` relation
        + _Pass conditions_:
            + ``errorCode`` is equal to *- INVALID USER ID*

#### getSportCenterInfo
- **Description:** request database for all information of sportCenter by a given sportCenterId
- **Request:** getSportCenterInfo(sportCenterId)
- **Response:**
    + **Success:**
        + `successCode`
        + `SportCentre`
    + **Error:**
        + `errorCode`
- **Tests:**
    + **`testGetSportCentreInfo`**: test if the request is accepted and a booking is created when all the parameters are valid
        + _Preconditions_:
            + `sportCentreId` uniquely identifies a row in `SportCentre` relation
        + _Expected response_:
            + `successCode` is equal to *200 - SUCCESS*
            + `SportCentre` is a relation `(sportCentreId, name, cityId, address, phoneNum)` that has only one row, where:
                + Primary key: `userId`
                + Foreign key: `cityId`
                + `sportCentreId` is is equal to the `sportCentreId` given as parameter
                + `name` is in `VARCHAR(64)` format
                + `lastName` is in `VARCHAR(32)` format
                + `birthday` is in `DATE` format
                + `phoneNumber` is in `VARCHAR(16)` format and contains only numeric characters
    + **`testGetSportCentreInfoInvalidId`**: test if the request is rejected when `caller` is invalid
        + _Preconditions_:
            + `sportCentreId` does not identify a row in `SportCentre` relation
        + _Pass conditions_:
            + ```errorCode``` is equal to *- INVALID SPORTCENTRE ID*



### PERFORM ACTION:
#### makeBooking
- **Description:** update to database new booking by a given userId, sportCenterId, courtId, date, startTime, endTime)
- **Request:** makeBooking(userId, sportCenterId, courtId, date, startTime, endTime)
- **Response:**
    + **Success:**
        + `successCode`
    + **Error:**
        + `errorCode`
- **Tests:**
    + **`testMakeBooking`**: test if the request is accepted and a booking is created when `caller` is valid and all the parameters are valid
        + _Preconditions_:
            + `userId` uniquely identifies a row in `User` relation
            + `courtId` uniquely identifies a row in `Court` relation
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND**  `date` comes after the date when the interface is called
            + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by ":" *(colon)*,, and ordered as given **AND** `startTime` comes before `endTime` **AND** `startTime` comes after the time when the interface is called
            + The user does not have unpaid booking
            + The user does not have three upcomming bookings
        + _Pass conditions_:
            + `successCode` is equal to *202 - CREATED*
            + *Side-effect:* a tuples and relations related the booking are created
    + **`testMakeBookingInvalidSlot`**: test if the request is rejected when the `caller` is valid and the booking is invalid, i.e., there exists another booking with the same `courtId` and the same `date`, but its `startTime` and `endTime` overlapping with the new booking;
        + _Preconditions_:
            + `userId` uniquely identifies a row in relation
            + `courtId` uniquely identifies a row in relation
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND**  `date` comes after the date when the interface is called
            + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by ":" *(colon)*,, and ordered as given **AND** `startTime` comes before `endTime` **AND** `startTime` comes after the time when the interface is called
            + The user does not have unpaid booking
            + The user does not have three upcomming bookings
            + The available slots computed by the server does not contains the given slot
        + _Pass conditions_:
            + ```errorCode``` is equal to *464 - INVALID SLOT*
    + **`testMakeBookingPendingPayment`**: test if the request is rejected when the `caller` is valid and the user has past unpaid payment
        + _Preconditions_:
            + `userId` uniquely identifies a row in relation
            + `courtId` uniquely identifies a row in relation
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND**  `date` comes after the date when the interface is called
            + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by ":" *(colon)*,, **AND**and ordered as given **AND** `startTime` comes before `endTime` **AND** `startTime` comes after the time when the interface is called
            + The user has a booking in the past that has not been paid
        + _Pass conditions_:
            + ```errorCode``` is equal to *413 - UNPAID BOOKING FOUND*
    + **`testMakeBookingLimitExceed`**: test if the server behaves as expected when the user has already had 3 future bookings
        + _Preconditions_:
            + `userId` uniquely identifies a row in relation
            + `courtId` uniquely identifies a row in relation
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND**  `date` comes after the date when the interface is called
            + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by ":" *(colon)*,, and ordered as given **AND** `startTime` comes before `endTime` **AND** `startTime` comes after the time when the interface is called
            + The user has already had three upcoming bookings
        + _Pass conditions_:
            + ```errorCode``` is equal to *- BOOKINGS LIMIT REACHED*
    + **`testMakeBookingUnauthorized`**:  test if the request is rejected when `caller` is invalid
         + _Preconditions_:
            + `userId` does not exist in the data-tier
        + _Pass conditions_:
            + ```errorCode``` is equal to *401 - UNAUTHORIZED*
    + **`testMakeBookingInvalidCourtId`**:  test if the request is rejected when `caller` is valid and `courtId` is invalid
        + _Preconditions_:
            + `userId` uniquely identifies a row in relation
            + `courtId` doest not exist in the data-tier
        + _Pass conditions_:
            + ```errorCode``` is equal to *- INVALID COURT ID*
    + **`testMakeBookingInvalidDate`**: test if the request is rejected when `caller` is valid and `date` is invalid
        + _Preconditions_:
            + `userId` uniquely identifies a row in relation
            + `date` is not formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, **OR** not ordered as given **OR**  `date` comes after the date when the interface is called
        + _Pass conditions_:
            + ```errorCode``` is equal to *463 - INVALID DATE*
    + **`testMakeBookingInvalidDuration`**: test if the server behaves as expected when the request contains an invalid duration (`endTime - startTime`)
        + _Preconditions_:
            + `userId` uniquely identifies a row in relation
            + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by ":" *(colon)*,, and ordered as given **AND** `startTime` comes before `endTime` **AND** `startTime` comes after the time when the interface is called
            + The period between `startTime` and `endTime` is not *45 minutes*, *1 hour*, *1 hour and 15 minutes*, or *1 hour and 30 minutes*
        + _Pass conditions_:
            + ```errorCode``` is equal to *465 - INVALID DURATION*
    + **`testMakeBookingInvalidStartTime`**: test if the server behaves as expected when the request contains an invalid `startTime`
        + _Preconditions_:
            + The user is logged in
            + The user does not have a past pending payment nor 3 future bookings
            + `startTime` is not formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by "-" *(hyphens)*, and ordered as given **OR** `startTime` does not come before `endTime` **OR** `startTime` does not come after the time when the interface is called
        + _Pass conditions_:
            + ```errorCode``` is equal to *470 - INVALID START TIME*
    + **`testMakeBookingInvalidEndTime`**: test if the server behaves as expected when the request contains an invalid `endTime`
        + _Preconditions_:
            + The user is logged in
            + The user does not have a past pending payment nor 3 future bookings
            + `endTime` is not formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by "-" *(hyphens)*, and ordered as given
        + _Pass conditions_:
            + ```errorCode``` is equal to *471 - INVALID END TIME*

#### cancelBooking
- **Description:** delete from database a existed booking by a given bookingId
- **Request:** cancelBooking(bookingId)
- **Response:**
    + **Success:**
        + `successCode`
    + **Error:**
        + `errorCode`
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

#### changeBookingState
- **Description:** update to database a new booking state by a given bookingId and a state
- **Request:** changeBookingState(bookingId, state)
- **Response:**
    + **Success:**
        + `successCode`
    + **Error:**
        + `errorCode`
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



### MODIFY INFORMATION
#### updateUserInfo (updateFirstName, updateLastName, updateBirthday, updatePhoneNumber)
- **Description:** update to database a new piece information base on the corresponding parameter and userId
- **Request:** updateFirstName(userId, firstName)/ updateLastName(userId, lastName)/ updateBirthday(userId, birthday)/ updatePhoneNum(userId, phoneNum)
- **Response:**
    + **Success:**
        + `successCode`
    + **Error:**
        + `errorCode`
- **Tests:**
    + **`testUpdateUserInfoInvalidId`**: test if the request is rejected when the `caller` is invalid
        + _Preconditions_:
            + `userId` does no exist in the data-tier
        + _Pass conditions_:
            + ```errorCode``` is equal to *401 - UNAUTHORIZED*
    + **`testUpdateUserFirstName`**: test if the request is accepted and the user's first name in the data-tier is changed according to the provided information when the `caller` is valid
        + _Preconditions_:
            + `userId` uniquely identifies a row in relation
            + `firstName` contains only alphabetic characters and spaces
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdateUserFirstNameInvalid`**: test if the request is rejected when the `caller` is valid and `firstName` is invalid
        + _Preconditions_:
            + `userId` uniquely identifies a row in relation
            + `firstName` does not contain only alphanumeric characters and spaces
        + _Pass conditions_:
            + ```errorCode``` is equal to *475 - INVALID FIRST NAME*

    + **`testUpdateUserLastName`**: test if the request is accepted and the user's last name in the data-tier is changed according to the provided information when the `caller` is valid
        + _Preconditions_:
            + `userId` uniquely identifies a row in relation
            + `lastName` contains only alphabetic characters and spaces
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdateUserLastNameInvalid`**: test if the request is rejected when the `caller` is valid and `lastName` is invalid
        + _Preconditions_:
            + `userId` uniquely identifies a row in relation
            + `lastName` does not contain only alphebetic characters and spaces
        + _Pass conditions_:
            + ```errorCode``` is equal to *476 - INVALID LAST NAME*
    + **`testUpdateUserBirthDay`**: test if the request is accepted and the user's birth day in the data-tier is changed according to the provided information when the `caller` is valid
        + _Preconditions_:
            + `userId` uniquely identifies a row in relation
            + `birthday` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdateUserBirthDayInvalid`**: test if the request is rejected when the `caller` is valid and `birthday` is invalid
        + _Preconditions_:
            + `userId` uniquely identifies a row in relation
            + `birthday` is not formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given
        + _Pass conditions_:
            + ```errorCode``` is equal to *477 - INVALID BIRTHDAY*
    + **`testUpdateUserPhoneNumber`**: test if the request is accepted and the user's phone number in the data-tier is changed according to the provided information when the `caller` is valid
        + _Preconditions_:
            + `userId` uniquely identifies a row in relation
            + `phoneNumber` contains only numeric characters
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdateUserPhoneNumberInvalid`**: test if the request is rejected when the `caller` is valid and `phoneNumber` is invalid
        + _Preconditions_:
            + `userId` uniquely identifies a row in relation
            + `phoneNumber` does not contain only numeric characters
        + _Pass conditions_:
            + ```errorCode``` is equal to *474 - INVALID PHONE NUMBER*

#### updateSportCenterInfo (updateName, updateCity, updatePhoneNumber)
- **Description:** update to database a new piece information base on the corresponding parameter and sportCenterId
- **Request:** updateName(sportCenterId, name)/ updateCity(sportCenterId, city)/ updatePhoneNumber(sportCenterId, phoneNum)
- **Response:**
    + **Success:**
        + `successCode`
    + **Error:**
        + `errorCode`
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

### FURTHER UPDATE / ADDITIONAL FEATURES:
#### contact
- **Description:** update to database a new message by a given senderId, receiverId and a message
- **Request:** contact(sportCenterId / userId (**senderId**), msg, sportCenterId / userId (**reveiverId**))
- **Response:**
    + **Success:**
        + `successCode`
    + **Error:**
        + `errorCode`
+ **Tests:**
    + **`testContact`**: test if the request is accepted and the message is sent when `caller` is valid and all the parameters are valid
        + _Preconditions_:
            + `caller` (`userId/staffId`) exists in the data returned to the logic-tier from the data-tier
            + `sportCentreId/sportCentreId` exists in the data returned to the logic-tier from the data-tier
        + _Pass conditions_:
            + `successCode` is equal to *202 - CREATED*
            + *Side effect*:  tuples and relations needed for the message are created in the data tier
    + **`testContactUnauthorized`**: test if the request is rejected when `caller` is invalid
        + _Preconditions_:
            + `caller` (`userId/staffId`) doest not exist in the data-tier invalid
        + _Pass conditions_:
            + `errorCode` is equal to *401 - UNAUTHORIZED*
    + **`testContactInvalidReceiverId`**: test if the request is rejected when `caller` is valid and `receiver` is invalid
        + _Preconditions_:
            + `caller` (`userId/staffId`) exists in the data returned to the logic-tier from the data-tier
            + `userId/sportCentreId` does not exists in the data returned to the logic-tier from the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *468 - INVALID RECEIVER ID*

#### report
- **Description:** update to database a new report by a given reporterId and reporteeId and a message
- **Request:** report(sportCenterId / userId (**reporterId**), msg, sportCenterId / userId (**reporteeId**))
- **Response:**
    + **Success:**
        + `successCode`
    + **Error:**
        + `errorCode`
+ **Tests:**
    + **`testReport`**: test if the request is accepted and a report is created when `caller` is valid and all the paramters are valid
        + _Preconditions_:
            + `caller` (`userId/staffId`) exists in the data returned to the logic-tier from the data-tier
            + `sportCentreId/sportCentreId` exists in the data returned to the logic-tier from the data-tier
        + _Pass conditions_:
            + `successCode` is equal to *202 - CREATED*
            + *Side effect*:  tuples and relations needed for the report are created in the data tier
    + **`testReportUnauthorized`**: test if the request is rejected when `caller` is invalid
        + _Preconditions_:
            + `caller` (`userId/staffId`) does not exist in the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *401 - UNAUTHORIZED*
    + **`testReportInvalidReporteeId`**: test if the request is rejected when `caller` is valid and `reportee` is invalid
        + _Preconditions_:
            + `caller` (`userId/staffId`) exists in the data returned to the logic-tier from the data-tier
            + `userId/sportCentreId` does not exists in the data returned to the logic-tier from the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *467 - INVALID REPORTEE ID*
