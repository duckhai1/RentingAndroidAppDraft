-- cancel booking
DELIMITER //

DROP PROCEDURE IF EXISTS cancelBooking//

CREATE PROCEDURE cancelBooking(
    in input_booking_id INT,
    in input_player_id INT,
    out result_code INT
)
BEGIN 
    -- test if parameter exist
    IF input_booking_id NOT IN (SELECT booking_id FROM booking) THEN 
        SET result_code = 410;
    ELSEIF input_player_id NOT IN (SELECT player FROM player) THEN 
        SET result_code = 478; 
    -- if player have permission
    ELSEIF input_player_id <> (SELECT player_id FROM booking WHERE booking_id = input_booking_id) THEN
        SET result_code = 401;
    -- test if cancel was late
    ELSEIF TIMEDIFF((SELECT (cast(concat(booking_date, ' ', booking_time) as datetime))
                     FROM booking 
                     WHERE booking_id = input_booking_id),
                    NOW()
        ) < TIME('24:00:00') THEN 
        SET result_code = 412;
    ELSE 
        -- cancel the booking 
        SET result_code = 200;
        DELETE FROM booking WHERE booking_id = input_booking_id;
    END IF;
END//

DELIMITER ;
