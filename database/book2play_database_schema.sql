CREATE DATABASE book2play;

DROP TABLE IF EXISTS player;
CREATE TABLE IF NOT EXISTS player(
    player_id INT NOT NULL AUTO_INCREMENT,
    p_name VARCHAR(50),
    /* -- For furture --
    p_email VARCHAR(50) NOT NULL,
    p_username VARCHAR(25) NOT NULL UNIQUE,
    p_password VARCHAR(25) NOT NULL,
    p_phonenumber VARCHAR(10),
    p_address VARCHAR(50), */
    PRIMARY KEY(player_id)
); 

DROP TABLE IF EXISTS staff;
CREATE TABLE IF NOT EXISTS staff(
    staff_id INT NOT NULL AUTO_INCREMENT,
    s_name VARCHAR(50),
    /* -- For furture --
    s_email VARCHAR(50) NOT NULL,
    s_username VARCHAR(25) NOT NULL UNIQUE,
    s_password VARCHAR(25) NOT NULL,
    s_phonenumber VARCHAR(10),
    s_address VARCHAR(50), */
    sportcenter_id int,
    PRIMARY KEY(staff_id),
    FOREIGN KEY(sportcenter_id) REFERENCES staffs(sportcenter_id) ON DELETE SET NULL

); 

DROP TABLE IF EXISTS booking;
CREATE TABLE IF NOT EXISTS booking(
    booking_id INT NOT NULL AUTO_INCREMENT,
    booking_time DATETIME NOT NULL,
    booking_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    booking_state BOOLEAN DEFAULT 0, -- 2 state: 0 unpaid; 1 paid
    player_id INT,
    court_id INT,
    PRIMARY KEY(booking_id),
    FOREIGN KEY(player_id) REFERENCES player(player_id) ON DELETE CASCADE,
    FOREIGN KEY(court_id) REFERENCES court(court_id) ON DELETE SET NULL
    
); 

DROP TABLE IF EXISTS city;
CREATE TABLE IF NOT EXISTS city(
    city_id INT NOT NULL AUTO_INCREMENT,
    city_name VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY(city_id)
); 

DROP TABLE IF EXISTS sportcenter;
CREATE TABLE IF NOT EXISTS sportcenter(
    sportcentre_id INT NOT NULL AUTO_INCREMENT,
    sc_name VARCHAR(50) NOT NULL UNIQUE,
    /* -- For furture 
    sc_address VARCHAR(50), */
    city_id INT,
    PRIMARY KEY(sportcenter_id),
    FOREIGN KEY(city_id) REFERENCES city(city_id) ON DELETE CASCADE
); 

DROP TABLE IF EXISTS court;
CREATE TABLE IF NOT EXISTS court(
    court_id INT NOT NULL AUTO_INCREMENT,
    court_name VARCHAR(50) NOT NULL UNIQUE,
    sportcentre_id INT,
    PRIMARY KEY(court_id),
    FOREIGN KEY(sportcentre_id) REFERENCES sportcenter(sportcentre_id) ON DELETE CASCADE
);


DELIMITER //

-- Make booking
DROP PROCEDURE IF EXISTS makeBooking//
CREATE PROCEDURE makeBooking(
    in input_player_id INT,
    in input_court_id INT,
    in input_booking_date DATE,
    in input_start_time TIME,
    in input_end_time TIME,
    out result_code INT
)
BEGIN 
    DECLARE no_book INT;        -- number of booking of that player
    DECLARE no_overlap INT;     -- number of booking overlap with this booking
    DECLARE no_outdate INT;     -- number of past unpaid booking of that player
    DECLARE open_time TIME;
    DECLARE close_time TIME;
    SET open_time = '07:00:00';
    SET close_time = '21:00:00';

    -- If there exists player and court
    IF input_player_id NOT IN (SELECT player_id FROM player) THEN 
        SET result_code = 478;
    ELSEIF input_court_id NOT IN (SELECT court_id FROM court) THEN 
        SET result_code = 469;
    -- start_time and end_time must be in working hour
    ELSEIF input_start_time < open_time OR input_start_time > close_time THEN
        SET result_code = 470;
    ELSEIF input_end_time < open_time OR input_end_time > close_time THEN
        SET result_code = 471;
    -- start_time and end_time must be in interval of 15 minute
    ELSEIF MOD(UNIX_TIMESTAMP(input_start_time),(15 * 60)) <> 0 THEN
        SET result_code = 470;
    ELSEIF MOD(UNIX_TIMESTAMP(input_end_time),(15 * 60)) <> 0 THEN
        SET result_code = 471;
    -- start_time must before end_time and different only for 45m, 1h, 1h15, 1h30
    ELSEIF TIMEDIFF(input_end_time, input_start_time) <> TIME('00:45:00') AND
        TIMEDIFF(input_end_time, input_start_time) <> TIME('01:00:00') AND
        TIMEDIFF(input_end_time, input_start_time) <> TIME('01:15:00') AND 
        TIMEDIFF(input_end_time, input_start_time) <> TIME('01:30:00') THEN 
        SET result_code = 465;
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

-- cancel booking
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
