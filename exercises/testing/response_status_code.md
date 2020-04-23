Error codes
===

Coding conventions:
+ Codes, which are in the format `2xx` where `x` is an arbitrary digit, are success code
+ Codes, which are in the format `40x` where `x` is an arbitrary digit, are code for general errors
+ Codes, which are in the format `41x` where `x` is an arbitrary digit, are code for booking errors
+ Codes, which are in the format `46x` where `x` is an arbitrary digit, are code for invalid parameters errors

| Code | Short description | Long description |
|:----:|-------------------|------------------|
| 200 | SUCCESS | Accepted and processed by the database |
| 400 | ERROR FOUND | An error was made on the client |
| 401 | UNAUTHORIZED | The caller is not authorized to access the requested content |
| 402 | CITY ID ALREADY EXISTS | Provided city id already exists | 
| 403 | SPORTCENTER ID ALREADY EXISTS | Provided sport center id already exists | 
| 404 | COURT ID ALREADY EXISTS |  Provided court id already exists |
| 405 | PLAYER ID ALREADY EXISTS | Provided player id already exists |
| 406 | STAFF ID ALREADY EXISTS | Provided staff id already exists |
| 407 | BOOKING ID ALREADY EXISTS |  Provided booking id already exists |
| 410 | BOOKINGS LIMIT REACHED | The player has reached the maximum number of allowed bookings |
| 411 | BOOKING CANCELLATION REJECTED | The cancellation request was made in less the 24 hours before the start time of the booking |
| 412 | UNPAID BOOKING FOUND | There exists a previous booking that has not been paid |
| 413 | OVERLAPPED BOOKING FOUND | There exists a booking with overlapping time duration with the new booking |
| 460 | INVALID CITY ID | An invalid city id was provided |
| 461 | INVALID SPORTCENTER ID | An invalid sportcenter id was provided |
| 462 | INVALID COURT ID | An invalid court id was provided |
| 463 | INVALID STAFF ID | An invalid staff id was provided |
| 464 | INVALID PLAYER ID | An invalid player id was provided |
| 465 | INVALID BOOKING ID | An invalid booking id was provided |
| 466 | INVALID DATE | An invalid date was provided |
| 467 | INVALID DURATION | An invalid booking duration was provided |
| 468 | INVALID START TIME | An invalid start time was provided |
| 469 | INVALID END TIME | An invalid end time was provided |
