/*
	Change the name of a player given the player id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS updatePlayerId//
CREATE PROCEDURE updatePlayerId (
    IN newPlayerId VARCHAR(100),
    IN inPlayerId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF newPlayerId REGEXP '[^a-zA-Z0-9]+' THEN
		SET statusCode = 464; -- invalid player id
	ELSEIF inPlayerId NOT IN (SELECT playerId FROM players) THEN 
		SET statusCode = 464; -- invalid player id
    ELSEIF newPlayerId IN (SELECT playerId FROM players) THEN 
		SET statusCode = 405; -- invalid player id
    ELSE
        SET statusCode = 200;
        UPDATE players
        SET playerId = newPlayerId
        WHERE playerId = inPlayerId;
    END IF;
END//
 
 DELIMITER ;