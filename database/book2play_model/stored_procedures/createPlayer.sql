/*
    Creates a player identified by player id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS createPlayer //
CREATE PROCEDURE createPlayer (
	IN inPlayerId VARCHAR(100)
)
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

    START TRANSACTION;

	IF inPlayerId REGEXP '[^a-zA-Z0-9]+$' THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 464; -- invalid player id 
	END IF;

    IF inPlayerId IN (SELECT playerId FROM players) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 405; -- Player already exists
    END IF;
           
    INSERT INTO players (playerId) 
    VALUES (inPlayerId);
END//

DELIMITER ;
