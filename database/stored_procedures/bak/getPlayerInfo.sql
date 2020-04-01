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

