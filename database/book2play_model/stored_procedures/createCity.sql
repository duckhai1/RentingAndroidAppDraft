/* 
     Creates a city identified by a city id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS createCity //
CREATE PROCEDURE createCity (
	IN inCityId VARCHAR(100),
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

	IF inCityId REGEXP '[^a-zA-Z0-9]+$' THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 460 ; -- invalid city id 
	END IF;
   
    IF inCityId IN (SELECT cityId FROM cities) THEN
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 402 ; -- city already exists 
	END IF;

    INSERT INTO cities (cityId)
    VALUES (inCityId);
    SET statusCode = 200;
END//

DELIMITER ;
