/*
    Login with playerId and password
*/
DELIMITER //

DROP PROCEDURE IF EXISTS loginPlayer //
CREATE PROCEDURE loginPlayer (
	IN inPlayerId VARCHAR(100),
    IN inPlayerPass VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	DECLARE inToken VARCHAR(100);
	SET inPlayerId = CONCAT("player", inPlayerId);
	IF inPlayerId REGEXP '[^a-zA-Z0-9]+' THEN
		SET statusCode = 464; -- invalid player id 
	ELSEIF NOT EXISTS (
		SELECT playerId 
		FROM players
		WHERE playerId = inPlayerId
			AND playerPass = inPlayerPass
	) THEN
		SET statusCode = 401; -- unauthorized
    ELSE
		SET statusCode = 200;

		SET inToken = MD5(CONCAT(NOW(), RAND(), inPlayerId, inPlayerPass));
		WHILE (inToken IN (SELECT token FROM players WHERE token = inToken)) DO 
			SET inToken = MD5(CONCAT(NOW(), RAND(), inPlayerId, inPlayerPass));
		END WHILE;

		UPDATE players
		SET token = inToken
		WHERE playerId = inPlayerId;

		SELECT token 
		FROM players
		WHERE playerId = inPlayerId 
			AND playerPass = inPlayerPass;
	END IF;
END//

DELIMITER ;
