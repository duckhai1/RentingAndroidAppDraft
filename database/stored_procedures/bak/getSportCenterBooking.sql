DROP PROCEDURE IF EXISTS getSportCenterBooking//
CREATE PROCEDURE getSportCenterBooking(
	in 	input_sportcenter_name VARCHAR(50),
    in  input_book_date DATE,
    out result_code INT
)
BEGIN
	IF input_sportcenter_name NOT in (SELECT sc_name
									FROM sportcenter
									NATURAL JOIN court 
                                    NATURAL JOIN booking
	) THEN
		SET result_code = 462;
	ELSEIF input_book_date NOT in (SELECT booking_date FROM booking) THEN
		SET result_code = 463;
	ELSE
		SET result_code = 200;
        SELECT * 
        FROM booking
        WHERE sc_name = input_sportcenter_name AND booking_date = input_book_date;
    END IF;
END//