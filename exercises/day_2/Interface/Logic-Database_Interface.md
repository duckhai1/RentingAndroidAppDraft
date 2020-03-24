### LOGIN:
- getUserName(): get Username of the input-username.
- getPassword(): get the password of that user to compare the input password is correct?

### DISPLAY INFORMATION:
- getUserInfo(): request from the database a row of all information of users by a given user id and store it in the User's attributes.

### MODIFY INFORMATION: *update user's information on the database*
##### USER:
- updateInfo(): update info to database server (lastName, firstName, phoneNum, birthday, password,...).
##### SPORT-CENTER: 
- updateInfo(): update name, court, address,...

### DISPLAY INFORMATION FOR BOOKING:
##### USER
- getAllSlot(): get all slots including available and booked one by a given city and date.
- getSlotInfo(): get all slot information including slot start/end time, slot's status.
- getAllUserBooking(): get all booking of the user.
#### SPORT-CENTER
- getAllSlot(): including booked slot and available slot by a given date.
- getUserInfo(): by a given slot
- getSlotInfo(): including userinfo, slot start/end time, slot's status.

### BOOKING PROCESS: *update and save all informaton on the booking entity on the database*
##### USER
- updateBookingInfo(): update date, court, status, start/end time.

##### SPORT-CENTER
- updateBookingStatus(): change booking status
- updateBookingState(): change booking state

### MISCELLANEOUS:
- getSportCenterInfo(): get a row containing all attribute of sportcenter by a given name.