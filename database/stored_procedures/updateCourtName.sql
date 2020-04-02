-- Update court name
DELIMITER //

DROP PROCEDURE IF EXISTS updateCourtName//
CREATE PROCEDURE updateCourtName (
    IN inCourtId INT,
    IN inCourtName VARCHAR(64)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

    IF inCourtId NOT IN (SELECT courtId FROM courts) THEN 
		SIGNAL SQLSTATE '45000'
			SET MYSQL_ERRNO = 469; -- test if parameter exist
    END IF;

	UPDATE courts 
	SET courtName = inCourtName
	WHERE courtId = inCourtId;
END//

DELIMITER ;
