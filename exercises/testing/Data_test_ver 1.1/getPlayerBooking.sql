/* test getPlayerBooking interface */
/* version 1.2 */
    /* ver1.1 fix error code */
    /* ver1.2 sửa tên + datatype theo database */
/* getPlayerBooking(player_id, city_name, booking_date) */

CREATE PROCEDURE testGetPlayerBooking
BEGIN
    --screnario
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    INSERT INTO cities(city_id, city_name) VALUES (001,"HCM");
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    --test
    DECLARE @resultCode INT;
	CALL getPlayerBooking (111, 001, '2020.01.02', @resultCode)
    IF (@resultCode = 200) THEN
        --Success
        SELECT "Success" AS "result of testGetPlayerBooking"
    ELSE 
        SELECT "Fail" AS "result of testGetPlayerBooking"
    END IF;
END;

---------------------------------------------------------

CREATE PROCEDURE testGetPlayerBookingInvalidPlayerId
BEGIN
    --screnario 
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    INSERT INTO cities(city_id, city_name) VALUES (001,"HCM");
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    --test 
    DECLARE @resultCode INT; 
    CALL getPlayerBooking(100a, 001, '2020.01.02', @resultCode)
    IF (@resultCode = 478) THEN
        -- 478 Invalid player_id
        SELECT "Fail" AS "result of testGetPlayerBookingInvalidPlayerId"
    ELSE
        SELECT "Success" AS "result of testGetPlayerBookingInvalidPlayerId" 
    END IF;
END;

---------------------------------------------------------

CREATE PROCEDURE testGetPlayerBookingInvalidCityId
BEGIN
    --screnario 
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    INSERT INTO cities(city_id, city_name) VALUES (001,"HCM");
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    --test
    DECLARE @resultCode INT; 
    CALL getPlayerBooking(111, 0x2, '2020.01.02', @resultCode)
    IF (@resultCode = 461) THEN
        --461 invalid city_id
        SELECT "Fail" AS "result of testGetPlayerBookingInvalidCityId"
    ELSE
        SELECT "Success" AS "result of testGetPlayerBookingInvalidCityId" 
    END IF;
END;

---------------------------------------------------------

CREATE PROCEDURE testGetPlayerBookingInvalidBookingDate
BEGIN
    --screnario 
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    INSERT INTO cities(city_id, city_name) VALUES (001,"HCM");
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    --test
    DECLARE @resultCode INT; 
    CALL getPlayerBooking(111,001,'20.01.02',@resultCode)
    IF (@resultCode = 463) THEN
        --463 invalid date
        SELECT "Fail" AS "result of testGetPlayerBookingInvalidBookingDate"
    ELSE
        SELECT "Success" AS "result of testGetPlayerBookingInvalidBookingDate" 
    END IF;
END;