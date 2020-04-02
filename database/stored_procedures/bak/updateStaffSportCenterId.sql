DELIMITER //

DROP PROCEDURE IF EXISTS updateStaffSportCenterId//
CREATE PROCEDURE updateStaffSportCenterId(
    in inStaffId INT,
    in inSportcenterId INT,
    out resultCode INT 
)
BEGIN
    IF inStaffId NOT IN (SELECT staffId FROM staffs) THEN 
        SET resultCode = 479; -- staff id does not exist
    ELSEIF inSportcenterId NOT IN (SELECT sportcenterId FROM sportcenters) THEN
        SET resultCode = 462; -- 
    ELSE
        SET resultCode = 203;
        UPDATE staff 
        SET sportcenterId = inSportCenterId
        WHERE staffId = inStaffId;
    END IF;
END//

DELIMITER ;
