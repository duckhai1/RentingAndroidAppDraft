/*
    Logout a player using token
*/
DELIMITER //

DROP PROCEDURE IF EXISTS logoutPlayer //
CREATE PROCEDURE logoutPlayer (
	IN inToken VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF inToken NOT IN (SELECT token FROM players) THEN
		SET statusCode = 401; -- invalid token
    ELSE
		SET statusCode = 200;
		UPDATE players
		SET token = ""
		WHERE token = inToken;
	END IF;
END//

DELIMITER ;
