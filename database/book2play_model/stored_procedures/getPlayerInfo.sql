/*
	Get all information about a player given the player id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getPlayerInfo//
CREATE PROCEDURE getPlayerInfo (
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

	IF inPlayerId NOT IN (SELECT playerId FROM players) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 464;
	END IF;

    SET statusCode = 200;

	SELECT playerId
	FROM players
	WHERE playerId = inPlayerId;
END//

DELIMITER ;