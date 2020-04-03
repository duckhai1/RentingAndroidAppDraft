/* test report interface */ 
/* version 1.0 */
    /* ver 1.1 sửa tên theo database */
/* report(sportCenterId / playerId (reporterId), sportCenterId / playerId (reporteeId), msg) */

CREATE PROCEDURE testReport
    --screnario
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    INSERT INTO sportcenters(sportcenter_id, sportcenter_name, city_id) VALUES (100, 'Dinh Hoa Sport Center',001);
    --test
    DECLARE @resultcode; 
    CALL report(111,100,'Client has a bug',@resultcode); 
    IF (@resultcode = 203) THEN
        --Success updated
        SELECT "Success" AS "result of testReport"
    ELSE 
        SELECT "Fail" AS "result of testReport"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testReportInvalidReporterId
    --screnario
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    INSERT INTO sportcenters(sportcenter_id, sportcenter_name, city_id) VALUES (100, 'Dinh Hoa Sport Center',001);
    --test
    DECLARE @resultcode; 
    CALL report(113,100,'Client has a bug',@resultcode); 
    IF (@resultcode = ???) THEN
        -- ??? Invalid reporter id
        SELECT "Fail" AS "result of testReportInvalidReporterId"
    ELSE 
        SELECT "Success" AS "result of testReportInvalidReporterId"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testReportInvalidReporteeId
    --screnario
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    INSERT INTO sportcenters(sportcenter_id, sportcenter_name, city_id) VALUES (100, 'Dinh Hoa Sport Center',001);
    --test
    DECLARE @resultcode; 
    CALL report(111,113,'Client has a bug',@resultcode); 
    IF (@resultcode = 467) THEN
        -- 467 Invalid reportee id
        SELECT "Fail" AS "result of testReportInvalidReporteeId"
    ELSE 
        SELECT "Success" AS "result of testReportInvalidReporteeId"
    END IF;
END; 

