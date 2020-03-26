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
- **Security/Caller:** anonymous
- **Request:** loginStaff(staffName, password) / loginUser(userName, password)
- **Response:** 
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
#### logout
- **Description:** User logout from a server
- **Security/Caller:** userId/ staffId
- **Request:** logout(userId/staffId)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
### DISPLAY INFORMATION:
#### getBooking
- **Description:** request database for all booking for a given city and a given date (**logic layer will extract from that data all available slot**)
- **Security/Caller:** userId
- **Request:** getBooking(cityId, date)
- **Response:** 
    + **Success:**
        + SuccessCode
        + bookingTable (contains these columns: bookingId, sportCenterId, courtId, startTime, endTime)
    + **Error:**
        + errorCode
        
#### getUserBooking
 - **Description:** request database for all booking of a user by a given city and date
 - **Security/Caller:** userId
 - **Request:** getUserBooking(userId, cityId, date)
 - **Response:**
     + **Success:**
         + SuccessCode
         + bookingTable (contains these columns: bookingId, sportCenterId, courtId, startTime, endTime)
     + **Error:**
         + errorCode
         
#### getSportCenterBooking
- **Description:** request database for all booking of a sportCenter by a given date
- **Security/Caller:** staffId
- **Request:** getSportCenterBooking(staffId, date)
- **Response:**
    + **Success:**
        + SuccessCode
        + bookingTable (contains these columns: bookingId, userId, courtId, startTime, endTime)
    + **Error:**
        + errorCode
        
#### getUserInfo
- **Description:** request database for all information of a user by a given userId
- **Security/Caller:** userId
- **Request:** getUserInfo(userId)
- **Response:**
    + **Success:**
        + SuccessCode
        + userInfoTable(contains these columns: firstName, lastName, dateOfBirth, phoneNumber)
    + **Error:**
        + errorCode
        
#### getSportCenterInfo
- **Description:** request database for all information of sportCenter by a given sportCenterId
- **Security/Caller:** staffId
- **Request:** getSportCenterInfo(sportCenterId)
- **Response:**
    + **Success:**
        + SuccessCode
        + sportCenterInfoTable(contains these columns: name, city, courtId)
    + **Error:**
        + errorCode
        
### PERFORM ACTION:
#### makeBooking
- **Description:** update to database new booking by a given userId, sportCenterId, courtId, date, startTime, endTime)
- **Security/Caller:** userId
- **Request:** makeBooking(userId, sportCenterId, courtId, date, startTime, endTime)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
#### cancelBooking
- **Description:** delete from database a existed booking by a given bookingId
- **Security/Caller:** userId
- **Request:** cancelBooking(bookingId)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
#### changeBookingState
- **Description:** update to database a new booking state by a given bookingId and a state
- **Security/Caller:** staffId
- **Request:** changeBookingState(bookingId, state)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
### MODIFY INFORMATION
#### updateUserInfo (updateFirstName, updateLastName, updateBirthday, updatePhoneNumber)
- **Description:** update to database a new piece information base on the corresponding parameter and userId
- **Security/Caller:** userId
- **Request:** updateFirstName(userId, firstName)/ updateLastName(userId, lastName)/ updateBirthday(userId, birthday)/ updatePhoneNum(userId, phoneNum)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode

#### updateSportCenterInfo (updateName, updateCity, updatePhoneNumber)
- **Description:** update to database a new piece information base on the corresponding parameter and sportCenterId
- **Security/Caller:** staffId
- **Request:** updateName(sportCenterId, name)/ updateCity(sportCenterId, city)/ updatePhoneNumber(sportCenterId, phoneNum)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
### FURTHER UPDATE / ADDITIONAL FEATURES:
#### contact
- **Description:** update to database a new message by a given senderId, receiverId and a message
- **Security/Caller:** userId/staffId
- **Request:** contact(sportCenterId / userId (**senderId**), msg, sportCenterId / userId (**reveiverId**))
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
#### report
- **Description:** update to database a new report by a given reporterId and reporteeId and a message
- **Security/Caller:** userId/staffId
- **Request:** report(sportCenterId / userId (**reporterId**), msg, sportCenterId / userId (**reporteeId**))
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        