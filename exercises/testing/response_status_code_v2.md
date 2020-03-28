Success codes and error codes
===


Success code
---

| Code | Short description | Long description |
|:----:|-------------------|------------------|
| 200 | SUCCESS | The request is accepted and processed by the server|
| 201 | SUCCESS WITH DATA | The request is accepted, and contains data sent by the server |
| 202 | CREATED | The request is accepted, and new content is created by the server |
| 203 | UPDATED | The request is accepted, and existing content is modified by the server |


Client error code
---

Coding conventions:
+ Codes, which are in the format `40x` where `x` is an arbitrary digit, are code for general errors
+ Codes, which are in the format `41x` where `x` is an arbitrary digit, are code for booking errors
+ Codes, which are in the format `45x` where `x` is an arbitrary digit, are code for invalid parameters errors

| Code | Short description | Long description |
|:----:|-------------------|------------------|
| 400 | ERROR FOUND | The request is rejected, because of an error was made on the client |
| 401 | UNAUTHORIZED | The request is rejected, because the requested content is not accessible by the caller of the interface |
| 410 | BOOKING NOT FOUND | The request is rejected, because an invalid booking id was provided |
| 411 | UNPAID BOOKING FOUND | The request is rejected, because there exists a booking in the past that has not been paid|
| 412 | BOOKINGS LIMIT REACHED | The request is rejected, because the user has reached the maximum number of allowed bookings |
| 413 | BOOKING CANCELLATION REJECTED | The request is rejected, because the user makes the request in less the 24 hours before the start time of the booking|
| 452 | INVALID SLOT | The request is rejected, because an invalid timeslot was provided |
| 453 | INVALID DURATION | The request is rejected, because an invalid booking duration was provided |
| 454 | INVALID CREDENTIALS | The request is rejected, because an invalid user-name or an invalid password was provided |
| 455 | INVALID PARAMETER | The request is rejected, because an invalid parameter was provided |
| 456 | INVALID STATE | The request is rejected, because an invalid booking state was provided |
| 457 | INVALID SENDER ID | The request is rejected, because an invalid sender id was provided |
| 458 | INVALID RECEIVER ID | The request is rejected, because an invalid receiver id was provided |
| 459 | INVALID DATE | The request is rejected, because van invalid date was provided |



Redirection code
---

*(Might be needed in the future)*


Informational code
---

*(Might be needed in the future)*


Server error code
---

*(Might be needed in the future)*