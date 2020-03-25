#### Agreement Presentation-Logic
- Every cityId, sportCenterId, courtId, bookingId is unique globally
- Each booking will have 4 statusId code: Pending(0), Confirmed(1), Unpaid(2), Paid(3) 
- Every list or array will be ordered in either Alphabetical order or Numerical order. Booking list will be ordered by time order of startTime

## LOGIC-PRESENTATION INTERFACES
### LOGIN-LOGOUT:
#### login
- **Description:** login by a given userName and password
- **Security/Caller:** anonymous
- **Request:** login(userName, password)
- **Response:**
    + **Success:**
        + SuccessCode(0)
    + **Error:**
        + errorCode(-1)
        
### logout
- **Description:** logout by a given userId
- **Security/Caller:** userId
- **Request:** logout(userId)
- **Response:**
    + **Success:**
        + SuccessCode(0)
    + **Error:**
        + errorCode(-1)
        
### DISPLAY INFORMATION:
#### getCityList:
- **Description**: get all city name and ID
- **Caller**: callerId
- **Request**: getCityList()
- **Response**: 
    + **Success**: 
        + SuccessCode(0)
        + array of City, where City is a array of (cityId, cityName)
    + **Error**: 
        + errorCode(-1)
    
#### getAvailableSlots
- **Description:** get all available slot for a given day and cityId
- **Security/Caller:** userId
- **Request:** getAvailableSlots(day, cityId)
- **Response:**
    + **Success:** 
        + SuccessCode(0)
        + slotArray (where Slot structure contains these attributes: sportCenterId, courtId, startTime, endTime)
    + **Error:** 
        + errorCode(-1)
        
#### getUserBooking
- **Description:** get all booking of the user by a given userId and date
- **Security/Caller:** userId
- **Request:** getUserBooking(userId, date)
- **Response:**
    + **Success:** 
        + SuccessCode(0)
        + userBookingArray (where userBooking structure contains these attributes: bookingId, courtId, startTime, endTime, statusId)
    + **Error:**
        + errorCode(-1)
        
#### getUserBookingInCity
- **Description:** get all booking of the user by a given userId in particular cityId and date
- **Security/Caller:** userId
- **Request:** getUserBookingInCity(userId, cityId, date)
- **Response:**
    + **Success:** 
        + SuccessCode(0)
        + userBookingArray (where userBooking structure contains these attributes: bookingId, courtId, startTime, endTime, statusId)
    + **Error:**
        + errorCode(-1)    
        
#### getSportCenterBooking
- **Description:** get all booking in the sportCenter by a given sportCenterId and date
- **Security/Caller:** userId
- **Request:** getSportCenterBooking(sportCenterId, date)
- **Response:**
    + **Success:**
        + SuccessCode(0)
        + sportCenterBookingArray (where sportCenterBooking structure contains these attributes: bookingId, userId, courtId, date, startTime, endTime, statusId)
    + **Error:**
        + errorCode(-1)

#### getUserInfo
- **Description:** get all information of the user by a given userId
- **Security/Caller:** userId
- **Request:** getUserInfo(userId)
- **Response:**
    + **Success:**
        + SuccessCode(0)
        + userInfo (where userInfo structure contains these attributes: firstName, lastName, dateOfBirth, phoneNum)
    + **Error:**
        + errorCode(-1)
        
#### getSportCenterInfo
- **Description:** get all information of the sportCenter by a given sportCenterId
- **Security/Caller:** userId
- **Request:** getSportCenterInfo(sportCenterId)
- **Response:**
    + **Success:**
        + SuccessCode(0)
        + sportCenterInfo (where sportCenterInfo structure contains these attributes: name, city, address, phoneNum, courtIdArray)
    + **Error:**
        + errorCode(-1)

#### getBookingInfo:
- **Description**: display all information of booking by given bookingId
- **Caller**: callerId
- **Request**: getBookingInfo(bookingId)
- **Response**: 
    + **Success**: 
        + SuccessCode(0)
        + array of (startTime, endTime, bookingUser, statusId)
    - **Error**: 
        + errorCode(-1)

### PERFORM ACTION:
#### makeBooking
- **Description:** make a booking by a given userId, courtId, date, startTime, endTime
- **Security/Caller:** userId
- **Request:** makeBooking(userId, courtId, date, startTime, endTime)
- **Response:**
    + **Success:**
        + SuccessCode(0)
    + **Error:**
        + errorCode(-1)
        
#### cancelBooking
- **Description:** cancel a booking by a given bookingId and sportCenterId
- **Security/Caller:** userId
- **Request:** cancelBooking(bookingId)
- **Response:** 
    + **Success:**
        + SuccessCode(0)
    + **Error:**
        + errorCode(-1)
        
#### changeBookingState
- **Description:** change a booking state by a given bookingId
- **Security/Caller:** userId
- **Request:** changeBookingState(bookingId, statusId)
- **Response:**
    + **Success:**
        + SuccessCode(0)
    + **Error:**
        + errorCode(-1)
        
### MODIFY INFORMATION:
#### updateUserInfo
- **Description:** update information of a user base on the corresponding parameter and a given userId
- **Security/Caller:** userId
- **Request:** updateUserInfo(userId, firstName/ lastName/ birthday/ phoneNumber)
- **Response:**
    + **Success:**
        + SuccessCode(0)
    + **Error:**
        + errorCode(-1)
        
#### updateSportCenterInfo
- **Description:** update information of a sport center base on the corresponding parameter and a given sportCenterId
- **Security/Caller:** userId
- **Request:** updateSportCenterInfo(SportCenterId, name/ city/ phoneNumber/ courtIdArray)
- **Response:**
    + **Success:**
        + SuccessCode(0)
    + **Error:**
        + errorCode(-1)
        
### FURTHER UPDATE / ADDITIONAL FEATURES:
#### contact
- **Description:** contact a sportCenter/user by a given senderId, receiverId and a message
- **Security/Caller:** userId
- **Request:** contact(sportCenterId / userId (**senderId**), msg, sportCenterId / userId (**reveiverId**))
- **Response:**
    + **Success:**
        + SuccessCode(0)
    + **Error:**
        + errorCode(-1)
        
#### report
- **Description:** report a user or a sportCenter by a given reporterId and reporteeId and a message
- **Security/Caller:** userId
- **Request:** report(sportCenterId / userId (**reporterId**), msg, sportCenterId / userId (**reporteeId**))
- **Response:**
    + **Success:**
        + SuccessCode(0)
    + **Error:**
        + errorCode(-1)
 
        
