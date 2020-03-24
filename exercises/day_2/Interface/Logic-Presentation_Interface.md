### LOGIN:
- login(String username, String Password): *login command with username and password*

### LOGOUT
- logout(): *send command log out*
 
### GET INFORMATION:
- getUserInfo(): display all user info (id, first/last name, phonenum, birthday, ...)
- getSportCenterInfo(): display all sportcenter info(id, name, ...)
- getUserBooking(user): get booking list of user
- getSlotInfo(slot): get info of selected slot
- getCityList(): get all city name
- getSportCenterList(city): get all Sport Center name
- getSchedule(sportCenter): get Schedule of specific Sport Center

### MODIFY INFORMATION
- updateUserInfo(): change infomation of user
- updateSportCenterInfo(): change information of Sport Center


### PROCESS
- booking(Time startTime, Time endTime, String username , String fieldName): booking behavior
- cancelBooking(Time startTime, Time endTime, String fieldName): cancel booking behavior
- review(String sportCenterName, String msg): review behavior
- contactUser(String username, String msg): Send message to user 
- reportUser(String username, String msg): Report user behavior
- contactSportCenter(String sportCenterName, String msg): Send message to Sport Center
- reportSportCenter(String sportCenterName, String msg): Report Sport Center behavior
