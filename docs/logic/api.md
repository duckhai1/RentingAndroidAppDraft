API URLs
===

GET */api/bookings[?courtId=&sportCenterId=&cityId=&date=]*
---
Get bookings of the given court

**Request query:**
+ `courtId` an alphanumeric characters sequence, represents the the identifier of the court.
+ `sportCenterId` an alphanumeric characters sequence, represents the identifier of the sport center.
+ `cityId` an alphanumeric characters sequence, represents the unique identifier of the city.
+ `date` the date of all the bookings, formatted as `HH:mm:ss`

**Posible responses:**
+ `200 - OK`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (200 - OK):** (JSON encoded)
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
	...
]
```

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database reports a an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```

GET */api/bookings[?sportCenterId=&cityId=&date=]*
---
Get bookings of the given sport center

**Request query:**
+ `sportCenterId` a numeric characters sequence that uniquely identifies the sport center.
+ `cityId` a numeric characters sequence that uniquely identifies the city.
+ `date` the date of all the bookings, formatted as `HH:mm:ss`.

**Posible responses:**
+ `200 - OK`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (200 - OK):** JSON encoded data
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
	...
]
```

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database reports a an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```

GET */api/bookings[?playerId=]*
---
Get bookings of the given player

**Request query**
+ `playerId` an alphanumeric characters sequence, represents the unique identifier of the player.

**Posible responses:**
+ `200 - OK`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (200 - OK):** JSON encoded data
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
	...
]
```

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database reports a an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```

GET */api/bookings[?playerId=&cityId=&date=]*
---
Get bookings of the given player for the given city

**Request query**
+ `playerId` an alphanumeric characters sequence, represents the unique identifier of the player.
+ `cityId` an alphanumeric characters sequence, represents the unique identifier of the city.
+ `date` the date of all the bookings, formatted as `HH:mm:ss`.

**Posible responses:**
+ `200 - OK`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (200 - OK):** JSON encoded data
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
	...
]
```

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database reports a an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```

POST */api/bookings*
---
Create a booking from the given data

**Request body:** (JSON encoded)
```
{
	"bookingDate": "yyyy-MM-dd",
	"bookingStartTime": "HH:mm:ss",
	"bookingEndTime": "HH:mm:ss",
	"cityId": "alphanumericCharSequence",
	"sportCenterId": "alphanumericCharSequence",
	"courtId": "alphanumericCharSequence",
	"playerId": "alphanumericCharSequence"
}
```

**Posible responses:**
+ `201 - CREATED`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database returns an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```

PUT */api/bookings[?status=?bookingId=?cityId=?sportCenterId=?staffId=]*
---
Update the status of the booking

**Request query**
+ `status` a boolean.
+ `bookingId` an alphanumeric characters sequence, represents the unique identifier of the booking.
+ `cityId` an alphanumeric characters sequence, represents the unique identifier of the city.
+ `sportCenterId` an alphanumeric characters sequence that uniquely identifies the sport center.
+ `staffId` an alphanumeric characters sequence that uniquely identifies the staff.

**Posible responses:**
+ `202 - ACCEPTED`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database returns an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```

DELETE */api/bookings[?bookingId=&playerId=]*
---
Remove the booking from the data

**Request query**
+ `bookingId` an alphanumeric characters sequence, represents the unique identifier of the booking.
+ `playerId` an alphanumeric characters sequence that uniquely identifies the player.

**Posible responses:**
+ `202 - ACCEPTED`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database returns an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```

GET */api/cities*
---
Get all cities exist in the database

**Response body:** JSON encoded data
```
[
	{
		"cityId": "alphanumericCharSequence",
	}
	...
]
```

**Posible responses:**
+ `200 - OK`
+ `401 - UNAUTHORIZED`

POST */api/cities*
---
Create a city from the given data

**Request body** (JSON encoded)
```
{
	"cityId": "alphanumericCharSequence",
}
```

**Posible responses:**
+ `202 - ACCEPTED`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database returns an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```

GET */api/courts[?cityId=]*
---
Get all the courts exist in the given city

**Request query**
+ `cityId` is an alphanumeric characters sequence that uniquely identifies the city.

**Response body:** JSON encoded data
```
[
	{
		"courtId": "alphanumericCharacterSequence",
		"cityId": "alphanumericCharSequence",
		"sportCenterId": "alphanumericCharSequence"
	}
	...
]
```

**Posible responses:**
+ `200 - OK`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database returns an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```

GET */api/courts[?sportCenterId=&cityId=]*
---
Get all the courts exist in the given sport center

**Request query**
+ `sportCenterId` is an alphanumeric characters sequence that uniquely identifies the sport center.
+ `cityId` is an alphanumeric characters sequence that uniquely identifies the city.

**Response body:** JSON encoded data
```
[
	{
		"courtId": "alphanumericCharacterSequence",
		"cityId": "alphanumericCharSequence",
		"sportCenterId": "alphanumericCharSequence"
	}
	...
]
```

**Posible responses:**
+ `200 - OK`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database returns an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```

POST */api/courts*
---
Create a court with the given data

**Request body**
```
{
	"courtId": "alphanumericCharacterSequence",
	"cityId": "alphanumericCharSequence",
	"sportCenterId": "alphanumericCharSequence"
}
```

**Posible responses:**
+ `201 - CREATED`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database returns an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```

PUT */api/court[?newCourtId=&oldCourtId=&cityId=&sportCenterId=]*
---
Update the ID of the given court

**Request query**
+ `newCourtId`is an alphanumeric characters sequence that uniquely identifies the court
+ `oldCourtId` is an alphanumeric characters sequence that uniquely identifies the court.
+ `cityId` is an alphanumeric characters sequence that uniquely identifies the city.
+ `sportCenterId` is an alphanumeric characters sequence that uniquely identifies the sport center.

**Posible responses:**
+ `202 - ACCEPTED`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database returns an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```

POST */api/players*
---
Create a player from the given data

**Request body** (JSON encoded)
```
{
	"playerId": "alphanumericCharSequence",
}
```

**Posible responses:**
+ `202 - ACCEPTED`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database returns an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```

PUT */api/players[?newPlayerId=&oldPlayerId=]*
---
Update the ID of the given player

**Request query**
+ `newPlayerId` is an alphanumeric characters sequence that uniquely identifies the player.
+ `oldPlayerId` is an alphanumeric characters sequence that uniquely identifies the player.

**Posible responses:**
+ `202 - ACCEPTED`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database returns an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```

GET */api/centers[?cityId=]*
---
Get all sport centers from the given city

**Request query**
+ `cityId` is an alphanumeric characters sequence that uniquely identifies the city.

**Response body:** JSON encoded data
```
[
	{
		"sportCenterId": "alphanumericCharacterSequence",
		"cityid": "alphanumericCharacterSequence"
	}
	...
]
```

**Posible responses:**
+ `200 - OK`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database returns an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```

POST */api/centers[?sportCenterId=&cityId=]*
---
Create a sport center from the given data

**Request body** (JSON encoded)
```
{
	"sportCenterId": "alphanumericCharacterSequence",
	"cityid": "alphanumericCharacterSequence"
}
```

**Posible responses:**
+ `201 - CREATED`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database returns an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```

PUT */api/centers[?newSportCenterId=&oldSportCenterId=&cityId]*
---
Update the ID of the given sport center

**Request query**
+ `newSportCenterId` is an alphanumeric characters sequence that uniquely identifies the sport center.
+ `oldSportCenterId` is an alphanumeric characters sequence that uniquely identifies the sport center.
+ `cityId` is an alphanumeric characters sequence that uniquely identifies the city.

**Posible responses:**
+ `202 - ACCEPTED`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database returns an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```

POST */api/staffs*
---
Create a staff from the given data

**Request body** (JSON encoded)
```
{
	"staffId": "alphanumericCharacterSequence".
	"sportCenterId": "alphanumericCharacterSequence",
	"cityid": "alphanumericCharacterSequence"
}
```

**Posible responses:**
+ `201 - CREATED`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database returns an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```

PUT */api/staff[?newStaffId=&oldStaffId=&cityId=&sportCenterId=]*
---
Update the ID of the given staff

**Request query**
+ `newStaffId` is an alphanumeric characters sequence that uniquely identifies the staff.
+ `oldStaffId` is an alphanumeric characters sequence that uniquely identifies the staff.
+ `sportCenterId` is an alphanumeric characters sequence that uniquely identifies the sport center.
+ `cityId` is an alphanumeric characters sequence that uniquely identifies the city.

**Posible responses:**
+ `202 - ACCEPTED`
+ `400 - BAD REQUEST`
+ `401 - UNAUTHORIZED`

**Response body (400 - BAD REQUEST):** (JSON encoded, only included when the database returns an error status code)
```
{
	"error": boolean,
	"statusCode": integer,
	"message": "Error message"
}
```
