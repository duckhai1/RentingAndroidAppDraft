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
                + state is either `UPCOMMING`, `UNPAID`, `PAID`, or `CANCELLED`
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
                + state is either `UPCOMMING`, `UNPAID`, `PAID`, or `CANCELLED`
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
                + state is either `UPCOMMING`, `UNPAID`, `PAID`, or `CANCELLED`
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
                + `firstName` is in `VARCHAR(32)` format, contains only alphabetic characters and spaces, and has no leading/trailing spaces
                + `lastName` is in `VARCHAR(32)` format, contains only alphabetic characters and spaces, and has no leading/trailing spaces
                + `birthday` is in `DATE` format
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
            + `SportCentre` is a relation `(sportCentreId, name, cityId, address, phoneNumber)` that has only one row, where:
                + Primary key: `userId`
                + Foreign key: `cityId`
                + `sportCentreId` is is equal to the `sportCentreId` given as parameter
                + `name` is in `VARCHAR(64)` format, contains only alphanumeric characters and spaces, and has no leading/trailing spaces
                + `address` is in `VARCHAR(64)` format, contains only alphanumeric characters, spaces, and the following special characters `-/,`, and has no leading/trailing spaces
                + `phoneNumber` is in `VARCHAR(16)` format and contains only numeric characters
    + **`testGetSportCentreInfoInvalidId`**: test if the request is rejected when `caller` is invalid
        + _Preconditions_:
            + `sportCentreId` does not identify a row in `SportCentre` relation
        + _Pass conditions_:
            + ```errorCode``` is equal to *- INVALID SPORTCENTRE ID*



### PERFORM ACTION:
#### makeBooking
- **Description:** update to database new booking by a given userId, sportCenterId, courtId, date, startTime, endTime)
- **Request:** `makeBooking(userId, courtId, dateCreated, date, startTime, endTime)`
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
            + `date` is in `DATE` format and `date` comes after the date when the interface is called
            + `dateCreated` is in `DATE` format and is equal to the date when the interface is called
            + `startTime` and `endTime` is in `TIMESTAMP` format, `startTime` comes before `endTime`, and `startTime` comes after the time when the interface is called
            + The user does not have unpaid booking
            + The user does not have three upcomming bookings
            + There does not exist another booking with the same `courtId` and the same `date`, but its `startTime` and `endTime` overlapping with the new booking
        + _Pass conditions_:
            + `successCode` is equal to *202 - CREATED*
            + *Side-effect:* a tuples and relations related the booking are created
    + **`testMakeBookingInvalidBooking`**: test if the request is rejected when the `caller` is valid and the booking is invalid, i.e.,
        + _Preconditions_:
            + `userId` uniquely identifies a row in `User` relation
            + `courtId` uniquely identifies a row in `Court` relation
            + `date` is in `DATE` format and `date` comes after the date when the interface is called
            + `dateCreated` is in `DATE` format and is equal to the date when the interface is called
            + `startTime` and `endTime` is in `TIMESTAMP` format, `startTime` comes before `endTime`, and `startTime` comes after the time when the interface is called
            + The user does not have unpaid booking
            + The user does not have three upcomming bookings
            + There exists another booking with the same `courtId` and the same `date`, but its `startTime` and `endTime` overlapping with the new booking
        + _Pass conditions_:
            + ```errorCode``` is equal to *464 - INVALID SLOT*
    + **`testMakeBookingPendingPayment`**: test if the request is rejected when the `caller` is valid and the user has past unpaid payment
        + _Preconditions_:
            + `userId` uniquely identifies a row in `User` relation
            + `courtId` uniquely identifies a row in `Court` relation
            + `date` is in `DATE` format and `date` comes after the date when the interface is called
            + `dateCreated` is in `DATE` format and is equal to the date when the interface is called
            + `startTime` and `endTime` is in `TIMESTAMP` format, `startTime` comes before `endTime`, and `startTime` comes after the time when the interface is called
            + The user has unpaid booking
            + The user does not have three upcomming bookings
            + There does not exist another booking with the same `courtId` and the same `date`, but its `startTime` and `endTime` overlapping with the new booking
        + _Pass conditions_:
            + ```errorCode``` is equal to *413 - UNPAID BOOKING FOUND*
    + **`testMakeBookingLimitExceed`**: test if the server behaves as expected when the user has already had 3 future bookings
        + _Preconditions_:
            + `userId` uniquely identifies a row in `User` relation
            + `courtId` uniquely identifies a row in `Court` relation
            + `date` is formatted as "YYYY-MM-DD", i.e., 4-digit year, 2-digit month, and 2-digit day of the month which are separated by "-" *(hyphens)*, and ordered as given **AND**  `date` comes after the date when the interface is called
            + `startTime` and `endTime` is formatted as "HH:mm:ss", i.e., 2-digit hour, 2-digit minute, and 2-digit second which are separated by ":" *(colon)*,, and ordered as given **AND** `startTime` comes before `endTime` **AND** `startTime` comes after the time when the interface is called
            + The user does not have unpaid booking
            + The user already has three upcomming bookings
            + There does not exist another booking with the same `courtId` and the same `date`, but its `startTime` and `endTime` overlapping with the new booking
        + _Pass conditions_:
            + ```errorCode``` is equal to *- BOOKINGS LIMIT REACHED*
    + **`testMakeBookingUnauthorized`**:  test if the request is rejected when `userId` is invalid
         + _Preconditions_:
            + `userId` does not identify a row in `User` relation
        + _Pass conditions_:
            + ```errorCode``` is equal to *401 - UNAUTHORIZED*
    + **`testMakeBookingInvalidCourtId`**:  test if the request is rejected `courtId` is invalid
        + _Preconditions_:
            + `courtId` does not identify a row in `Court` relation
        + _Pass conditions_:
            + ```errorCode``` is equal to *- INVALID COURT ID*
    + **`testMakeBookingInvalidDate`**: test if the request is rejected when `date` is invalid
        + _Preconditions_:
            + `date` is not in `DATE` format
        + _Pass conditions_:
            + ```errorCode``` is equal to *463 - INVALID DATE*
    + **`testMakeBookingInvalidDuration`**: test if the request is rejected when the duration between `startTime` and `endTime` is invalid
        + _Preconditions_:
            + The period between `startTime` and `endTime` is not *45 minutes*, *1 hour*, *1 hour and 15 minutes*, or *1 hour and 30 minutes*
        + _Pass conditions_:
            + ```errorCode``` is equal to *465 - INVALID DURATION*
    + **`testMakeBookingInvalidStartTime`**: test if the request is rejected when `startTime` is invalid
        + _Preconditions_:
            + `startTime` is not in `TIMESTAMP` format **OR** `startTime` does not come before `endTime` **OR** `startTime` does not come after the time when the interface is called
        + _Pass conditions_:
            + ```errorCode``` is equal to *470 - INVALID START TIME*
    + **`testMakeBookingInvalidEndTime`**: test if the request is rejected when `endTime` is invalid
        + _Preconditions_:
            + `startTime` is not in `TIMESTAMP` format
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
    + **`testCancelBooking`**:  test if the request is accepted and the state of the booking is changed to `CANCELLED` when all the parameters are valid
        + _Preconditions_:
            + `bookingId` uniquely identifies a row in `Booking` relation
            + The request is made in more than 24 hours before the start time of the booking
        + _Pass conditions_:
            + `successCode` is equal to *200 - SUCCESS*
    + **`testCancelBookingLate`**: test is the request is rejected when the request is made in less than 24 hours before `startTime` of the booking
        + _Preconditions_:
            + `bookingId` uniquely identifies a row in `Booking` relation
            + The request is made in less than 24 hours before the start time of the booking
        + _Pass conditions_:
            + ``errorCode`` is equal to *412 - BOOKING CANCELLATION REJECTED*
    + **`testCancelBookingInvalidId`**: test if the request is rejected when `bookingId` is invalid
        + _Preconditions_:
            + `bookingId` does not identify a row in `Booking` relation
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
            + `bookingId` uniquely identifies a row in `Booking` relation
            + Given the row uniquely identified by `bookingId`, `state` is valid when:
                + `state == PAID` iff `row.State == UPCOMMING || row.State == UNPAID`
                + or `state == CANCELLED || state == UNPAID` iff `row.State == UPCOMMING`
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testChangeBookingStateInvalidBookingId`**: test if the request is rejected when `caller` is valid and `bookingId` is invalid
        + _Preconditions_:
            + `bookingId` does not identify a row in `Booking` relation
        + _Pass conditions_:
            + ``errorCode`` is equal to *410 - BOOKING NOT FOUND*
    + **`testChangeBookingStateInvalidState`**: test if the request is rejected when `state` is invalid
        + _Preconditions_:
            + Given the row uniquely identified by `bookingId`, `state` is invalid when none of the following is satisfied:
                + `state == PAID` iff `row.State == UPCOMMING || row.State == UNPAID`
                + `state == CANCELLED || state == UNPAID` iff `row.State == UPCOMMING`
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
    + **`testUpdateUserFirstName`**: test if the request is accepted and the user's first name is changed according to the provided information all the paramters are valid
        + _Preconditions_:
            + `userId` uniquely identifies a row in `User` relation
            + `firstName` is in `VARCHAR(32)` format, contains only alphabetic characters and spaces, and has no leading/trailing spaces
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdateUserFirstNameInvalidId`**: test if the request is rejected when `userId` is invalid
        + _Preconditions_:
            + `userId` doest not identify a row in `User` relation
        + _Pass conditions_:
            + ```errorCode``` is equal to *478 - INVALID USER ID*
    + **`testUpdateUserFirstNameInvalid`**: test if the request is rejected when `firstName` is invalid
        + _Preconditions_:
            + `firstName` is not in `VARCHAR(32)` format, or not contains only alphabetic characters and spaces, or has leading/trailing spaces
        + _Pass conditions_:
            + ```errorCode``` is equal to *475 - INVALID FIRST NAME*
    + **`testUpdateUserLastName`**: test if the request is accepted and the user's last name is changed according to the provided information all the paramters are valid
        + _Preconditions_:
            + `userId` uniquely identifies a row in `User` relation
            + `lastName` is in `VARCHAR(32)` format, contains only alphabetic characters and spaces, and has no leading/trailing spaces
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdateUserLastNameInvalidId`**: test if the request is rejected when `userId` is invalid
        + _Preconditions_:
            + `userId` doest not identify a row in `User` relation
        + _Pass conditions_:
            + ```errorCode``` is equal to *478 - INVALID USER ID*
    + **`testUpdateUserLastNameInvalid`**: test if the request is rejected when `lastName` is invalid
        + _Preconditions_:
            + `lastName` is not in `VARCHAR(32)` format, or not contains only alphabetic characters and spaces, or has leading/trailing spaces
        + _Pass conditions_:
            + ```errorCode``` is equal to *476 - INVALID LAST NAME*
    + **`testUpdateUserBirthday`**: test if the request is accepted and the user's birthday is changed according to the provided information all the paramters are valid
        + _Preconditions_:
            + `userId` uniquely identifies a row in `User` relation
            + `birthday` is in `DATE` format
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdateUserBirthdayInvalidId`**: test if the request is rejected when `userId` is invalid
        + _Preconditions_:
            + `userId` doest not identify a row in `User` relation
        + _Pass conditions_:
            + ```errorCode``` is equal to *478 - INVALID USER ID*
    + **`testUpdateUserBirthdayInvalid`**: test if the request is rejected when `birthday` is invalid
        + _Preconditions_:
            + `birthday` is no in `DATE` format
        + _Pass conditions_:
            + `errorCode` is equal to *477 - INVALID BIRTHDAY*
    + **`testUpdateUserPhoneNumber`**: test if the request is accepted and the user's phone number is changed according to the provided information all the paramters are valid
        + _Preconditions_:
            + `userId` uniquely identifies a row in `User` relation
            + `phoneNumber` is in `VARCHAR(16)` format and contains only numeric characters
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdateUserPhoneNumberInvalidId`**: test if the request is rejected when `userId` is invalid
        + _Preconditions_:
            + `userId` doest not identify a row in `User` relation
        + _Pass conditions_:
            + ```errorCode``` is equal to *478 - INVALID USER ID*
    + **`testUpdateUserPhoneNumberInvalid`**: test if the request is rejected when `phoneNumber` is invalid
        + _Preconditions_:
            + `phoneNumber` is not in `VARCHAR(16)` format or does not contain only numeric characters
        + _Pass conditions_:
            + `errorCode` is equal to *474 - INVALID PHONE NUMBER*


#### updateSportCenterInfo (updateName, updateCity, updatePhoneNumber)
- **Description:** update to database a new piece information base on the corresponding parameter and sportCenterId
- **Request:** `updateName(sportCenterId, name)` / `updateCity(sportCenterId, cityId)` / `updatePhoneNumber(sportCenterId, phoneNum)`
- **Response:**
    + **Success:**
        + `successCode`
    + **Error:**
        + `errorCode`
- **Tests:**
    + **`testUpdateName`**: test if the request is accepted and the sport centre's name is changed according to the provided information when all the parameters are valid
        + _Preconditions_:
            + `sportCentreId` uniquely identifies a row in `SportCentre` relation
            + `name` is in `VARCHAR(64)` format, contains only alphanumeric characters and spaces, and has no leading/trailing spaces
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdateNameInvalidId`**: test if the request is rejected when and `sportCentreId` is invalid
        + _Preconditions_:
            + `sportCentreId` does not identify a row in `SportCentre` relation
        + _Pass conditions_:
            + ``errorCode`` is equal to *462 - INVALID SPORT CENTRE ID*
    + **`testUpdateNameInvalid`**: test if the request is rejected when `name` is invalid
        + _Preconditions_:
            + `name` is not in `VARCHAR(64)` format, or does not contain only alphanumeric characters and spaces, or has leading/trailing spaces
        + _Pass conditions_:
            + ``errorCode`` is equal to *472 - INVALID SPORT CENTRE NAME*

    + **`testUpdateCity`**: test if the request is accepted and the sport centre's `cityId` is changed according to the provided information when all the parameters are valid
        + _Preconditions_:
            + `sportCentreId` uniquely identifies a row in `SportCentre` relation
            + `cityId` uniquely identifies a row in `City` relation
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdateCityInvalidId`**: test if the request is rejected when and `sportCentreId` is invalid
        + _Preconditions_:
            + `sportCentreId` does not identify a row in `SportCentre` relation
        + _Pass conditions_:
            + ``errorCode`` is equal to *462 - INVALID SPORT CENTRE ID*
    + **`testUpdateCityInvalid`**: test if the request is rejected when `cityId` is invalid
        + _Preconditions_:
            + `cityId` does not identify a row in `City` relation
        + _Pass conditions_:
            + ``errorCode`` is equal to *461 - INVALID CITY ID*

    + **`testUpdateAddress`**: test if the request is accepted and the sport centre's `address` is changed according to the provided information when all the paratemeters are valid
        + _Preconditions_:
            + `sportCentreId` uniquely identifies a row in `SportCentre` relation
            + `address` is in `VARCHAR(64)` format, contains only alphanumeric characters, spaces, and the following special characters `-/,`, and has no leading/trailing spaces
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdateAddressInvalidId`**: test if the request is rejected when and `sportCentreId` is invalid
        + _Preconditions_:
            + `sportCentreId` does not identify a row in `SportCentre` relation
        + _Pass conditions_:
            + ``errorCode`` is equal to *462 - INVALID SPORT CENTRE ID*
    + **`testUpdateAddressInvalid`**: test if the request is rejected when `address` is invalid
        + _Preconditions_:
            + `address` is not in `VARCHAR(64)` format, or does not contain only alphanumeric characters, spaces, and the following special characters `-/,`, or has leading/trailing spaces
        + _Pass conditions_:
            + ``errorCode`` is equal to *473 - INVALID ADDRESS*

    + **`testUpdatePhoneNumber`**: test if the request is accepted and the sport centre's phone number is changed according to the provided information when all the parameters are valid
        + _Preconditions_:
            + `sportCentreId` uniquely identifies a row in `SportCentre` relation
            + `phoneNumber` is in `VARCHAR(16)` format and contains only numeric characters
        + _Pass conditions_:
            + `successCode` is equal to *203 - UPDATED*
    + **`testUpdatePhoneNumberInvalidId`**: test if the request is rejected when `sportCentreId` is invalid
        + _Preconditions_:
            + `sportCentreId` does not identify a row in `SportCentre` relation
        + _Pass conditions_:
            + ``errorCode`` is equal to *462 - INVALID SPORT CENTRE ID*
    + **`testUpdatePhoneNumberInvalid`**: test if the request is rejected when `phoneNumber` is invalid
        + _Preconditions_:
            + `phoneNumber` is not in `VARCHAR(16)` format or does no contain only numeric characters
        + _Pass conditions_:
            + ``errorCode`` is equal to *474 - INVALID PHONE NUMBER*

### FURTHER UPDATE / ADDITIONAL FEATURES:
#### contact
- **Description:** update to database a new message by a given senderId, receiverId and a message
- **Request:** `contact(sportCenterId/userId (**senderId**), userId/sportCentreId (**reveiverId**), msg)`
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
    + **`testContactInvalidReceiverId`**: test if the request is rejected when `caller` is valid and `receiver` is invalid
        + _Preconditions_:
            + `caller` (`userId/staffId`) exists in the data returned to the logic-tier from the data-tier
            + `userId/sportCentreId` does not exists in the data returned to the logic-tier from the data-tier
        + _Pass conditions_:
            + `errorCode` is equal to *468 - INVALID RECEIVER ID*

#### report
- **Description:** update to database a new report by a given reporterId and reporteeId and a message
- **Request:** report(sportCenterId / userId (**reporterId**), sportCenterId / userId (**reporteeId**), msg)
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
