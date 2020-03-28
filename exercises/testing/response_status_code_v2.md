Success codes and error codes
===


Success code
---

| Code | Short description | Long description |
|:----:|-------------------|------------------|
| 200 | SUCCESS | The request is accepted and processed by the server|
| 201 | SUCCESS WITH DATA | The request is accepted, and contains data sent by the server |
| 202 | CREATED | The request is accepted, and new content is created by the server |
| 203 | UPDATED | The request is accepter, and existing content is modified by the server |


Client error code
---

| Code | Short description | Long description |
|:----:|-------------------|------------------|
| 400 | ERROR FOUND | The request is rejected, because of an error was made on the client |
| 409 | BOOKING REJECTED | The request for a new booking is rejected, because of unexpected reason |
| 406 | UNPAID BOOKING FOUND | The request is rejected, because there exists a booking in the past that has not been paid|
| 407 | BOOKING LIMIT REACHED | The request is rejected, because the user has reached the maximum number of allowed bookings |
| 404 | INVALID BOOKING ID | The request is rejected, because an invalid booking id was provided |
| 405 | INVALID TIME SLOT | The request is rejected, because an invalid timeslot was provided |
| 408 | INVALID BOOKING DURATION | The request is rejected, because an invalid booking duration was provided |
| 401 | INVALID CREDENTIALS | The request is rejected, because an invalid user-name or an invalid password was provided |
| 402 | INVALID PARAMETER | The request is rejected, because an invalid parameter was provided |
| 410 | INVALID BOOKING STATE | The request is rejected, because an invalid booking state was provided |
| 411 | INVALID SENDER ID | The request is rejected, because an invalid sender id was provided |
| 412 | INVALID RECEIVER ID | The request is rejected, because an invalid receiver id was provided |
| 403 | UNAUTHORIZED REQUEST | The request is rejected, because the requested content is not accessible by the caller of the interface |



Redirection code
---

*(Might be needed in the future)*


Informational code
---

*(Might be needed in the future)*


Server error code
---

*(Might be needed in the future)*