API URLs
===

getCourtBookings */api/bookings[?courtId=&sportCenterId=&cityId=&date=]*
---
**URL encoded keys:**
+ `courtId` an alphanumeric characters sequence, represents the the identifier of the court.
+ `sportCenterId` an alphanumeric characters sequence, represents the identifier of the sport center.
+ `cityId` an alphanumeric characters sequence, represents the unique identifier of the city.
+ `date` the date of all the bookings, formatted as `HH:mm:ss`

**On success:** JSON encoded data
```
[
	{
		"bookingId": "alphanumericCharSequence",
		"createdAt": "yyyy-MM-dd HH:mm:ss",
		"bookingDate": "yyyy-MM-dd",
		"bookingStartTime": "HH:mm:ss",
		"bookingEndTime": "HH:mm:ss",
		"isPaid": boolean,
		"cityId": "alphanumericCharSequence",
		"sportCenterId": "alphanumericCharSequence",
		"courtId": "alphanumericCharSequence",
		"playerId": "alphanumericCharSequence"
	}

]
```
**On failure** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

getSportCenterBookings */api/bookings[?sportCenterId=&cityId=&date=]*
---
**URL encoded keys:**
+ `sportCenterId` a numeric characters sequence that uniquely identifies the sport center.
+ `cityId` a numeric characters sequence that uniquely identifies the city.
+ `date` the date of all the bookings, formatted as `HH:mm:ss`.
  
**On scuccess:** JSON encoded data
```
[
	{
		"bookingId": "alphanumericCharSequence",
		"createdAt": "yyyy-MM-dd HH:mm:ss",
		"bookingDate": "yyyy-MM-dd",
		"bookingStartTime": "HH:mm:ss",
		"bookingEndTime": "HH:mm:ss",
		"isPaid": boolean,
		"cityId": "alphanumericCharSequence",
		"sportCenterId": "alphanumericCharSequence",
		"courtId": "alphanumericCharSequence",
		"playerId": "alphanumericCharSequence"
	}
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

getPlayerBookings */api/bookings[?playerId=]*
---
**URL encoded keys**
+ `playerId` an alphanumeric characters sequence, represents the unique identifier of the player.

**On scuccess:** JSON encoded data
```
[
	{
		"bookingId": "alphanumericCharSequence",
		"createdAt": "yyyy-MM-dd HH:mm:ss",
		"bookingDate": "yyyy-MM-dd",
		"bookingStartTime": "HH:mm:ss",
		"bookingEndTime": "HH:mm:ss",
		"isPaid": boolean,
		"cityId": "alphanumericCharSequence",
		"sportCenterId": "alphanumericCharSequence",
		"courtId": "alphanumericCharSequence",
		"playerId": "alphanumericCharSequence"
	}
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

getPlayerBookingsInCity */api/bookings[?playerId=&cityId=&date=]*
---
**URL encoded keys**
+ `playerId` an alphanumeric characters sequence, represents the unique identifier of the player.
+ `cityId` an alphanumeric characters sequence, represents the unique identifier of the city. 
+ `date` the date of all the bookings, formatted as `HH:mm:ss`.

**On scuccess:** JSON encoded data
```
[
	{
		"bookingId": "alphanumericCharSequence",
		"createdAt": "yyyy-MM-dd HH:mm:ss",
		"bookingDate": "yyyy-MM-dd",
		"bookingStartTime": "HH:mm:ss",
		"bookingEndTime": "HH:mm:ss",
		"isPaid": boolean,
		"cityId": "alphanumericCharSequence",
		"sportCenterId": "alphanumericCharSequence",
		"courtId": "alphanumericCharSequence",
		"playerId": "alphanumericCharSequence"
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

createBooking */api/bookings[?bookingId=&timeStamp=&date=&startTime=&endTime=&cityId=&sportCenterId=&courtId=&playerId=]*
---
**URL encoded keys**
+ `bookingId` an alphanumeric characters sequence, represents the unique identifier of the booking.
+ `timeStamp` the date when the booking is created, formatted as `YYYY:MM:DD HH:MM:SS`.
+ `date` the date when player booked to play, formatted as `HH:mm:ss`.
+ `startTime` the time that the booking starts, formatted as `HH:MM:SS`.
+ `endTime` the time that the booking starts, formatted as `HH:MM:SS`.
+ `cityId` an alphanumeric characters sequence, represents the unique identifier of the city. 
+ `sportCenterId` an alphanumeric characters sequence that uniquely identifies the sport center.
+ `courtId` an alphanumeric characters sequence, represents the the identifier of the court.
+ `playerId` an alphanumeric characters sequence, represents the unique identifier of the player.

**On scuccess:** JSON encoded data
```
[
	{
		"bookingId": "alphanumericCharSequence",
		"createdAt": "yyyy-MM-dd HH:mm:ss",
		"bookingDate": "yyyy-MM-dd",
		"bookingStartTime": "HH:mm:ss",
		"bookingEndTime": "HH:mm:ss",
		"isPaid": boolean,
		"cityId": "alphanumericCharSequence",
		"sportCenterId": "alphanumericCharSequence",
		"courtId": "alphanumericCharSequence",
		"playerId": "alphanumericCharSequence"
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

updateBookingStatus */api/bookings[?status=?bookingId=?cityId=?sportCenterId=?staffId=]*
---
**URL encoded keys**
+ `status` boolean True or False, stands for isPaid or isNotPaid.
+ `bookingId` an alphanumeric characters sequence, represents the unique identifier of the booking.
+ `cityId` an alphanumeric characters sequence, represents the unique identifier of the city. 
+ `sportCenterId` an alphanumeric characters sequence that uniquely identifies the sport center.
+ `staffId` an alphanumeric characters sequence that uniquely identifies the staff.

**On scuccess:** JSON encoded data
```
[
	{
		"bookingId": "alphanumericCharSequence",
		"createdAt": "yyyy-MM-dd HH:mm:ss",
		"bookingDate": "yyyy-MM-dd",
		"bookingStartTime": "HH:mm:ss",
		"bookingEndTime": "HH:mm:ss",
		"isPaid": boolean,
		"cityId": "alphanumericCharSequence",
		"sportCenterId": "alphanumericCharSequence",
		"courtId": "alphanumericCharSequence",
		"playerId": "alphanumericCharSequence"
	}
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

cancelBooking */api/bookings[?bookingId=&playerId=]*
---
---
**URL encoded keys**
+ `bookingId` an alphanumeric characters sequence, represents the unique identifier of the booking.
+ `playerId` an alphanumeric characters sequence that uniquely identifies the player.

**On scuccess:** JSON encoded data
```
[
	{
		
	}
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

getCities *api/city[?=]*
---
**URL encoded keys**

**On scuccess:** JSON encoded data
```
[
	{
		"cityId": "alphanumericCharSequence",
	}
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

createCity */api/city[?cityId=]*
---
**URL encoded keys**
+ `cityId` is an alphanumeric characters sequence that uniquely identifies the city.

**On scuccess:** JSON encoded data
```
[
	{
		"cityId": "alphanumericCharSequence",
	}
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

getCityCourt */api/court[?cityId=]*
---
**URL encoded keys**
+ `cityId` is an alphanumeric characters sequence that uniquely identifies the city.
  
**On scuccess:** JSON encoded data
```
[
	{
		"courtId": "alphanumericCharacterSequence",
		"cityId": "alphanumericCharSequence",
		"sportCenterId": "alphanumericCharSequence"
	}
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

getSportCenterCourt */api/court[?sportCenterId=&cityId=]*
---
**URL encoded keys**
+ `sportCenterId` is an alphanumeric characters sequence that uniquely identifies the sport center.
+ `cityId` is an alphanumeric characters sequence that uniquely identifies the city.
  
**On scuccess:** JSON encoded data
```
[
	{
		"courtId": "alphanumericCharacterSequence",
		"cityId": "alphanumericCharSequence",
		"sportCenterId": "alphanumericCharSequence"
	}
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

createCityCenterCourt */api/court[?courtId=&cityId&=sportCenterId=]*
---
**URL encoded keys**
+ `courtId` is an alphanumeric characters sequence that uniquely identifies the court.
+ `sportCenterId` is an alphanumeric characters sequence that uniquely identifies the sport center.
+ `cityId` is an alphanumeric characters sequence that uniquely identifies the city.
  
**On scuccess:** JSON encoded data
```
[
	{
		"courtId": "alphanumericCharacterSequence",
		"cityId": "alphanumericCharSequence",
		"sportCenterId": "alphanumericCharSequence"
	}
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```
updateCourtId */api/court[?newCourtId=&oldCourtId=&cityId=&sportCenterId=]*
---
**URL encoded keys**
+ `newCourtId`is an alphanumeric characters sequence that uniquely identifies the court
+ `oldCourtId` is an alphanumeric characters sequence that uniquely identifies the court.
+ `cityId` is an alphanumeric characters sequence that uniquely identifies the city.
+ `sportCenterId` is an alphanumeric characters sequence that uniquely identifies the sport center.

**On scuccess:** JSON encoded data
```
[
	{
		"newCourtId": "alphanumericCharacterSequence",
		"cityId": "alphanumericCharSequence",
		"sportCenterId": "alphanumericCharSequence"
	}
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

createPlayer */api/player[?playerId=]*
---
**URL encoded keys**
+ `playerId` is an alphanumeric characters sequence that uniquely identifies the player.

**On scuccess:** JSON encoded data
```
[
	{
		"playerId": "alphanumericCharacterSequence"
	}
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

updatePlayerId */api/player[?newPlayerId=&oldPlayerId=]*
---
**URL encoded keys**
+ `newPlayerId` is an alphanumeric characters sequence that uniquely identifies the player.
+ `oldPlayerId` is an alphanumeric characters sequence that uniquely identifies the player.

**On scuccess:** JSON encoded data
```
[
	{
		"newPlayerId": "alphanumericCharacterSequence"
	}
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

getCitySportCenter */api/sportCenter[?cityId=]*
---
**URL encoded keys**
+ `cityId` is an alphanumeric characters sequence that uniquely identifies the city.
  
**On scuccess:** JSON encoded data
```
[
	{
		"sportCenterId": "alphanumericCharacterSequence",
		"cityid": "alphanumericCharacterSequence"
	}
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

createCityCenter */api/sportCenter[?sportCenterId=&citId=]*
---
**URL encoded keys**
+ `sportCenterId` is an alphanumeric characters sequence that uniquely identifies the sport center.
+ `cityId` is an alphanumeric characters sequence that uniquely identifies the city.
  
**On scuccess:** JSON encoded data
```
[
	{
		"sportCenterId": "alphanumericCharacterSequence",
		"cityid": "alphanumericCharacterSequence"
	}
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

updateSportCenterId */api/sportCenter[?newSportCenterId=&oldSportCenterId=&cityId]*
---
**URL encoded keys**
+ `newSportCenterId` is an alphanumeric characters sequence that uniquely identifies the sport center.
+ `oldSportCenterId` is an alphanumeric characters sequence that uniquely identifies the sport center.
+ `cityId` is an alphanumeric characters sequence that uniquely identifies the city.
  
**On scuccess:** JSON encoded data
```
[
	{
		"newSportCenterId": "alphanumericCharacterSequence",
		"cityid": "alphanumericCharacterSequence"
	}
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

createStaff */api/staff[?staffId=&cityId=&sportCenterId=]*
---
**URL encoded keys**
+ `staffId` is an alphanumeric characters sequence that uniquely identifies the staff.
+ `sportCenterId` is an alphanumeric characters sequence that uniquely identifies the sport center.
+ `cityId` is an alphanumeric characters sequence that uniquely identifies the city.
  
**On scuccess:** JSON encoded data
```
[
	{
		"staffId": "alphanumericCharacterSequence",
		"cityid": "alphanumericCharacterSequence"
		"sportCenterId": "alphanumericCharacterSequence"
	}
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

updateStaffId */api/staff[?newStaffId=&oldStaffId=&cityId=&sportCenterId=]*
---
**URL encoded keys**
+ `newStaffId` is an alphanumeric characters sequence that uniquely identifies the staff.
+ `oldStaffId` is an alphanumeric characters sequence that uniquely identifies the staff.
+ `sportCenterId` is an alphanumeric characters sequence that uniquely identifies the sport center.
+ `cityId` is an alphanumeric characters sequence that uniquely identifies the city.
  
**On scuccess:** JSON encoded data
```
[
	{
		"newStaffId": "alphanumericCharacterSequence",
		"cityid": "alphanumericCharacterSequence"
		"sportCenterId": "alphanumericCharacterSequence"
	}
]
```
**On failure:** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```



