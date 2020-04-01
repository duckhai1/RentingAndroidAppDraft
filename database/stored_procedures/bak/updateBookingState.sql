-- change booking state
DROP PROCEDURE IF EXISTS changeBookingState//
CREATE PROCEDURE changeBookingState(
    in input_booking_id INT,
    in input_booking_state INT,
    in input_staff_id INT,
    out result_code INT
)
BEGIN
    -- test if parameter exist
    IF input_booking_id NOT IN (SELECT booking_id FROM booking) THEN 
        SET result_code = 410;
    ELSEIF input_booking_state NOT IN (SELECT booking_state FROM booking) THEN 
        SET result_code = 466;
    ELSEIF input_staff_id NOT IN (SELECT staff_id FROM staff) THEN 
        SET result_code = 479;
    -- test if staff have permission
    ELSEIF input_staff_id = (SELECT staff_id 
                             FROM booking NATURAL JOIN court 
                                          NATURAL JOIN sportcenter
                             WHERE booking_id = input_booking_id) THEN 
        SET result_code = 401;
    ELSE 
        SET result_code = 203;
        UPDATE booking 
        Set booking_state = 1
        WHERE booking_id = input_booking_id;
    END IF;
END//