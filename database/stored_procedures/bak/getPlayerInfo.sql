DELIMITER //

DROP PROCEDURE IF EXISTS getPlayerInfo//
CREATE PROCEDURE getPlayerInfo(
	IN inPlayerId INT,
    OUT resultCode INT
)
BEGIN
	IF inPlayerId NOT in(SELECT playerId FROM players) THEN
		SET resultCode = 478;
    ELSE
        SET resultCode = 201;
        SELECT *
        FROM player
        WHERE playerId = inPlayerId;
	END IF;
END//

DELIMITER ;