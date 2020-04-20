/*
   Get courts from the sport center identified by the centerId
*/
DELIMITER //

DROP PROCEDURE IF EXISTS getCourtsInCenter //
CREATE PROCEDURE getCourtsInCenter (
	IN inSportCenterId VARCHAR(100),
    OUT statusCode INT
)
BEGIN
	IF inSportCenterId  NOT IN (SELECT sportCenterId FROM sportCenters) THEN
		SET statusCode = 461; -- invalid sportCenter id 
	
	ELSE
		SET statusCode = 200;
		
        SELECT courtId, sportCenterId
		FROM courts
		NATURAL JOIN sportCenters
		WHERE sportCenterId = InSportCenterId;
	END IF;
END//

DELIMITER ;