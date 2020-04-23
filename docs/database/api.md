Stored procedures
===

`getBookingInfo(IN inBookingId, OUT statusCode)`
---
Query all the attributes of a single booking identitfied by the given booking id

**Parameters:**
+ `inBookingId - VARCHAR(100)` is an alphanumeric chararacters sequence that uniquely identitfy a booking
+ `statusCode - INT` is set by the database and indicates whether any error happened during the execution
	+ *200 - SUCCESS*
	+ *465 - INVALID BOOKING ID*

**Return:** `R(bookingId, createdAt, bookingDate, bookingStartTime, bookingEndTime, isPaid, cityId, sportCenterId,courtId, playerId)`

`getSportCenterInfo(IN inSportCenterId, IN inCityId, OUT statusCode)`
---
Query all the attributes of a single sport center identitfied by the given sportCenterId and cityId

**Parameters:**
+ `inSportCenterId - VARCHAR(100)` is an alphanumeric chararacters sequence that uniquely identitfy a sport center in a given city
+ `inCityId - VARCHAR(100)` is an alphanumeric chararacters sequence that uniquely identitfy a city
+ `statusCode - INT` is set by the data and indicates whether any error happended during the execution
	+ *200 - SUCCESS*
	+ *460 - INVALID CITY ID*
	+ *461 - INVALID SPORT CENTER ID*

**Return:** `R(sportCenterId, cityId)`

`getPlayerBookings`
---

`getPlayerInfo`
---

`getCourtBookings`
---

`getBookings`
---

`getCitySportCenters`
---

`getSportcenterBookings`
---

`getCourtInfo`
---

`getSportCenterCourts`
---

`getCities`
---

`getCityCourts`
---

`getPlayerBookingsInCity`
---

`getStaffInfo`
---

`createCity`
---

`createPlayer`
---

`createCityCenter`
---

`createCityCenterCourt`
---

`createStaff`
---

`createBooking`
---

`updateBookingStatus`
---

`updateCourtId`
---

`updatePlayerId`
---

`updateSportcenterId`
---

`updateStaffId`
---

`cancelBooking`
---
