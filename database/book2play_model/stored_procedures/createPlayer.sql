/*
    Creates a player identified by player id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS createPlayer //
CREATE PROCEDURE createPlayer (
	IN inPlayerId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF inPlayerId REGEXP '[^a-zA-Z0-9]+' THEN
		SET statusCode = 464; -- invalid player id 
	ELSEIF inPlayerId IN (SELECT playerId FROM players) THEN
		SET statusCode = 405; -- Player already exists
    ELSE
		SET statusCode = 200;
		INSERT INTO players (playerId) 
		VALUES (inPlayerId);
	END IF;
END//

DELIMITER ;
