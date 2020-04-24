API URLs
===

GET */api/bookings[?playerId=&courtId=&sportCenterId=&cityId=&date=]*
---
**URL encoded keys:**
+ `playerId` an alphanumeric characters sequence, represents the unique identifier of the player
+ `courtId` an alphanumeric characters sequence, represents the the identifier of the court
+ `sportCenterId` an alphanumeric characters sequence, represents the identifier of the sport center
+ `cityId` an alphanumeric characters sequence, represents the unique identifier of the city
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
		"courtId": "alphanumericCharSequence"<
		"playerId": "alphanumericCharSequence:
	},

	...
]
```

**On failure** JSON encoded data
```
{
	"error": boolean
	"message": "Error message"
}
```

