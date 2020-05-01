Stored procedures
===

`getCourtBookings(IN inCourtId, IN inCityId, IN inSportCenterId, IN inBookingDate, OUT statusCode)`
---
Query all the attributes of a court booking identified by the given courtId, cityId, sportCenterId, bookingDate.

**Parameter:**
+ `inCourtId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a court in a given sportcenter.
+ `inSportCenterId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a sportcenter in a given city. 
+ `inCityId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a city.
+ `inBookingDate - DATE` is a date value that uniquely identify the booking date
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*
    + *460 - INVALID CITY ID*
    + *461 - INVALID SPORTCENTER ID*
    + *462 - INVALID COURT ID*
    + *466 - INVALID DATE*

**Return** `R(bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, playerId)`

**Return** `R(bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, playerId)`

`getCitySportCenters(IN inCityId, OUT statusCode)`
---
Query all the attributes of a sportcenter identified by the given sportCenterId, cityId.

**Parameter**
+ `inCityId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a city.
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*
    + *460 - INVALID CITY ID*

**Return** `R(sportCenterId, cityId)`

`getPlayerBookings(IN inPlayerId, OUT statusCode)`
---
Query all the attributes of a sportcenter identified by the given playerId.

**Parameter**
+ `inPlayerId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a player.
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    +  *200 - SUCCESS*
    +  *464 - INVALID PLAYER ID*

**Return** `R(bookingId, timeStam, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, playerId)`
 

`getSportcenterBookings(IN inSportCenterId, IN inCityId, IN inBookingDate, OUT statusCode)`
---
Query all the attributes of all bookings of the sportcenter identified by the given sportCenterId, cityId, and bookingDate.

**Parameter**
+ `inSportCenterid - VARCHAR(100)` is an alphanumeric characters sequence that identify a sportcenter in a given city.
+  `inCityId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a city.
+  `inBookingDate - DATE` is a date value that identify the booking date 
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*
    + *460 - INVALID CITY ID*
    + *461 - INVALID SPORTCENTER ID*
    + *466 - INVALID DATE*

**Return** `R(bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, playerId)`

`getSportCenterCourts(IN inSportCenterId, IN inCityId, OUT statusCode)`
---
Query all the court from the sport center identified by the given sportcenterId and cityId.

**Parameter**
+ `inSportCenterId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a sportcenter in a given city.
+ `inCityId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a city.
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*
    + *460 - INVALID CITY ID*
    + *461 - INVALID SPORTCENTER ID*

**Return** `R(courtId, cityId, sportCenterId)`

`getCities(OUT statusCode)`
---
Get all cities 

**Parameter**
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*

**Return** `R(cityId)`

`getCityCourts(IN inCityId, OUT statusCode)`
---
Query all the court from the city identified by the given cityId.

**Parameter**
+ `inCityId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a city.
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*
    + *460 - INVALID CITY ID*

**Return** `R(courtId, cityId, sportCenterId)`

`getPlayerBookingsInCity(IN inPlayerId, IN inCityId, IN inBookingDate, OUT statusCode)`
---
Query all the attributes of all bookings identified by the given playerId, cityId and bookingDate

**Parameter**
+ `inPlayerId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a player.
+ `inCityId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a city.
+ `inBookingDate - DATE` is a date value that identify the booking date.
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*
    + *460 - INVALID CITY ID*
    + *464 - INVALID PLAYER ID*
    + *466 - INVALID DATE*

**Return** `R(bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId, courtId, playerId)`

`createCity(IN inCityId, OUT statusCode)`
---
Create a new city with the given cityId.

**Parameter**
+ `inCityId - VARCHAR(100)` is alphanumeric characters sequence that uniquely identify a city.
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*
    + *460 - INVALID CITY ID*
    + *402 - CITY ALREADY EXISTS*

**Return** `R()`

`createPlayer(IN inPlayerId, OUT statusCode)`
---
Create a new player.

**Parameter**
+ `inPlayerId - VARCHAR(100)` is alphanumeric character sequence that uniquely identify a player.
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*
    + *464 - INVALID PLAYER ID*
    + *405 - PLAYER ID ALREADY EXISTS*

**Return** `R()`

`createCityCenter(IN inSportCenterId, IN inCityId, OUT statusCode)`
---
Create new Sportcenter with the given sportCenterId and cityId.

**Parameter**
+ `inSportCenterId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a sport center in a given city.
+ `inCityId - VARCHAR(100)` is alphanumreic characters sequence that uniquely identify a city.
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*
    + *460 - INVALID CITY ID*
    + *461 - INVALID SPORTCENTER ID*
    + *403 - SPORTCENTER ID ALREADY EXISTS*

**Return** `R()`

`createCityCenterCourt(IN inCourtId, IN inCityId, IN inSportCenterId, OUT statusCode)`
---
Create a new court with the given courId, cityId, sportCenterId

**Parameter**
+ `inCourtId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a court in a sport center.
+ `inSportCenterId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a sport center in a given city.
+ `inCityId - VARCHAR(100)` is alphanumreic characters sequence that uniquely identify a city.
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*
    + *462 - INVALID COURT ID*
    + *460 - INVALID CITY ID*
    + *461 - INVALID SPORTCENTER ID*
    + *404 - COURT ID ALREADY EXISTS*

**Return** `R()`

`createStaff(IN inStaffId, IN inCityId, IN inSportCenterId, OUT statusCode)`
---
Create a new staff with given cityId, sportCenterId.

**Parameter**
+ `inStaffId - VARCHAR(100)` is an alphanumeric character sequence that uniquely identify a staff.
+ `inSportCenterId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a sport center in a given city.
+ `inCityId - VARCHAR(100)` is alphanumreic characters sequence that uniquely identify a city.
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*
    + *463 - INVALID STAFF ID*
    + *460 - INVALID CITY ID*
    + *461 - INVALID SPORTCENTER ID*
    + *406 - STAFFID ALREADY EXISTS*

**Return** `R()`

`createBooking(IN inTimeStamp, IN inDate, IN inStartTime, IN inEndTime, IN inCityId, IN inSportCenterId, IN inCourtId, IN inplayerId, OUT statusCode)`
---
Create a new booking 

**Parameter**
+ `inTimeStamp - TIMESTAMP` is a date and time sequence that uniquely identify the booking date and time.
+ `inDate - DATE` is a date sequence that identify the playing date.
+ `inStartTime - TIME` is a time sequence that identify the start time of the playing time.
+ `inEndtime - TIME` is a time sequence that identify the end time of the playing time.
+ `inSportCenterId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a sport center in a given city.
+ `inCityId - VARCHAR(100)` is alphanumreic characters sequence that uniquely identify a city.
+ `inCourtId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a court in a sport center.
+ `inPlayerId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a player.
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*
    + *465 - INVALID BOOKING ID*
    + *466 - INVALID DATE*
    + *468 - INVALID START TIME*
    + *469 - INVALID END TIME*
    + *467 - INVALID DURATION*
    + *407 - BOOKING ALREADY EXISTS*
    + *464 - INVALID PLAYER ID*
    + *460 - INVALID CITY ID*
    + *461 - INVALID SPORTCENTER ID*
    + *462 - INVALID COURT ID*
    + *410 - BOOKINGS LIMIT REACHED*
    + *412 - UNPAID BOOKING FOUND*
    + *413 - OVERLAPPED BOOKING FOUND*

**Return** `R()`

`updateBookingStatus(IN inStatus, IN inBookingId, IN inCityId, IN inSportCenterId, IN inStaffId, OUT statuscode)`
---
Update new booking status with given status, bookingId, cityId, sportCenterId and staffId.

**Parameter**
+ `inStatus - BOOLEAN` is result of comparison operator, either TRUE - isPaid or False - isNotpaid
+ `inBookingId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a booking.
+ `inCityId - VARCHAR(100)` is an alphanumeric characters sequcence that uniquely identify a city.
+ `inSportCenterId - VARCHAR(100)` is an alphanumeric characters sequcence that uniquely identify a sport center in a city. 
+ `inStaffId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a staff.
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*
    + *460 - INVALID CITY ID*
    + *461 - INVALID SPORT CENTER ID*
    + *465 - INVALID BOOKING ID*
    + *463 - INVALID STAFF ID*
    + *401 - *UNAUTHORIZED*

**Return** `R()`

`updateCourtId(IN newCourtId, IN inCourtId, inCityId, inSportCenterId, OUT statusCode)`
---
Update a new courtId with given new courtId.

**Parameter**
+ `newCourtId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a new court.
+ `inCourtId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a court which wants to be updated.
+ `inCityId - VARCHAR(100)` is an alphanumeric characters sequcence that uniquely identify a city.
+ `inSportCenterId - VARCHAR(100)` is an alphanumeric characters sequcence that uniquely identify a sport center in a city.
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*
    + *462 - INVALID COURT ID*
    + *460 - INVALID CITY ID*
    + *461 - INVALID SPORTCENTER ID*
    + *462 - INVALID COURT ID*
    + *404 - COURT ID ALREADY EXISTS*
  
**Return** `R()`

`updatePlayerId(IN newplayerId, IN inPlayerId, OUT statusCode)`
---
Update a new player id

**Parameter**
+ `newPlayerId - VARCHAR(100)` is an alphanumeric characters sequcence that uniquely identify a player.
+ `inPlayerId - VARCHAR(100)` is an alphanumeric characters sequcence that uniquely identify a player.
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*
    + *464 - INVALID PLAYER ID*
    + *405 - PLAYER ID ALREADY EXISTS*

**Return** `R()`

`updateSportcenterId(IN newSportCenterid, IN inSportCenterId, IN inCityId, OUT statusCode)`
---
Update a new sportcenter id with given new sportcenter id.

**Parameter**
+ `newSportCenterId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a new sportcenter. 
+ `inSportCenterId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a sportcenter which wants to be updated.
+ `inCityId - VARCHAR(100)` is aan alphanumeric characters sequence that uniquely identify a city.
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*
    + *461 - INVALID SPORTCENTER ID*
    + *460 - INVALID CITY ID*
    + *403 - SPORTCENTER ID ALREADY EXISTS*
  
**Return** `R()`

`updateStaffId(IN newStaffId, IN inStaffId, IN inCityId, IN inSportCenterId, OUT statusCode)`
---
Update a new staff with a given new staff id.

**Parameter**
+ `newStaffId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a new staff.
+ `inStaffId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a staff.
+ `inCityId - VARCHAR(100)` is aan alphanumeric characters sequence that uniquely identify a city.
+ `inSportCenterId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a sportcenter.
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*
    + *463 - INVALID STAFF ID*
    + *460 - INVALID CITY ID*
    + *461 - INVALID SPORTCENTER ID*
    + *406 - STAFF ID ALREADY EXISTS*
  
**Return** `R()`

`cancelBooking(IN inBookingId, IN inPlayerId, OUT statusCode)`
---
Cancel an existed booking in the database.

**Parameter**
+ `inBookingId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a booking.
+ `inPlayerId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a player.
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution:
    + *200 - SUCCESS*
    + *464 - INVALID PLAYER ID*
    + *465 - INVALID BOOKING ID*
    + *401 - UNAUTHORIZED*
    + *411 - BOOKING CANCELLATION REJECTED*

**Return** `R()`