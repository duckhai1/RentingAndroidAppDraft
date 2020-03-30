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
- **Description:** request database for a given playerName and hashed password, check whether it is correct or not
- **Security/Caller:** anonymous
- **Request:** loginStaff(staffName, password) / loginPlayer(playerName, password)
- **Response:** 
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
#### logout
- **Description:** Player logout from a server
- **Security/Caller:** playerId/ staffId
- **Request:** logout(playerId/staffId)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
### DISPLAY INFORMATION:
#### getBooking
- **Description:** request database for all booking for a given city and a given date (**logic layer will extract from that data all available slot**)
- **Security/Caller:** playerId
- **Request:** getBooking(cityId, date)
- **Response:** 
    + **Success:**
        + SuccessCode
        + bookingTable (contains these columns: bookingId, sportCenterId, courtId, startTime, endTime)
    + **Error:**
        + errorCode
        
#### getPlayerBooking
 - **Description:** request database for all booking of a player by a given city and date
 - **Security/Caller:** playerId
 - **Request:** getPlayerBooking(playerId, cityId, date)
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
        + bookingTable (contains these columns: bookingId, playerId, courtId, startTime, endTime)
    + **Error:**
        + errorCode
        
#### getPlayerInfo
- **Description:** request database for all information of a player by a given playerId
- **Security/Caller:** playerId
- **Request:** getPlayerInfo(playerId)
- **Response:**
    + **Success:**
        + SuccessCode
        + playerInfoTable(contains these columns: firstName, lastName, dateOfBirth, phoneNumber)
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
- **Description:** update to database new booking by a given playerId, sportCenterId, courtId, date, startTime, endTime)
- **Security/Caller:** playerId
- **Request:** makeBooking(playerId, sportCenterId, courtId, date, startTime, endTime)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
#### cancelBooking
- **Description:** delete from database a existed booking by a given bookingId
- **Security/Caller:** playerId
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
#### updatePlayerInfo (updateFirstName, updateLastName, updateBirthday, updatePhoneNumber)
- **Description:** update to database a new piece information base on the corresponding parameter and playerId
- **Security/Caller:** playerId
- **Request:** updateFirstName(playerId, firstName)/ updateLastName(playerId, lastName)/ updateBirthday(playerId, birthday)/ updatePhoneNum(playerId, phoneNum)
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
- **Security/Caller:** playerId/staffId
- **Request:** contact(sportCenterId / playerId (**senderId**), msg, sportCenterId / playerId (**reveiverId**))
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
#### report
- **Description:** update to database a new report by a given reporterId and reporteeId and a message
- **Security/Caller:** playerId/staffId
- **Request:** report(sportCenterId / playerId (**reporterId**), msg, sportCenterId / playerId (**reporteeId**))
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        