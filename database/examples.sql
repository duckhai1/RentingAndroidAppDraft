-- Database's APIs examples
INSERT INTO players(playerName) VALUES
	('Tung'),
	('Khai'),
	('Hieu'),
    
INSERT INTO cities(cityName) VALUES
	('Ho Chi Minh'),
	('Ha Noi'),
	('Da Nang'),
    
INSERT INTO sportcenters(cityId, sportcenterName) VALUES
	((SELECT cityId FROM cities WHERE cityName = 'Ho Chi Minh'), 'HoChiMinh Sport Center A'),
    ((SELECT cityId FROM cities WHERE cityName = 'Ho Chi Minh'), 'HoChiMinh Sport Center B'),
    ((SELECT cityId FROM cities WHERE cityName = 'Ho Chi Minh'), 'HoChiMinh Sport Center C'),
    ((SELECT cityId FROM cities WHERE cityName = 'Ha Noi'), 'HaNoi Sport Center A'),
    ((SELECT cityId FROM cities WHERE cityName = 'Ha Noi'), 'HaNoi Sport Center B'),
    ((SELECT cityId FROM cities WHERE cityName = 'Da Nang'), 'DaNang Sport Center A');

INSERT INTO courts(sportcenterId, courtName) VALUES
	((SELECT sportcenter_id FROM sportcenters WHERE sportcenter_name = 'HoChiMinh Sport Center A'), 'CourtVip1'),
	((SELECT sportcenter_id FROM sportcenters WHERE sportcenter_name = 'HoChiMinh Sport Center A'), 'CourtVip1'),
	((SELECT sportcenter_id FROM sportcenters WHERE sportcenter_name = 'HoChiMinh Sport Center A'), 'CourtVip1'),

CALL makeBooking(
	(SELECT player_id FROM players WHERE player_name = 'TUNG'),
    (SELECT court_id FROM courts WHERE court_name = 'CourtVip4'),
    "2020-04-15",
    "9:00:00",
    "10:30:00",
    @StatusCode
);

SELECT @StatusCode;

SELECT count(*)
        FROM bookings
        WHERE court_id = (SELECT court_id FROM courts WHERE court_name = 'CourtVip4')
			AND booking_date = "2020-04-15"
			AND (("9:00:00" <= booking_start_time AND "10:30:00" > booking_start_time)
				OR ("9:00:00" >= booking_start_time AND "9:00:00" < booking_end_time))
