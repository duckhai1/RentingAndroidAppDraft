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
+ Codes, which are in the format `46x` where `x` is an arbitrary digit, are code for invalid parameters errors

| Code | Short description | Long description |
|:----:|-------------------|------------------|
| 400 | ERROR FOUND | The request is rejected, because of an error was made on the client |
| 401 | UNAUTHORIZED | The request is rejected, because the requested content is not accessible by the caller of the interface |
| 410 | BOOKING NOT FOUND | The request is rejected, because an invalid booking id was provided |
| 411 | BOOKINGS LIMIT REACHED | The request is rejected, because the player has reached the maximum number of allowed bookings |
| 412 | BOOKING CANCELLATION REJECTED | The request is rejected, because the player makes the request in less the 24 hours before the start time of the booking|
| 413 | UNPAID BOOKING FOUND | The request is rejected, because there exists a booking in the past that has not been paid|
| 460 | INVALID CREDENTIALS | The request is rejected, because an invalid player-name or an invalid password was provided |
| 461 | INVALID CITY ID | The request is rejected, because an invalid city i was provided |
| 462 | INVALID SPORTCENTRE ID | The request is rejected, because an invalid city id was provided |
| 463 | INVALID DATE | The request is rejected, because an invalid date was provided |
| 464 | INVALID SLOT | The request is rejected, because an invalid timeslot was provided |
| 465 | INVALID DURATION | The request is rejected, because an invalid booking duration was provided |
| 466 | INVALID STATE | The request is rejected, because an invalid booking state was provided |
| 467 | INVALID REPORTEE ID | The request is rejected, because an invalid sender id was provided |
| 468 | INVALID RECEIVER ID | The request is rejected, because an invalid receiver id was provided |
| 469 | INVALID COURT ID | The request is rejected, because an invalid court id was provided |
| 470 | INVALID START TIME | The request is rejected, because an invalid start time was provided |
| 471 | INVALID END TIME | The request is rejected, because an invalid end time was provided |
| 472 | INVALID SPORTCENTRE NAME | The request is rejected, because an invalid sport centre name was provided |
| 473 | INVALID ADDRESS | The request is rejected, because an invalid address was provided |
| 474 | INVALID PHONE NUMBER | The request is rejected, because an invalid phone number was provided
| 475 | INVALID FIRST NAME | The request is rejected, because an invalid first name was provided |
| 476 | INVALID LAST NAME | The request is rejected, because an invalid last name was provided |
| 477 | INVALID BIRTHDAY | The request is rejected, because an invalid birthday was provided |
| 478 | INVALID PLAYER ID | The request is rejected, because an invalid player id was provided |




Redirection code
---

*(Might be needed in the future)*


Informational code
---

*(Might be needed in the future)*


Server error code
---

*(Might be needed in the future)*
