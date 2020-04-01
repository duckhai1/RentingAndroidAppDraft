DROP PROCEDURE IF EXISTS getPlayerBooking//
	CREATE PROCEDURE getPlayerBooking(
	in input_player_id INT,
    in input_city_name INT,
    in input_book_date DATE,
    out result_code INT
)
BEGIN
	IF input_player_id NOT in(SELECT player_id FROM booking) THEN
		SET result_code = 478;
	ELSEIF input_city_name NOT in(SELECT city_name
					            FROM  city
                                NATURAL JOIN sportcenter 
                                NATURAL JOIN court 
                                NATURAL JOIN booking
	) THEN
		SET result_code = 461;
	ELSEIF input_book_date NOT in (SELECT booking_date FROM booking) THEN 
        SET result_code = 463;
    ELSE
        SET result_code = 200;
        SELECT * 
        FROM booking 
        WHERE player_id = input_player_id 
            AND city_name = input_city_name 
            AND booking_date = input_book_date;
    END IF;
END//
