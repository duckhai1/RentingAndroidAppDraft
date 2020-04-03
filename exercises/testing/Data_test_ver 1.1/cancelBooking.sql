/* test cancelBooking interface */ 
/* version 1.1 */
    /* ver1.1 sửa lỗi tên + datatype theo database */
/* cancelBooking(booking_id, player_id) */ 

CREATE PROCEDURE testCancelBooking  
BEGIN 
    --screnario
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    --test
    DECLARE @resultcode; 
    CALL cancelBooking(001, 111, @resultcode); 
    IF (@resultcode = 200) THEN
        --Success
        SELECT "Success" AS "result of testCancelBooking"
    ELSE 
        SELECT "Fail" AS "result of testCancelBooking"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testCancelBookingLate  
BEGIN 
    --screnario
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    --test
    DECLARE @resultcode; 
    CALL cancelBooking(001, 111,@resultcode); 
    IF (@resultcode = 412) THEN
        -- 412 Cancelation is rejected (more than 24h)
        SELECT "Fail" AS "result of testCancelBookingLate"
    ELSE 
        SELECT "Success" AS "result of testCancelBookingLate"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testCancelBookingInvalidBookingId  
BEGIN 
    --screnario
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    --test
    DECLARE @resultcode; 
    CALL cancelBooking(011, 111,@resultcode); 
    IF (@resultcode = 410) THEN
        -- 410 Booking is not found 
        SELECT "Fail" AS "result of testCancelBookingInvalidBookingId"
    ELSE 
        SELECT "Success" AS "result of testCancelBookingInvalidBookingId"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testCancelBookingInvalidPlayerId
BEGIN 
    --screnario
    INSERT INTO booking (booking_id, booking_time, start_time, end_time, booking_state, player_id, court_id) VALUES (001,'01.01.2020','02.01.2020 10:00', '02.01.2020 11:00',1,111,10);
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    --test
    DECLARE @resultcode; 
    CALL cancelBooking(011, 112,@resultcode); 
    IF (@resultcode = 478) THEN
        -- 478 Booking is not found 
        SELECT "Fail" AS "result of testCancelBookingInvalidPlayerId"
    ELSE 
        SELECT "Success" AS "result of testCancelBookingInvalidPlayerId"
    END IF;
END; 