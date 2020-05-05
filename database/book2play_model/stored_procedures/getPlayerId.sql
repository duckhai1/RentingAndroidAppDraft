/*
    Get playerId given the token
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getPlayerId //
CREATE PROCEDURE getPlayerId (
	IN inToken VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF inToken NOT IN (SELECT token FROM players) THEN
		SET statusCode = 464; -- player does not exist
    ELSE
        SELECT playerId
        FROM players
        WHERE token = inToken;
	END IF;
END//
DELIMITER ;