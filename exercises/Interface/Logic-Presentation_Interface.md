## LOGIC-PRESENTATION INTERFACES
### LOGIN-LOGOUT:
#### login
- **Description:** login by a given userName and password
- **Security/Caller:** anonymous
- **Request:** login(userName, password)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        +
### logout
- **Description:** logout by a given userId/sportCenterId
- **Security/Caller:** anonymous
- **Request:** logout(userId, sportCenterId)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        +
### DISPLAY INFORMATION:
#### getAvailableSlots
- **Description:** get all available slot for a given day and cityId
- **Security/Caller:** anonymous
- **Request:** getAvailableSlots(day, city)
- **Response:**
    + **Success:** 
        + SuccessCode 
        + slotArray (where Slot structure contains these attributes: sportCenterId, courtId, startTime, endTime)
    + **Error:** 
        +
#### getUserBooking
- **Description:** get all booking of the user by a given userId, city and date
- **Security/Caller:** userId
- **Request:** getUserBooking(userId, city, date)
- **Response:**
    + **Success:** 
        + SuccessCode
        + userBookingArray (where userBooking structure contains these attributes: bookingId, centerId, courtId, startTime, endTime)
    + **Error:**
        +
#### getSportCenterBooking
- **Description:** get all booking of the sportCenter by a given sportCenterId and date
- **Security/Caller:** sportCenterId
- **Request:** getSportCenterBooking(sportCenterId, date)
- **Response:**
    + **Success:**
        + SuccessCode
        + sportCenterBookingArray (where sportCenterBooking structure contains these attributes: bookingId, userId, courtId, date, startTime, endTime)
    + **Error:**
#### getUserInfo
- **Description:** get all information of the user by a given userId
- **Security/Caller:** anonymous
- **Request:** getUserInfo(userId)
- **Response:**
    + **Success:**
        + SuccessCode
        + userInfo (where userInfo structure contains these attributes: userId, firstName, lastName, dateOfBirth, phoneNum)
    + **Error:**
        +
#### getSportCenterInfo
- **Description:** get all information of the sportCenter by a given sportCenterId
- **Security/Caller:** anonymous
- **Request:** getSportCenterInfo(sportCenterId)
- **Response:**
    + **Success:**
        + SuccessCode
        + sportCenterInfo (where sportCenterInfo structure contains these attributes: sportCenterId, name, city, phoneNum, courtIdArray)
    + **Error:**
        +
### PERFORM ACTION:
#### makeBooking
- **Description:** make a booking by a given userId, sportCenterId, courtId, date, startTime, endTime
- **Security/Caller:** userId
- **Request:** makeBooking(userId, sportCenterId, courtId, date, startTime, endTime)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        +
#### cancelBooking
- **Description:** cancel a booking by a given bookingId and sportCenterId
- **Security/Caller:** sportCenterId
- **Request:** cancelBooking(bookingId, sportCenterId)
- **Response:** 
    + **Success:**
        + SuccessCode
    + **Error:**
        +
#### changeBookingState
- **Description:** change a booking state by a given bookingId, sportCenterId, and newState
- **Security/Caller:** sportCenterId
- **Request:** changeBookingState(bookingId, sportCenterId, newState)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        +
### MODIFY INFORMATION:
#### updateUserInfo
- **Description:** update information of a user base on the corresponding parameter and a given userId
- **Security/Caller:** userId
- **Request:** updateUserInfo(userId, firstName/ lastName/ birthday/ phoneNumber)
- **Response:**
    + **Success:**
        SuccessCode
    + **Error:**
        +
#### updateSportCenterInfo
- **Description:** update information of a sport center base on the corresponding parameter and a given sportCenterId
- **Security/Caller:** sportCenterId
- **Request:** updateSportCenterInfo(SportCenterId, name/ city/ phoneNumber)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        +
### FURTHER UPDATE / ADDITIONAL FEATURES:
#### contact
- **Description:** contact a sportCenter/user by a given senderId, receiverId and a message
- **Security/Caller:** userId/sportCenterId
- **Request:** contact(sportCenterId / userId (**senderId**), msg, sportCenterId / userId (**reveiverId**))
- **Response:**
    + **Success:** 
        + SuccessCode
    + **Error:**
        +
#### report
- **Description:** report a user or a sportCenter by a given reporterId and reporteeId and a message
- **Security/Caller:** anonymous
- **Request:** report(sportCenterId / userId (**reporterId**), msg, sportCenterId / userId (**reporteeId**))
 
        