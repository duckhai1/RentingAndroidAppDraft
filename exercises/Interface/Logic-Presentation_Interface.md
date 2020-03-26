#### Agreement Presentation-Logic
- Every cityId, sportCenterId, courtId, bookingId is unique globally
- Each booking will have 4 state code: 
    - Upcoming(0): upcoming booking 
    - Unpaid(1): Passed booking but unpaid
    - Paid(2): Paid booking
    - Cancelled(3): Cancelled booking
- Every list or array will be ordered in either Alphabetical order or Numerical order. Booking list will be ordered by time order of startTime

## LOGIC-PRESENTATION INTERFACES
### LOGIN-LOGOUT:
#### login
- **Description:** login by a given userName and password
- **Security/Caller:** anonymous
- **Request:** loginStaff(staffName, password) / loginUser(userName, password)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
### logout
- **Description:** logout by a given userId or staffId
- **Security/Caller:** userId/ staffId
- **Request:** logout(userId)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
### DISPLAY INFORMATION:
#### getCityList:
- **Description**: get all city name and ID
- **Caller**: userId
- **Request**: getCityList()
- **Response**: 
    + **Success**: 
        + SuccessCode
        + City array (where Cit structure contains these attributes: (cityId, cityName)
    + **Error**: 
        + errorCode
    
#### getAvailableSlots
- **Description:** get all available slot for a given day and cityId
- **Security/Caller:** userId
- **Request:** getAvailableSlots(day, cityId)
- **Response:**
    + **Success:** 
        + SuccessCode
        + slotArray (where Slot structure contains these attributes: sportCenterId, courtId, startTime, endTime)
    + **Error:** 
        + errorCode        
        
#### getUserBooking
- **Description:** get all booking of the user by a given userId in particular cityId and date
- **Security/Caller:** userId
- **Request:** getUserBookingInCity(userId, cityId, date)
- **Response:**
    + **Success:** 
        + SuccessCode
        + userBookingArray (where userBooking structure contains these attributes: bookingId, courtId, startTime, endTime, state)
    + **Error:**
        + errorCode    
        
#### getSportCenterBooking
- **Description:** get all booking in the sportCenter by a given sportCenterId and date
- **Security/Caller:** staffId
- **Request:** getSportCenterBooking(sportCenterId, date)
- **Response:**
    + **Success:**
        + SuccessCode
        + sportCenterBookingArray (where sportCenterBooking structure contains these attributes: bookingId, userId, courtId, date, startTime, endTime, state)
    + **Error:**
        + errorCode

#### getUserInfo
- **Description:** get all information of the user by a given userId
- **Security/Caller:** userId
- **Request:** getUserInfo(userId)
- **Response:**
    + **Success:**
        + SuccessCode
        + userInfo (where userInfo structure contains these attributes: firstName, lastName, dateOfBirth, phoneNum)
    + **Error:**
        + errorCode
        
#### getSportCenterInfo
- **Description:** get all information of the sportCenter by a given sportCenterId
- **Security/Caller:** staffId
- **Request:** getSportCenterInfo(sportCenterId)
- **Response:**
    + **Success:**
        + SuccessCode
        + sportCenterInfo (where sportCenterInfo structure contains these attributes: name, city, address, phoneNum, courtIdArray)
    + **Error:**
        + errorCode

#### getBookingInfo:
- **Description**: display all information of booking by given bookingId
- **Caller**: callerId
- **Request**: getBookingInfo(bookingId)
- **Response**: 
    + **Success**: 
        + SuccessCode
        + booking (where booking structure contains these attributes userId, sportCenterId, courtId, date startTime, endTime, state)
    - **Error**: 
        + errorCode

### PERFORM ACTION:
#### makeBooking
- **Description:** make a booking by a given userId, courtId, date, startTime, endTime
- **Security/Caller:** userId
- **Request:** makeBooking(userId, courtId, date, startTime, endTime)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
#### cancelBooking
- **Description:** cancel a booking by a given bookingId and sportCenterId
- **Security/Caller:** userId
- **Request:** cancelBooking(bookingId)
- **Response:** 
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
#### changeBookingState
- **Description:** change a booking state by a given bookingId
- **Security/Caller:** staffId
- **Request:** changeBookingState(bookingId, state)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
### MODIFY INFORMATION:
#### updateUserInfo (updateFirstName, updateLastName, updateBirthday, updatePhoneNumber)
- **Description:** update information of a user base on the corresponding parameter and a given userId
- **Security/Caller:** userId
- **Request:** updateFirstName(userId, firstName)/ updateLastName(userId, lastName)/ updateBirthday(userId, birthday)/ updatePhoneNum(userId, phoneNum)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
#### updateSportCenterInfo (updateName, updateCity, updatePhoneNumber)
- **Description:** update information of a sport center base on the corresponding parameter and a given sportCenterId
- **Security/Caller:** staffId
- **Request:** updateName(sportCenterId, name)/ updateCity(sportCenterId, city)/ updatePhoneNumber(sportCenterId, phoneNum)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
### FURTHER UPDATE / ADDITIONAL FEATURES:
#### contact
- **Description:** contact a sportCenter/user by a given senderId, receiverId and a message
- **Security/Caller:** userId/ staffId
- **Request:** contact(sportCenterId / userId (**senderId**), msg, sportCenterId / userId (**reveiverId**))
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
#### report
- **Description:** report a user or a sportCenter by a given reporterId and reporteeId and a message
- **Security/Caller:** userId/ staffId
- **Request:** report(sportCenterId / userId (**reporterId**), msg, sportCenterId / userId (**reporteeId**))
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
 
        
