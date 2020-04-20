Error codes
===

Coding conventions:
+ Codes, which are in the format `2xx` where `x` is an arbitrary digit, are success code
+ Codes, which are in the format `40x` where `x` is an arbitrary digit, are code for general errors
+ Codes, which are in the format `41x` where `x` is an arbitrary digit, are code for booking errors
+ Codes, which are in the format `46x` where `x` is an arbitrary digit, are code for invalid parameters errors

| Code | Short description | Long description |
|:----:|-------------------|------------------|
| 200 | SUCCESS | The request is accepted and processed by the database |
| 400 | ERROR FOUND | The request is rejected, because of an error was made on the client |
| 401 | UNAUTHORIZED | The request is rejected, because the requested content is not accessible by the caller of the interface |
| 402 | CITY ID ALREADY EXISTS | The request is rejected, because the provided `cityId` already exists | 
| 403 | SPORTCENTER ID ALREADY EXISTS | The request is rejected, because the provided `sportCenterId` already exists | 
| 404 | COURT ID ALREADY EXISTS | The request is rejected because the provided `courtId` already exists |
| 405 | PLAYER ID ALREADY EXISTS | The request is rejected because the provided `playerId` already exists |
| 406 | STAFF ID ALREADY EXISTS | The request is rejected because the provided `staffId` already exists |
| 407 | BOOKING ID ALREADY EXISTS | The request is rejected because the provided `bookingId` already exists |
| 410 | BOOKINGS LIMIT REACHED | The request is rejected, because the player has reached the maximum number of allowed bookings |
| 411 | BOOKING CANCELLATION REJECTED | The request is rejected, because the player makes the cancellation request in less then *24 hours* before the start time of the booking|
| 412 | UNPAID BOOKING FOUND | The request is rejected, because there exists a booking in the past that has not been paid|
| 413 | OVERLAPPED BOOKING FOUND | The request is rejected, because there exists a booking with overlapping time duration with the new booking|
| 460 | INVALID CITY ID | The request is rejected, because an invalid `cityId` was provided. `cityId` can only contains alphanumeric characters, is limited to 100 characters, and uniquely identifies a city |
| 461 | INVALID SPORTCENTER ID | The request is rejected, because an invalid `sportCenterId` was provided. `sportCenterId` can only contains alphanumeric characters, is limited to 100 characters, and the combination of `cityId + sportCenterId` uniquely identify a sport center |
| 462 | INVALID COURT ID | The request is rejected, because an invalid `courtId` was provided. `courtId` can only contains alphanumeric characters, is limited to 100 characters, and the combination of `cityId + sportCenterId + courtId` uniquely identify a court |
| 463 | INVALID STAFF ID | The request is rejected, because an invalid `staffId` was provided. `staffId` can only contains alphanumeric characters, is limited to 100 characters, and the combination of `cityId + sportCenterId + staffId` uniquely identify a staff |
| 464 | INVALID PLAYER ID | The request is rejected, because an invalid `playerId` was provided. `playerId` can only contains alphanumeric characters, is limited to 100 characters, and uniquely identifies a player  |
| 465 | INVALID BOOKING ID | The request is rejected, because an invalid `bookingId` was provided. `bookingId` can only contains alphanumeric characters, is limited to 100 characters, and uniquely identifies a booking |
| 466 | INVALID DATE | The request is rejected, because an invalid date was provided. A date is invalid if it is earlier than the time instance in which the method is called |
| 467 | INVALID DURATION | The request is rejected, because an invalid booking duration was provided. A duraton is invalid if it is not *45 minutes*, *1 hour*, *1 hour and 15 minutes*, or *1 hour and 30 minutes*. |
| 468 | INVALID START TIME | The request is rejected, because an invalid start time was provided. The start time is invalid if it is not a factor of *15 minutes*, or the start time is not in the working hours of the sport center |
| 469 | INVALID END TIME | The request is rejected, because an invalid end time was provided. The start time is invalid if it is not a factor of *15 minutes*, or the start time is not in the working hours of the sport center |
