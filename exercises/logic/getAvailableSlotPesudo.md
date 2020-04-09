## GET AVAILABLE SLOT PSEUDOCODE

```
Slot[] slotList
Court[] courtList  
for court in courtList:
    if (len(court.Bookings) == 0): // court.Booking is the list of booking in a given day and court sorted by time in ascending order
         slotList <- slot(court.Id, court.sportCenterId, court.cityId, 07:00:00, 21:00:00)
    else:
        for i := 0 to len(court.Bookings):
            if (i == 0 and court.Booking[i].startTime - 07:00:00 >= 00:45:00):
                slotList <- slot(court.Id, court.sportCenterId, court.cityId, 07:00:00, court.Booking[i].startTime)
            if (i == len(court.Bookings) - 1 and 21:00:00 - court.Booking[i].endTime >= 00:45:00):
                slotList <- slot(court.Id, court.sportCenterId, court.cityId, court.Booking[i].endTime, 21:00:00)
            if (i < len(court.Bookings) - 1 and court.Booking[i+1].startTime - court.Booking[i].endTime >= 00:45:00):
                slotList <- slot(court.Id, court.sportCenterId, court.cityId, court.Booking[i].endTime, court.Booking[i+1].startTime)
return slotList
```
