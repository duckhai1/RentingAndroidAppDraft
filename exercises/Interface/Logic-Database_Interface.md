## LOGIC-DATABASE INTERFACES
### LOGIN-LOGOUT:
#### login
- **Description:** request database for a given userName and password, check whether it is correct or not
- **Security/Caller:** anonymous
- **Request:** login(userName, password)
- **Response:** 
    + **Success:**
        + SuccessCode
    + **Error:**
        + 
#### logout
- **Description:** logout from a server
- **Security/Caller:** anonymous
- **Request:** logout(userId/sportCenterId)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + 
### DISPLAY INFORMATION:
#### getBooking
- **Description:** request database for all booking for a given city and a given date (**logic layer will extract from that data all available slot**)
- **Security/Caller:** anonymous
- **Request:** getBooking(city, date)
- **Response:** 
    + **Success:**
        + SuccessCode
        + bookingTable (contains these columns: bookingId, sportCenterId, courtId, startTime, endTime)
    + **Error:**
        +
#### getUserBooking
 - **Description:** request database for all booking of a user by a given city and date
 - **Security/Caller:** userId
 - **Request:** getUserBooking(userId, city, date)
 - **Response:**
     + **Success:**
         + SuccessCode
         + bookingTable (contains these columns: bookingId, sportCenterId, courtId, startTime, endTime)
     + **Error:**
         + 
#### getSportCenterBooking
- **Description:** request database for all booking of a sportCenter by a given date
- **Security/Caller:** sportCenterId
- **Request:** getSportCenterBooking(sportCenterId, date)
- **Response:**
    + **Success:**
        + SuccessCode
        + bookingTable (contains these columns: bookingId, userId, courtId, startTime, endTime)
    + **Error:**
        +
#### getUserInfo
- **Description:** request database for all information of a user by a given userId
- **Security/Caller:** anonymous
- **Request:** getUserInfo(userId)
- **Response:**
    + **Success:**
        + SuccessCode
        + userInfoTable(contains these columns: userId, firstName, lastName, dateOfBirth, phoneNumber)
    + **Error:**
        + 
#### getSportCenterInfo
- **Description:** request database for all information of sportCenter by a given sportCetnterId
- **Security/Caller:** anonymous
- **Request:** getSportCenterInfo(sportCenterId)
- **Response:**
    + **Success:**
        + SuccessCode
        + sportCenterInfoTable(contains these columns: sportCenterId, name, city, courtId)
    + **Error:**
        +
### PERFORM ACTION:
#### makeBooking
- **Description:** update to database new booking by a given userId, sportCenterId, courtId, date, startTime, endTime)
- **Security/Caller:** userId
- **Request:** makeBooking(userId, sportCenterId, courtId, date, startTime, endTime)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        +
#### cancelBooking
- **Description:** delete from database a existed booking by a given bookingId
- **Security/Caller:** userId
- **Request:** cancelBooking(userId, bookingId)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        +
#### changeBookingState
- **Description:** update to database a new booking state by a given bookingId and sportCenterId
- **Security/Caller:** sportCenterId
- **Request:** changeBookingState(bookingId, sportCenterId, newState)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        +
### MODIFY INFORMATION
#### updateUserInfo
- **Description:** update to database a new piece information base on the corresponding parameter and userId
- **Security/Caller:** userId
- **Request:** updateUserInfo(userId, firstName/ lastName/ birthday/ phoneNumber)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        +
#### updateSportCenterInfo
- **Description:** update to database a new piece information base on the corresponding parameter and sportCenterId
- **Security/Caller:** sportCenterId
- **Request:** updateSportCenterInfo(SportCenterId, name/ city/ phoneNumber)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + 
### FURTHER UPDATE / ADDITIONAL FEATURES:
#### contact
- **Description:** update to database a new message by a given senderId, receiverId and a message
- **Security/Caller:** userId/sportCenterId
- **Request:** contact(sportCenterId / userId (**senderId**), msg, sportCenterId / userId (**reveiverId**))
- **Response:**
    + **Success:** 
        + SuccessCode
    + **Error:**
        +
#### report
- **Description:** update to database a new report by a given reporterId and reporteeId and a message
- **Security/Caller:** anonymous
- **Request:** report(sportCenterId / userId (**reporterId**), msg, sportCenterId / userId (**reporteeId**))
- **Response:**
    + **Success:** 
        + SuccessCode
    + **Error:**
        +