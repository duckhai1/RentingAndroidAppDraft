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
    START TRANSACTION;

	IF newPlayerId REGEXP '[^a-zA-Z0-9]+$' THEN
		SET statusCode = 464; -- invalid player id
	ELSEIF inPlayerId NOT IN (SELECT playerId FROM players) THEN 
        SIGNAL SQLSTATE '45000'
            SET MYSQL_ERRNO = 464; -- invalid player id
    ELSEIF newPlayerId IN (SELECT playerId FROM players) THEN 
        SIGNAL SQLSTATE '45000'
            SET MYSQL_ERRNO = 405; -- player Id already exists
    ELSE
        SET statusCode = 200;
        UPDATE players
        SET playerId = newPlayerId
        WHERE playerId = inPlayerId;
    END IF;
END//
 
 DELIMITER ;