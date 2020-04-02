/*
	Change the name of a sport center given the sport center id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS updateSportCenterName //
CREATE PROCEDURE updateSportCenterName (
    IN inSportcenterId INT,
    IN inSportcenterName VARCHAR(64),
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

    START TRANSACTION;

    -- test if parameter exist
    IF inSportcenterId NOT IN (SELECT sportcenterId FROM sportcenters) THEN 
        SIGNAL SQLSTATE '45000'
            SET MYSQL_ERRNO = 462;
    END IF;

    UPDATE sportcenters
    SET sportcenterName = inSportCenterName
    WHERE sportcenterId = inSportcenterId;
END//

DELIMITER ;