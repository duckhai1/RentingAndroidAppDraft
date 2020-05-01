/*
    Creates a player from playerId and password
*/
DELIMITER //

DROP PROCEDURE IF EXISTS signupPlayer //
CREATE PROCEDURE signupPlayer (
	IN inPlayerId VARCHAR(100),
    IN inPlayerPass VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	SET inPlayerId = CONCAT("player", inPlayerId);
	IF inPlayerId REGEXP '[^a-zA-Z0-9]+' THEN
		SET statusCode = 464; -- invalid player id 
	ELSEIF inPlayerId IN (SELECT playerId FROM players) THEN
		SET statusCode = 405; -- Player already exists
    ELSE
		SET statusCode = 200;
		INSERT INTO players (playerId, playerPass) 
		VALUES (inPlayerId, inPlayerPass);
	END IF;
END//

DELIMITER ;
