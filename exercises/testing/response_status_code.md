## Response status code
---


#### **Informational code**
*(Furture expand)*

#### **Success code**
- 200: The request has fullfilled and processing
- 201: The request has fullfilled and resulting in return content 
- 202: The request has fullfilled and resulting in create new content
- 203: The request has fullfilled and resulting in change the existing data

#### **Redirection code**
*(Furture expand)*

#### **Client error code**
- 400: General client error 
- 401: Login error due to username and password are not matched in server
- 402: The server could not understand the request due to invalid parameter provided
- 403: The request is unauthorized
- 404: The server can not find the requested booking’s information
- 405: The server can not find the requested timeslot (invalid time slot)
- 406: Error in making new booking due to have past unpaid booking
- 407: Error in making new booking due to exceed booking limitation
- 408: Booking have invalid duration (endTime - startTime)
- 409: Booking request rejected
- 410: Invalid State is provided due to wrong format
- 411: Fail to recognize sender ID
- 412: Fail ro recognize receiver ID


#### **Server error code**
*(TODO)*


