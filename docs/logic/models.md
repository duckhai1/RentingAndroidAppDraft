Java Models
===

`getCourtBooking(String courtId, String cityId, String sportCenterId, Date date)`
---
Return a collection of bookings that were made with the given court

**Parameter** 
+ `courtId - VARCHAR(100)` is a sequence of characters that uniquely identify the booking Id.
+ `cityId - VARCHAR(100)` is a sequence of characters that uniquely identifies the city.
+ `sportCenterId - VARCHAR(100)` is a sequence of characters id that uniquely identifies the sport center in the given city.
+ `date - DATE` the booking date of all the bookings in the collection

**Error Code**
+ if (statusCode >= 400 and statusCode < 500) throws MySQLException(statusCode) 
+ catch SQLException throws MySQLException

**Return** `Collection<Booking>`

`getSportCenterBooking(String sportCenterId, String cityId, Date date)`
--- 
Return a collection of bookings that were made with the given sport center

**Parameter** 
+ `sportCenterId - VARCHAR(100)` is a sequence of characters that uniquely identifies the sport center in the given city.
+ `cityID - VARCHAR(100)` is a sequence of characters that uniquely identify the booking Id.
+ `date - DATE` the booking date of all the bookings in the collection

**Error Code** 
+ if (statusCode >= 400 and statusCode < 500) throws MySQLException(statusCode) 
+ catch SQLException throws MySQLException

**Return** `Collection<Booking>`

`getPlayerBooking(String playerId)`
---
Return a collection of bookings that were made by player

**Parameter** 
+ `playerId - VARCHAR(100)` is a sequence of characters that uniquely identifies the player

**Error Code** 
+ if (statusCode >= 400 and statusCode < 500) throws MySQLException(statusCode) 
+ catch SQLException throws MySQLException

**Return** `Collection<Booking>`

`getPlayerBookingInCity(String playerId, String cityId, Date date)`
--- 
Return a collection of bookings that were made by the player in city

**Parameter** 
+ `playerId - VARCHAR(100)` is a sequence of characters that uniquely identifies the player
+ `cityID - VARCHAR(100)` is a sequence of characters that uniquely identify the booking Id.
+ `date - DATE` the booking date of all the bookings in the collection

**Error Code** 
+ if (statusCode >= 400 and statusCode < 500) throws MySQLException(statusCode) 
+ catch SQLException throws MySQLException

**Return** `Collection<Booking>`

`createBooking(String bookingId, Timestamp timestamp, Date date, Time startTime, Time endTime, String cityId, String sportCenterId, String courtId, String playerId)`
---
Create a new booking

**Parameter** 
+ `bookingId - VARCHAR(100)` is a sequence of characters that uniquely identifies the booking 
+ `timestamp - TIMESTAMP` is a date and time sequence that uniquely identify the booking date and time
+ `date - DATE` is a date sequence that identify the playing date.
+ `startTime - TIME` is a time sequence that identify the start time of the playing time.
+ `endtime - TIME` is a time sequence that identify the end time of the playing time.
+ `sportCenterId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a sport center in a given city.
+ `cityId - VARCHAR(100)` is alphanumreic characters sequence that uniquely identify a city.
+ `inCourtId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a court in a sport center.
+ `playerId - VARCHAR(100)` is a sequence of characters that uniquely identifies the player

**Error Code** 
+ if (statusCode >= 400 and statusCode < 500) throws MySQLException(statusCode) 
+ catch SQLException throws MySQLException

**Return** `Collection<Booking>`

`updateBookingStatus(Boolean status, String bookingId, String cityId, String sportCenterId, String staffId)`
---
Update the status of the give booking, the staff must be authorized to perform this action

**Parameter**
+ `status - BOOLEAN` is result of comparison operator, either TRUE - isPaid or False - isNotpaid
+ `bookingId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a booking.
+ `cityId - VARCHAR(100)` is an alphanumeric characters sequcence that uniquely identify a city.
+ `sportCenterId - VARCHAR(100)` is an alphanumeric characters sequcence that uniquely identify a sport center in a city. 
+ `staffId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a staff.

**Error Code** 
+ if (statusCode >= 400 and statusCode < 500) throws MySQLException(statusCode) 
+ catch SQLException throws MySQLException

**Return** `Collection<Booking>`

`cancelBooking(String bookingId, String playerId)`
--- 
Remove the given booking from the database

**Parameter** 
+ `bookingId - VARCHAR(100)` is an alphanumeric characters sequence that uniquely identify a booking.
+ `playerId - VARCHAR(100)` is a sequence of characters that uniquely identifies the player

**Error Code** 
+ if (statusCode >= 400 and statusCode < 500) throws MySQLException(statusCode) 
+ catch SQLException throws MySQLException

**Return** `Collection<Booking>`
