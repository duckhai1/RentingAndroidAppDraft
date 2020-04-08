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
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

    START TRANSACTION;

	IF newPlayerId REGEXP '[^a-zA-Z0-9]+$' THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 464; -- invalid player id
	END IF;

    IF inPlayerId NOT IN (SELECT playerId FROM players) THEN 
        SIGNAL SQLSTATE '45000'
            SET MYSQL_ERRNO = 464; -- invalid player id
    END IF;

    IF newPlayerId IN (SELECT playerId FROM players) THEN 
        SIGNAL SQLSTATE '45000'
            SET MYSQL_ERRNO = 405; -- player Id already exists
    END IF;

    UPDATE players
    SET playerId = newPlayerId
    WHERE playerId = inPlayerId;
END//
 
 DELIMITER ;