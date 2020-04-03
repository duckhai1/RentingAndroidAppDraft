/* test getPlayerInfo interface */ 
/* version 1.1 */
    /* ver1.1 sửa tên + datatype theo database */
/* getPlayerInfo(player_id) */

CREATE PROCEDURE testGetPlayerInfo 
BEGIN 
    --screnario
    INSERT INTO players(player_id, player_name) VALUES (111, 'Phat');
    --test
    DECLARE @resultcode; 
    CALL getPlayerInfo(111, @resultcode);
    IF (@resultcode = 201) THEN
        --Success
        SELECT "Success" AS "result of testGetPlayerInfo"
    ELSE 
        SELECT "Fail" AS "result of testGetPlayerInfo"
    END IF;
END;    

---------------------------------------------------------

CREATE PROCEDURE testGetPlayerInfoInvalidPlayerId
BEGIN 
    --screnario 
    INSERT INTO players(player_id, player_name) VALUES (111, 'Phat');
    --test 
    DECLARE @resultcode;
    CALL getPlayerInfo(112, @resultcode);
    IF (@resultcode = 478) THEN 
        --478 invalid player_id 
        SELECT "Fail" AS "result of testGetPlayerInfoInvalidPlayerId"
    ELSE
        SELECT "Success" AS "result of testGetPlayerInfoInvalidPlayerId"
    END IF; 
END; 
