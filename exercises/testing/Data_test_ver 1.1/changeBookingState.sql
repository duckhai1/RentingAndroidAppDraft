/* test changeBookingState interface */
/* version 1.1 */
    /* ver1.1 sửa tên + định dạng + procedure theo database + add Invalid StaffID testcase */
/* changeBookingState(booking_id, is_paid, staff_id) */ 

CREATE PROCEDURE testChangeBookingState  
BEGIN 
    --screnario
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    INSERT INTO staffs(staff_id, staff_name, sportcenter_id) VALUES (010, 'NCP', 100)
    INSERT INTO sportcenters(sportcenter_id, sportcenter_name, city_id) VALUES (100, 'Dinh Hoa Sport Center',001);
    --test
    DECLARE @resultcode; 
    CALL changeBookingState(001,'TRUE', 010, @resultcode); 
    IF (@resultcode = 203) THEN
        --Success updated
        SELECT "Success" AS "result of testChangeBookingState"
    ELSE 
        SELECT "Fail" AS "result of testChangeBookingState"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testChangeBookingStateInvalidBookingId  
BEGIN 
    --screnario
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    INSERT INTO staffs(staff_id, staff_name, sportcenter_id) VALUES (010, 'NCP', 100)
    INSERT INTO sportcenters(sportcenter_id, sportcenter_name, city_id) VALUES (100, 'Dinh Hoa Sport Center',001);
    --test
    DECLARE @resultcode; 
    CALL changeBookingState(101, 'TRUE', 010, @resultcode); 
    IF (@resultcode = 410) THEN
        -- 410 Booking not found
        SELECT "Fail" AS "result of testChangeBookingStateInvalidBookingId"
    ELSE 
        SELECT "Success" AS "result of testChangeBookingStateInvalidBookingId"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testChangeBookingStateInvalidState  
BEGIN 
    --screnario
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    INSERT INTO staffs(staff_id, staff_name, sportcenter_id) VALUES (010, 'NCP', 100)
    INSERT INTO sportcenters(sportcenter_id, sportcenter_name, city_id) VALUES (100, 'Dinh Hoa Sport Center',001);
    --test
    DECLARE @resultcode; 
    CALL changeBookingState(001,0,010,@resultcode); 
    IF (@resultcode = 466) THEN
        -- 466 Invalid state
        SELECT "Fail" AS "result of testChangeBookingStateInvalidState"
    ELSE 
        SELECT "Success" AS "result of testChangeBookingStateInvalidState"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testChangeBookingStateInvalidStaffId
BEGIN 
    --screnario
    INSERT INTO bookings(booking_id, booking_date, booking_start_time, booking_end_time, created_at, is_paid, is_cancelled, player_id, court_id) VALUES (001, '2020.01.02', '10:00:00', '11:00:00', '2020.01.01 10:00:00', 'FALSE', 'FALSE', 111, 10);
    INSERT INTO staffs(staff_id, staff_name, sportcenter_id) VALUES (010, 'NCP', 100)
    INSERT INTO sportcenters(sportcenter_id, sportcenter_name, city_id) VALUES (100, 'Dinh Hoa Sport Center',001);
    --test
    DECLARE @resultcode; 
    CALL changeBookingState(001, 'TRUE', 011, @resultcode); 
    IF (@resultcode = 479) THEN
        -- 479 Booking not found
        SELECT "Fail" AS "result of testChangeBookingStateInvalidStaffId"
    ELSE 
        SELECT "Success" AS "result of testChangeBookingStateInvalidStaffId"
    END IF;
END; 