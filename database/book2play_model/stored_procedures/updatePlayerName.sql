/*
	Change the name of a player given the player id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS updatePlayerName//
CREATE PROCEDURE updatePlayerName (
    IN inPlayerId INT,
    IN inPlayerName VARCHAR(64)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

    START TRANSACTION;

    IF inPlayerId NOT IN (SELECT playerId FROM players) THEN 
        SIGNAL SQLSTATE '45000'
            SET MYSQL_ERRNO = 478; -- player id does not exist
    END IF;

    UPDATE players
    SET playerName = inPlayerName
    WHERE playerId = inPlayerId;
END//
 
 DELIMITER ;