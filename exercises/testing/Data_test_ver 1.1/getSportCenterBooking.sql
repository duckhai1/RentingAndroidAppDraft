/* test getSportCenter Booking interface */ 
/* version 1.1 */
    /* 1.1 sửa tên + datatype theo database */
/* getSportCenterBooking(sportcenter_name, booking_date) */ 

CREATE PROCEDURE testGetSportCenterBooking 
BEGIN 
    --scenario 
    INSERT INTO cities(city_id, city_name) VALUES (001,"HCM");
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    INSERT INTO sportcenters(sportcenter_id, sportcenter_name, city_id) VALUES (100, 'Dinh Hoa Sport Center',001);
    --test
    DECLARE @resultcode INT; 
    CALL getSportCenter('Dinh Hoa Sport Center', '2020.01.02', @resultcode)
    IF (@resultCode = 200) THEN
        --Success
        SELECT "Success" AS "result of testGetSportCenterBooking"
    ELSE 
        SELECT "Fail" AS "result of testGetSportCenterBooking"
    END IF;
END;    

---------------------------------------------------------

CREATE PROCEDURE testGetSportCenterBookingInvalidSportCenterName
BEGIN 
    --screnario 
    INSERT INTO cities(city_id, city_name) VALUES (001,"HCM");
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    INSERT INTO sportcenters(sportcenter_id, sportcenter_name, city_id) VALUES (100, 'Dinh Hoa Sport Center',001);
    --test
    DECLARE @resultcode INT; 
    CALL getSportCenter('VGU Sport Center'), '2020.01.02', @resultcode)
    IF (@resultCode = ???) THEN
        --??? invalid sportcenter_name
        SELECT "Fail" AS "result of testGetSportCenterBookingInvalidSportCenterID"
    ELSE 
        SELECT "Success" AS "result of testGetSportCenterBookingInvalidSportCenterID"
    END IF;
END;    

---------------------------------------------------------

CREATE PROCEDURE testGetSportCenterBookingInvalidBookingDate
BEGIN
    --screnario 
    INSERT INTO cities(city_id, city_name) VALUES (001,"HCM");
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    INSERT INTO sportcenters(sportcenter_id, sportcenter_name, city_id) VALUES (100, 'Dinh Hoa Sport Center',001);
    --test
    DECLARE @resultcode INT; 
    CALL getSportCenter('Dinh Hoa Sport Center', '20.01.02', @resultcode)
    IF (@resultCode = 463) THEN
        -- 463 invalid booking_date 
        SELECT "Fail" AS "result of testGetSportCenterBookingInvalidBookingDate"
    ELSE 
        SELECT "Success" AS "result of testGetSportCenterBookingInvalidBookingDate"
    END IF;
END;    