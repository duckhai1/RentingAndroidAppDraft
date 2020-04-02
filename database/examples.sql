-- Database's APIs examples
INSERT INTO players(playerName) VALUES
	('Tung'),
	('Khai'),
	('Hieu');
    
INSERT INTO cities(cityName) VALUES
	('Ho Chi Minh');
    
INSERT INTO sportcenters(cityId, sportcenterName) VALUES
	((SELECT cityId FROM cities WHERE cityName = 'Ho Chi Minh'), 'HoChiMinh Sport Center A'),
    ((SELECT cityId FROM cities WHERE cityName = 'Ho Chi Minh'), 'HoChiMinh Sport Center B'),
    ((SELECT cityId FROM cities WHERE cityName = 'Ho Chi Minh'), 'HoChiMinh Sport Center C');
INSERT INTO courts(sportcenterId, courtName) VALUES
	((SELECT sportcenterId FROM sportcenters WHERE sportcenterName = 'HoChiMinh Sport Center A'), 'Court 1'),
	((SELECT sportcenterId FROM sportcenters WHERE sportcenterName = 'HoChiMinh Sport Center A'), 'Court 2'),
	((SELECT sportcenterId FROM sportcenters WHERE sportcenterName = 'HoChiMinh Sport Center A'), 'Court 3');

CALL makeBooking(
	(SELECT playerId FROM players WHERE playerName = 'Hieu'),
    (SELECT courtId FROM courts WHERE courtName = 'Court 1'),
    "2020-04-15",
    "6:00:00",
    "7:30:00",
    @StatusCode
);
SELECT @StatusCode;

CALL makeBooking(
	(SELECT playerId FROM players WHERE playerName = 'Tung'),
    (SELECT courtId FROM courts WHERE courtName = 'Court 1'),
    "2020-04-15",
    "9:00:00",
    "10:30:00",
    @StatusCode
);

CALL makeBooking(
	(SELECT playerId FROM players WHERE playerName = 'Tung'),
    (SELECT courtId FROM courts WHERE courtName = 'Court 1'),
    "2020-04-15",
    "10:30:00",
    "12:00:00",
    @StatusCode
);

CALL makeBooking(
	(SELECT playerId FROM players WHERE playerName = 'Tung'),
    (SELECT courtId FROM courts WHERE courtName = 'Court 1'),
    "2020-04-15",
    "14:00:00",
    "15:00:00",
    @StatusCode
);


CALL cancelBooking(1, @StatusCode);
SELECT @StatusCode;

CALL getBookings((SELECT cityId FROM cities WHERE cityName = 'Ho Chi Minh'), "2020-04-15", @StatusCode);
SELECT @StatusCode;