USE book2play;

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

-- update player infomation
DROP PROCEDURE IF EXISTS updatePlayerName//
CREATE PROCEDURE updatePlayerName(
    in input_player_id INT,
    in input_name VARCHAR(50),
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_player_id NOT IN (SELECT player_id FROM player) THEN 
        SET result_code = 478;
    ELSE
        SET result_code = 203;
        UPDATE player 
        SET p_name = input_name
        WHERE player_id = input_player_id;
    END IF;
END//
 
 /* -- for furture --
DROP PROCEDURE IF EXISTS updatePlayerPhonenumber//
CREATE PROCEDURE updatePlayerPhonenumber(
    in input_player_id INT,
    in input_phonenumber VARCHAR(10),
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_player_id NOT IN (SELECT player_id FROM player) THEN 
        SET result_code = 478;
    ELSE
        SET result_code = 203;
        UPDATE player 
        SET p_phonenumber = input_phonenumber
        WHERE player_id = input_player_id;
    END IF;
END//
DROP PROCEDURE IF EXISTS updatePlayerAddress//
CREATE PROCEDURE updatePlayerAddress(
    in input_player_id INT,
    in input_address VARCHAR(50),
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_player_id NOT IN (SELECT player_id FROM player) THEN 
        SET result_code = 478;
    ELSE
        SET result_code = 203;
        UPDATE player 
        SET p_address = input_address
        WHERE player_id = input_player_id;
    END IF;
END//
DROP PROCEDURE IF EXISTS updatePlayerEmail//
CREATE PROCEDURE updatePlayerEmail(
    in input_player_id INT,
    in input_email VARCHAR(50),
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_player_id NOT IN (SELECT player_id FROM player) THEN 
        SET result_code = 478;
    ELSE
        SET result_code = 203;
        UPDATE player 
        SET p_email = input_email
        WHERE player_id = input_player_id;
    END IF;
END//
*/


-- update staff info

DROP PROCEDURE IF EXISTS updateStaffName//
CREATE PROCEDURE updateStaffName(
    in input_staff_id INT,
    in input_name VARCHAR(50),
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_staff_id NOT IN (SELECT staff_id FROM staff) THEN 
        SET result_code = 479;
    ELSE
        SET result_code = 203;
        UPDATE staff 
        SET s_name = input_name
        WHERE staff_id = input_staff_id;
    END IF;
END//


/* -- for furture --
DROP PROCEDURE IF EXISTS updateStaffPhonenumber//
CREATE PROCEDURE updateStaffPhonenumber(
    in input_staff_id INT,
    in input_phonenumber VARCHAR(10),
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_staff_id NOT IN (SELECT staff_id FROM staff) THEN 
        SET result_code = 479;
    ELSE
        SET result_code = 203;
        UPDATE staff 
        SET s_phonenumber = input_phonenumber
        WHERE staff_id = input_staff_id;
    END IF;
END//
DROP PROCEDURE IF EXISTS updateStaffAddress//
CREATE PROCEDURE updateStaffAddress(
    in input_staff_id INT,
    in input_address VARCHAR(50),
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_staff_id NOT IN (SELECT staff_id FROM staff) THEN 
        SET result_code = 479;
    ELSE
        SET result_code = 203;
        UPDATE staff 
        SET s_address = input_address
        WHERE staff_id = input_staff_id;
    END IF;
END//
DROP PROCEDURE IF EXISTS updateStaffEmail//
CREATE PROCEDURE updateStaffEmail(
    in input_staff_id INT,
    in input_email VARCHAR(50),
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_staff_id NOT IN (SELECT staff_id FROM staff) THEN 
        SET result_code = 479;
    ELSE
        SET result_code = 203;
        UPDATE staff 
        SET s_email = input_email
        WHERE staff_id = input_staff_id;
    END IF;
END//

-- update sport center information
DROP PROCEDURE IF EXISTS updateSportCenterName//
CREATE PROCEDURE updateSportCenterName(
    in input_sportcenter_id INT,
    in input_name VARCHAR(50),
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_sportcenter_id NOT IN (SELECT sportcentre_id FROM sportcenter) THEN 
        SET result_code = 462;
    ELSE
        SET result_code = 203;
        UPDATE sportcenter 
        SET sc_name = input_name
        WHERE sportcentre_id = input_sportcenter_id;
    END IF;
END//

DROP PROCEDURE IF EXISTS updateSportCenterAddress//
CREATE PROCEDURE updateSportCenterAddress(
    in input_sportcenter_id INT,
    in input_address VARCHAR(50),
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_sportcenter_id NOT IN (SELECT sportcentre_id FROM sportcenter) THEN 
        SET result_code = 462;
    ELSE
        SET result_code = 203;
        UPDATE sportcenter 
        SET sc_address = input_address
        WHERE sportcentre_id = input_sportcenter_id;
    END IF;
END//
*/

DROP PROCEDURE IF EXISTS updateStaffSportCenterId//
CREATE PROCEDURE updateStaffSportCenterId(
    in input_staff_id INT,
    in input_sportcenter_id INT,
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_staff_id NOT IN (SELECT staff_id FROM staff) THEN 
        SET result_code = 479;
    ELSEIF input_sportcenter_id NOT IN (SELECT sportcenter_id FROM sportcenter) THEN
        SET result_code = 462;
    ELSE
        SET result_code = 203;
        UPDATE staff 
        SET sportcenter_id = input_sportcenter_id
        WHERE staff_id = input_staff_id;
    END IF;
END//

-- update sport center information
DROP PROCEDURE IF EXISTS updateSportCenterCityId//
CREATE PROCEDURE updateSportCenterCityId(
    in input_sportcenter_id INT,
    in input_city_id INT,
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_sportcenter_id NOT IN (SELECT sportcentre_id FROM sportcenter) THEN 
        SET result_code = 462;
    ELSEIF input_city_id NOT IN (SELECT city_id FROM city) THEN 
        SET result_code = 461;  
    ELSE
        SET result_code = 203;
        UPDATE sportcenter 
        SET city_id = input_city_id
        WHERE sportcentre_id = input_sportcenter_id;
    END IF;
END//
DROP PROCEDURE IF EXISTS updateSportCenterName//
CREATE PROCEDURE updateSportCenterName(
    in input_sportcenter_id INT,
    in input_name VARCHAR(50),
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_sportcenter_id NOT IN (SELECT sportcentre_id FROM sportcenter) THEN 
        SET result_code = 462;
    ELSE
        SET result_code = 203;
        UPDATE sportcenter 
        SET sc_name = input_name
        WHERE sportcentre_id = input_sportcenter_id;
    END IF;
END//

-- update court information
DROP PROCEDURE IF EXISTS updateCourtSportCenterLocation//
CREATE PROCEDURE updateCourtSportCenterLocation(
    in input_court_id INT,
    in input_sportcenter_id INT,
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_sportcenter_id NOT IN (SELECT sportcentre_id FROM sportcenter) THEN 
        SET result_code = 462;
    ELSEIF input_court_id NOT IN (SELECT court_id FROM court) THEN 
        SET result_code = 469;  
    ELSE
        SET result_code = 203;
        UPDATE court 
        SET sportcentre_id = input_sportcenter_id
        WHERE court_id = input_court_id;
    END IF;
END//

DROP PROCEDURE IF EXISTS updateCourtName//
CREATE PROCEDURE updateCourtName(
    in input_court_id INT,
    in input_name VARCHAR(50),
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_court_id NOT IN (SELECT court_id FROM court) THEN 
        SET result_code = 469; 
    ELSE
        SET result_code = 203;
        UPDATE court 
        SET court_name = input_name
        WHERE court_id = input_court_id;
    END IF;
END//

-- update city information
DROP PROCEDURE IF EXISTS updateCityName//
CREATE PROCEDURE updateCityName(
    in input_city_id INT,
    in input_name VARCHAR(50),
    out result_code INT 
)
BEGIN
    -- test if parameter exist
    IF input_city_id NOT IN (SELECT city_id FROM city) THEN 
        SET result_code = 461;
    ELSE
        SET result_code = 203;
        UPDATE city 
        SET city_name = input_name
        WHERE city_id = input_city_id;
    END IF;
END//

-- Get Booking 
DROP PROCEDURE IF EXISTS getBooking//
CREATE PROCEDURE getBooking(
    in input_booking_id INT,
    in input_book_date DATE,
    out result_code INT
)
BEGIN
    IF input_booking_id NOT in (SELECT booking_id FROM booking ) THEN 
        SET result_code = 461;
    ELSEIF input_book_date NOT in (SELECT booking_date FROM booking ) THEN 
        SET result_code = 463;
    ELSE
        SET result_code = 200;
        SELECT * 
        FROM booking 
        WHERE booking_id = input_booking_id AND booking_date = input_book_date ;
    END IF;
END//


 -- Get Player Booking
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

 -- Get Sport Center Booking
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

 -- Get Player Info
DROP PROCEDURE IF EXISTS getPlayerInfo//
CREATE PROCEDURE getPlayerInfo(
	in input_player_id INT,
    out result_code INT
)
BEGIN
	IF input_player_id NOT in(SELECT player_id FROM player) THEN
		SET result_code = 478;
    ELSE
        SET result_code = 201;
        SELECT *
        FROM player
        WHERE player_id = input_player_id;
	END IF;
END//

 -- Get Staff Info
DROP PROCEDURE IF EXISTS getStaffInfo//
CREATE PROCEDURE getStaffInfo(
	in input_staff_id INT,
    out result_code INT
)
BEGIN
	IF input_staff_id NOT in(SELECT input_staff_id FROM staff) THEN
			SET result_code = 479;
	ELSE
		    SET result_code = 201;
            SELECT *
            FROM staff
            WHERE staff_id = input_staff_id;
	END IF;
END//
DELIMITER ;
