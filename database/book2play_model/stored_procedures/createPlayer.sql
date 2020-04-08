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
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO;
		SET statusCode = @p1;
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
    SET statusCode = 200;
END//

DELIMITER ;
