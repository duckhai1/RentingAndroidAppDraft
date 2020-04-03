/*
	Change the name of a staff given the staff id
*/
DELIMITER //

DROP PROCEDURE IF EXISTS updateStaffName //
CREATE PROCEDURE updateStaffName (
    IN inStaffId INT,
    IN inStaffName VARCHAR(64)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET STACKED DIAGNOSTICS CONDITION 1 @p1 = MYSQL_ERRNO, @p2 = MESSAGE_TEXT;
		SELECT @p1 AS `STATUS_CODE`, @p2 AS `STATUS_MESSAGE`;
		ROLLBACK;
	END;

	START TRANSACTION;

    IF inStaffId NOT IN (SELECT staffId FROM staffs) THEN 
        SIGNAL SQLSTATE '45000'
            SET MYSQL_ERRNO = 479;
    END IF;

    UPDATE staffs
    SET staffName = inStaffName
    WHERE staffId = inStaffId;
END//

DELIMITER ;