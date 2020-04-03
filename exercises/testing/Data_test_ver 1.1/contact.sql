/* test contact interface */ 
/* version 1.1 */
    /* 1.1 sửa tên theo database */
/* contact(sportcenter_id/player_id (**senderId**), player_id/sportcenter_id (**receiverId**), msg */

CREATE PROCEDURE testContact 
    --screnario
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    INSERT INTO sportcenters(sportcenter_id, sportcenter_name, city_id) VALUES (100, 'Dinh Hoa Sport Center',001);
    --test
    DECLARE @resultcode; 
    CALL contact(111,100,'Hello world',@resultcode); 
    IF (@resultcode = 203) THEN
        --Success updated
        SELECT "Success" AS "result of testContact"
    ELSE 
        SELECT "Fail" AS "result of testContact"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testContactInvalidReceiverId
    --screnario
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    INSERT INTO sportcenters(sportcenter_id, sportcenter_name, city_id) VALUES (100, 'Dinh Hoa Sport Center',001);
    --test
    DECLARE @resultcode; 
    CALL contact(111,113,'Hello world',@resultcode); 
    IF (@resultcode = 468) THEN
        -- 468 Invalid receiver id
        SELECT "Fail" AS "result of testContactInvalidReceiverId"
    ELSE 
        SELECT "Success" AS "result of testContactInvalidReceiverId"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testContactInvalidSenderId
    --screnario
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    INSERT INTO sportcenters(sportcenter_id, sportcenter_name, city_id) VALUES (100, 'Dinh Hoa Sport Center',001);
    --test
    DECLARE @resultcode; 
    CALL contact(114,100,'Hello world',@resultcode); 
    IF (@resultcode = ???) THEN
        -- ??? Invalid sender id
        SELECT "Fail" AS "result of testContactInvalidSenderId"
    ELSE 
        SELECT "Success" AS "result of testContactInvalidSenderId"
    END IF;
END; 