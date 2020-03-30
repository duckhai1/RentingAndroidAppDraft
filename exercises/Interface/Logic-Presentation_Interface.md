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
- **Description:** login by a given playerName and password
- **Security/Caller:** anonymous
- **Request:** loginStaff(staffName, password) / loginPlayer(playerName, password)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
### logout
- **Description:** logout by a given playerId or staffId
- **Security/Caller:** playerId/ staffId
- **Request:** logout(playerId)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
### DISPLAY INFORMATION:
#### getCityList:
- **Description**: get all city name and ID
- **Caller**: playerId
- **Request**: getCityList()
- **Response**: 
    + **Success**: 
        + SuccessCode
        + City array (where Cit structure contains these attributes: (cityId, cityName)
    + **Error**: 
        + errorCode
    
#### getAvailableSlots
- **Description:** get all available slot for a given day and cityId
- **Security/Caller:** playerId
- **Request:** getAvailableSlots(day, cityId)
- **Response:**
    + **Success:** 
        + SuccessCode
        + slotArray (where Slot structure contains these attributes: sportCenterId, courtId, startTime, endTime)
    + **Error:** 
        + errorCode        
        
#### getPlayerBooking
- **Description:** get all booking of the player by a given playerId in particular cityId and date
- **Security/Caller:** playerId
- **Request:** getPlayerBookingInCity(playerId, cityId, date)
- **Response:**
    + **Success:** 
        + SuccessCode
        + playerBookingArray (where playerBooking structure contains these attributes: bookingId, courtId, startTime, endTime, state)
    + **Error:**
        + errorCode    
        
#### getSportCenterBooking
- **Description:** get all booking in the sportCenter by a given sportCenterId and date
- **Security/Caller:** staffId
- **Request:** getSportCenterBooking(sportCenterId, date)
- **Response:**
    + **Success:**
        + SuccessCode
        + sportCenterBookingArray (where sportCenterBooking structure contains these attributes: bookingId, playerId, courtId, date, startTime, endTime, state)
    + **Error:**
        + errorCode

#### getPlayerInfo
- **Description:** get all information of the player by a given playerId
- **Security/Caller:** playerId
- **Request:** getPlayerInfo(playerId)
- **Response:**
    + **Success:**
        + SuccessCode
        + playerInfo (where playerInfo structure contains these attributes: firstName, lastName, dateOfBirth, phoneNum)
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
        + booking (where booking structure contains these attributes playerId, sportCenterId, courtId, date startTime, endTime, state)
    - **Error**: 
        + errorCode

### PERFORM ACTION:
#### makeBooking
- **Description:** make a booking by a given playerId, courtId, date, startTime, endTime
- **Security/Caller:** playerId
- **Request:** makeBooking(playerId, courtId, date, startTime, endTime)
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
#### cancelBooking
- **Description:** cancel a booking by a given bookingId and sportCenterId
- **Security/Caller:** playerId
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
#### updatePlayerInfo (updateFirstName, updateLastName, updateBirthday, updatePhoneNumber)
- **Description:** update information of a player base on the corresponding parameter and a given playerId
- **Security/Caller:** playerId
- **Request:** updateFirstName(playerId, firstName)/ updateLastName(playerId, lastName)/ updateBirthday(playerId, birthday)/ updatePhoneNum(playerId, phoneNum)
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
- **Description:** contact a sportCenter/player by a given senderId, receiverId and a message
- **Security/Caller:** playerId/ staffId
- **Request:** contact(sportCenterId / playerId (**senderId**), msg, sportCenterId / playerId (**reveiverId**))
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
        
#### report
- **Description:** report a player or a sportCenter by a given reporterId and reporteeId and a message
- **Security/Caller:** playerId/ staffId
- **Request:** report(sportCenterId / playerId (**reporterId**), msg, sportCenterId / playerId (**reporteeId**))
- **Response:**
    + **Success:**
        + SuccessCode
    + **Error:**
        + errorCode
 
        
