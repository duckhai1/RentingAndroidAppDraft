/*
	Get all information about a player given the player id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getPlayerInfo//
CREATE PROCEDURE getPlayerInfo (
	IN inPlayerId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF inPlayerId NOT IN (SELECT playerId FROM players) THEN
		SET statusCode = 464;
    ELSE
		SET statusCode = 200;
		SELECT playerId
		FROM players
		WHERE playerId = inPlayerId;
	END IF;
END//

DELIMITER ;