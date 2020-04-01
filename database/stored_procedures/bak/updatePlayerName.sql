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
 