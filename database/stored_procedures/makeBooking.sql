-- Make booking
DELIMITER //
DROP PROCEDURE IF EXISTS makeBooking//
CREATE PROCEDURE makeBooking(
    IN input_player_id INT,
    IN input_court_id INT,
    IN input_date DATE,
    IN input_start_time TIME,
    IN input_end_time TIME,
    OUT result_code INT
)
BEGIN 
    DECLARE n_bookings INT; -- number of bookings of that player
    DECLARE n_overlaps INT; -- number of bookings overlap with the new booking
    DECLARE n_outdated INT; -- number of past unpaid booking of that player
    DECLARE open_time TIME;
    DECLARE close_time TIME;

    SET open_time = '07:00:00';
    SET close_time = '21:00:00';

    IF input_player_id NOT IN (SELECT player_id FROM player) THEN 
        SET result_code = 478; -- player is does not exist
    ELSE IF input_court_id NOT IN (SELECT court_id FROM court) THEN 
        SET result_code = 469; -- court id does not exist
    ELSE IF input_start_time < open_time
    OR input_start_time > close_time
    OR MOD(UNIX_TIMESTAMP(input_start_time, 15 * 60)) <> 0 THEN
        SET result_code = 470; -- start time is not in working hour or is not in interval of 15 minutes with 00h00m is the start
    ELSE IF input_end_time < open_time
    OR input_end_time > close_time
    OR MOD(UNIX_TIMESTAMP(input_end_time),(15 * 60)) <> 0 THEN
        SET result_code = 471; -- end time is not in working hour or is not in interval of 15 minutes with 00h00m is the start
    ELSE IF TIMEDIFF(input_end_time, input_start_time) <> TIME('00:45:00')
    AND TIMEDIFF(input_end_time, input_start_time) <> TIME('01:00:00')
    AND TIMEDIFF(input_end_time, input_start_time) <> TIME('01:15:00')
    AND TIMEDIFF(input_end_time, input_start_time) <> TIME('01:30:00') THEN 
        SET result_code = 465; -- invalid duration, interval between start time and end time must be 45m, 1h, 1h15, or 1h30
    ELSE 
        -- test if player have more than 3 booking upcoming
        SELECT count(*) 
        INTO no_book
        FROM booking 
        WHERE player_id = input_player_id 
            AND cast(concat(booking_date, ' ', booking_time) as datetime) > NOW(); -- starting datatime after current time

        -- test if the booking is overlapped
        SELECT count(*)
        INTO no_overlap
        FROM booking 
        WHERE booking_id in (SELECT booking_id 
                             FROM booking 
                             WHERE 
                                ((start_time>input_start_time AND start_time<input_end_time) 
                                    OR (start_time<=input_start_time AND end_time>input_start_time))
                                AND (booking_date = input_booking_date)
                                AND (court_id = input_court_id)
                            );

        -- test if the player havie any past unpaid booking
        SELECT count(*)
        INTO no_outdate
        FROM booking
        WHERE booking_id in (SELECT booking_id
                             FROM booking
                             WHERE cast(concat(booking_date, ' ', booking_time) as datetime) > NOW() 
                                AND booking_state = 0
                                AND player_id = input_player_id);

        IF no_book > 3 THEN 
            SET result_code = 411;
        ELSEIF no_overlap > 0 THEN
            SET result_code = 464;
        ELSEIF no_outdate > 0 THEN 
            SET result_code = 413;
        ELSE 
            SET result_code = 202;
            INSERT INTO booking 
            Values (NULL, NOW(), input_start_time, input_end_time, 0, input_player_id, input_court_id);
        END IF;
    END IF;
End//

DELIMITER ;