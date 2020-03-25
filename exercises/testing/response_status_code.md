## Response status code
---


#### **Informational code**
*(Furture expand)*

#### **Success code**
- 200: The request has fullfilled
- 201: The request has fullfilled and resulting in return content 
- 202: The request has fullfilled and resulting in create new content
- 203: The request has fullfilled and resulting in change the existing data

#### **Redirection code**
*(Furture expand)*

#### **Client error code**
- 400: General client error 
- 401: Login error due to username and password are not matched in server
- 402: The server could not understand the request due to invalid username
- 403: The server could not understand the request due to invalid password
- 404: The request is unauthorized
- 405: The server could not understand the request due to invalid userId
- 406: The server could not understand the request due to invalid staffId 
- 407: The server could not understand the request due to invalid date is provided 
- 408: The server could not understand the request due to invalid cityId is provided
- 409: The server could not understand the request due to invalid sportCenterId is provided
- 410: Not found the server can not find the requested booking’s information
- 411: The server can not find the requested timeslot (invalid time slot)
- 412: Error in making new booking due to have past unpaid booking
- 413: Error in making new booking due to exceed booking limitation
- 414: Booking have invalid duration (endTime - startTime)
- 415: Booking have invalid booking starttime
- 416: Booking have invalid booking endtime
- 417: Error in cancel booking due to cancel time is exceeded 24 hours
- 418: Invalid State is provided due to wrong format
- 419: invalid Firstname is provided
- 420: invalid Lastname is provided
- 421: invalid Birthday format is provided
- 422: invalid Phone number is provided
- 423: Fail to recognize sender ID
- 424: Fail ro recognize receiver ID

#### **Server error code**
*(TODO)*


