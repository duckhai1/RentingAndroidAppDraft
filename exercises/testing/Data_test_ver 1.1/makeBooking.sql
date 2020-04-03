/* test makeBooking interface */ 
/* version 1.1 */ 
    /* ver1.1 sửa theo database tên + ngày tháng */
/* makeBooking(player_id, court_id, booking_date, booking_start_time, booking_end_time) */ 

CREATE PROCEDURE testMakeBooking 
BEGIN 
    --screnario
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    --test
    DECLARE @resultcode; 
    CALL makeBooking(111,10,'2020.01.02', '10:00:00','11:00:00',@resultcode);
    IF (@resultcode = 201) THEN
        --Success
        SELECT "Success" AS "result of testMakeBooking"
    ELSE 
        SELECT "Fail" AS "result of testMakeBooking"
    END IF;
END;  

---------------------------------------------------------

CREATE PROCEDURE testMakeBookingInvalidBooking 
BEGIN 
    --screnario 
    INSERT INTO players(player_id, player_name) VALUES (112,"NP")
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    --test 
    DECLARE @resultcode; 
    CALL makeBooking(112,10,'2020.01.02', '9:45:00','10:45:00',@resultcode);
    IF (@resultcode = 464) THEN
        --464 invalid Booking Slot
        SELECT "Fail" AS "result of testMakeBookingInvalidBooking"
    ELSE
        SELECT "Success" AS "result of testMakeBookingInvalidBooking"
    END IF; 
END; 

---------------------------------------------------------

CREATE PROCEDURE testMakeBookingPendingPayment 
BEGIN
    --screnario
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
            --Insert an unpaid booking
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    --test 
    DECLARE @resultcode;
    CALL makeBooking(111,10,'2020.01.03', '9:45:00','10:45:00',@resultcode);
    IF (@resultcode = 413) THEN 
        -- 413 unpaid booking found  
        SELECT "Fail" AS "result of testMakeBookingPendingPayment"
    ELSE
        SELECT "Success" AS "result of testMakeBookingPendingPayment"
    END IF; 
END;

---------------------------------------------------------

CREATE PROCEDURE testMakeBookingLimitExceed
BEGIN 
    --screnario 
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
        --insert 3 upcoming booking 
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.03', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.04', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    --test 
    DECLARE @resultcode;
    CALL makeBooking(111,13,'2020.01.05','10:00:00', '10:45:00',@resultcode);
    IF (@resultcode = 411) THEN 
        -- 411 booking limit reached  
        SELECT "Fail" AS "result of testMakeBookingLimitExceed"
    ELSE
        SELECT "Success" AS "result of testMakeBookingLimitExceed"
    END IF; 
END; 

---------------------------------------------------------

CREATE PROCEDURE testMakeBookingUnauthorized
BEGIN
    --screnario 
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    --test 
    DECLARE @resultcode;
    CALL makeBooking(112,13,'2020.01.05','10:00:00', '10:45:00',@resultcode);
    IF (@resultcode = 401) THEN 
        -- 401 Unauthorized player
        SELECT "Fail" AS "result of testMakeBookingUnauthorized"
    ELSE
        SELECT "Success" AS "result of testMakeBookingUnauthorized"
    END IF; 
END; 

---------------------------------------------------------

CREATE PROCEDURE testMakeBookingInvalidCourtId
BEGIN
    --screnario 
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    --test 
    DECLARE @resultcode;
    CALL makeBooking(111,100,'2020.01.05','10:00:00', '10:45:00',@resultcode);
    IF (@resultcode = 469) THEN 
        --469 Invalid court_id
        SELECT "Fail" AS "result of testMakeBookingInvalidCourtId"
    ELSE
        SELECT "Success" AS "result of testMakeBookingInvalidCourtId"
    END IF; 
END; 

---------------------------------------------------------

CREATE PROCEDURE testMakeBookingInvalidDuration
BEGIN
    --screnario 
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    --test 
    DECLARE @resultcode;
    CALL makeBooking(111,10,'2020.01.05','10:00:00', '10:50:00',@resultcode);
    IF (@resultcode = 465) THEN 
        -- 465 Invalid duration
        SELECT "Fail" AS "result of testMakeBookingInvalidDuration"
    ELSE
        SELECT "Success" AS "result of testMakeBookingInvalidDuration"
    END IF; 
END;

---------------------------------------------------------

CREATE PROCEDURE testMakeBookingInvalidStartTime
BEGIN
    --screnario 
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    --test 
    DECLARE @resultcode;
    CALL makeBooking(111,10,'2020.01.05','05:00:00', '8:00:00',@resultcode);
    IF (@resultcode = 470) THEN 
        -- 470 Invalid startime
        SELECT "Fail" AS "result of testMakeBookingInvalidStartTime"
    ELSE
        SELECT "Success" AS "result of testMakeBookingInvalidStartTime"
    END IF; 
END;

---------------------------------------------------------

CREATE PROCEDURE testMakeBookingInvalidEndTime
BEGIN
    --screnario 
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    --test 
    DECLARE @resultcode;
    CALL makeBooking(111,10,'2020.01.05','10:00:00', '20:00:00',@resultcode);
    IF (@resultcode = 471) THEN 
        -- 471 Invalid endtime
        SELECT "Fail" AS "result of testMakeBookingInvalidEndTime"
    ELSE
        SELECT "Success" AS "result of testMakeBookingInvalidEndTime"
    END IF; 
END;

