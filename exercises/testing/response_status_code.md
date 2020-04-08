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
| 400 | ERROR FOUND | The request is rejected, because of an error was made on the client |
| 401 | UNAUTHORIZED | The request is rejected, because the requested content is not accessible by the caller of the interface |
| 402 | CITY ID ALREADY EXISTS | The request is rejected, because the provided city id already exists | 
| 403 | SPORTCENTER ID ALREADY EXISTS | The request is rejected, because the provided sportcenter id already exists | 
| 404 | COURT ID ALREADY EXISTS | The request is rejected because the provided court id already exists |
| 405 | PLAYER ID ALREADY EXISTS | The request is rejected because the provided player id already exists |
| 406 | STAFF ID ALREADY EXISTS | The request is rejected because the provided staff id already exists |
| 407 | BOOKING ID ALREADY EXISTS | The request is rejected because the provided booking id already exists |
| 410 | BOOKINGS LIMIT REACHED | The request is rejected, because the player has reached the maximum number of allowed bookings |
| 411 | BOOKING CANCELLATION REJECTED | The request is rejected, because the player makes the cancellation request in less the 24 hours before the start time of the booking|
| 412 | UNPAID BOOKING FOUND | The request is rejected, because there exists a booking in the past that has not been paid|
| 413 | OVERLAPPED BOOKING FOUND | The request is rejected, because there exists a booking with overlapping time duration with the new booking|
| 460 | INVALID CITY ID | The request is rejected, because an invalid city id was provided |
| 461 | INVALID SPORTCENTER ID | The request is rejected, because an invalid sportcenter id was provided |
| 462 | INVALID COURT ID | The request is rejected, because an invalid court id was provided |
| 463 | INVALID STAFF ID | The request is rejected, because an invalid staff id was provided |
| 464 | INVALID PLAYER ID | The request is rejected, because an invalid player id was provided |
| 465 | INVALID BOOKING ID | The request is rejected, because an invalid booking id was provided |
| 466 | INVALID DATE | The request is rejected, because an invalid date was provided |
| 467 | INVALID DURATION | The request is rejected, because an invalid booking duration was provided |
| 468 | INVALID START TIME | The request is rejected, because an invalid start time was provided |
| 469 | INVALID END TIME | The request is rejected, because an invalid end time was provided |
