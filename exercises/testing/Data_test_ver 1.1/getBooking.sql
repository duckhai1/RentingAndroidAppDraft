/* test getBooking interface */
/* version 1.1 */
    /* ver1.1 sửa tên + sửa theo database + Invalid bookingId errorCode */
/* getBooking(booking_id, booking_date) */ 


CREATE PROCEDURE testGetBooking
BEGIN
    --screnario
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    --test
    DECLARE @resultCode INT;
	CALL getBooking (001,'2020.01.02',@resultCode)
    IF (@resultCode = 200) THEN
        --Success
        SELECT "Success" AS "result of testGetBooking"
    ELSE 
        SELECT "Fail" AS "result of testGetBooking"
    END IF;
END;

---------------------------------------------------------

CREATE PROCEDURE testGetPlayerBookingInvalidBookingId
BEGIN 
    --screnario 
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    --test
    DECLARE @resultCode INT;
	CALL getBooking (002,'2020.01.02',@resultCode)
    IF (@resultCode = ???) THEN
        -- ??? Invalid BookingId
        SELECT "Fail" AS "result of testGetPlayerBookingInvalidBookingId"
    ELSE
        SELECT "Success" AS "result of testGetPlayerBookingInvalidBookingId"
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
    CALL getBooking(001,'20.01.02',@resultCode)
    IF (@resultCode = 463) THEN
        -- 463 Invalid date 
        SELECT "Fail" AS "result of testGetPlayerBookingInvalidBookingDate"
    ELSE
        SELECT "Success" AS "result of testGetPlayerBookingInvalidBookingDate"
    END IF;
END;
