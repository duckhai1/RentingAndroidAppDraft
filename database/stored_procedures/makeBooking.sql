-- MakeBooking
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
    DECLARE open_time TIME;
    DECLARE close_time TIME;
    SET open_time = '07:00:00';
    SET close_time = '21:00:00';

    IF input_player_id NOT IN (SELECT player_id FROM player) THEN
        SET result_code = 478; -- player is does not exist
    ELSEIF input_court_id NOT IN (SELECT court_id FROM court) THEN
        SET result_code = 469; -- court id does not exist
    ELSEIF input_start_time < open_time
		OR input_start_time > close_time
		OR MOD(UNIX_TIMESTAMP(input_start_time), 15 * 60) <> 0 THEN
        SET result_code = 470; -- start time is not in working hour or is not in interval of 15 minutes with 00h00m is the start
    ELSEIF input_end_time < open_time
		OR input_end_time > close_time
		OR MOD(UNIX_TIMESTAMP(input_end_time),(15 * 60)) <> 0 THEN
        SET result_code = 471; -- end time is not in working hour or is not in interval of 15 minutes with 00h00m is the start
    ELSEIF TIMEDIFF(input_end_time, input_start_time) <> TIME('00:45:00')
		AND TIMEDIFF(input_end_time, input_start_time) <> TIME('01:00:00')
		AND TIMEDIFF(input_end_time, input_start_time) <> TIME('01:15:00')
		AND TIMEDIFF(input_end_time, input_start_time) <> TIME('01:30:00') THEN
        SET result_code = 465; -- invalid duration, interval between start time and end time must be 45m, 1h, 1h15, or 1h30
	ELSEIF (
        SELECT count(*)
        FROM booking
        WHERE player_id = input_player_id
            AND CAST(CONCAT(booking_date, ' ', booking_start_time) AS DATETIME) > NOW()
    ) >= 3 THEN
		SET result_code = 411; -- the user already has 3 upcomming bookings
	ELSEIF (
		SELECT count(*)
        FROM booking
        WHERE court_id = input_court_id
			AND booking_date = input_date
			AND ((input_start_time < start_time AND input_end_time > start_time)
				OR (input_start_time > start_time AND input_start_time < end_time))
    ) > 0 THEN
		SET result_code = 464; -- the booking's period overlaps with another existing booking
    ELSEIF (
		SELECT count(*)
        FROM booking
		WHERE player_id = input_player_id
			AND is_paid = FALSE
			AND is_cancelled = FALSE
			AND cast(concat(booking_date, ' ', booking_time) as datetime) < NOW()
    ) > 0 THEN
		SET result_code = 413;
    ELSE
		SET result_code = 202;
        
		INSERT INTO booking (booking_date, booking_start_time, booking_end_time, created_at, player_id, court_id)
		VALUES (input_date, input_start_time, input_end_time, NOW(), input_player_id, input_court_id);
    END IF;
END//

DELIMITER ;
