/*
    Check if player exist or not
*/
DELIMITER //

DROP PROCEDURE IF EXISTS isPlayerExist //
CREATE PROCEDURE isPlayerExist (
	IN inPlayerId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF inPlayerId NOT IN (SELECT playerId FROM players) THEN
		SET statusCode = 464; -- player does not exist
    ELSEIF inPlayerId IN (SELECT playerId FROM players) THEN
		SET statusCode = 405; -- Player already exists
	END IF;
END//
DELIMITER ;
