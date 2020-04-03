/* test updatePlayerInfo interface */ 
/* version 1.1 */
        /* ver1.1 sửa tên + định dạng + procedure theo database */
/* updateName(player_id, player_name)/updatePlayerPhoneNumber(player_id, p_phonenumber)/updatePlayerEmail(player_id, p_email)/updatePlayerPassword(player_id, p_password)/updatePlayerAddress(player_id, p_address)*/

CREATE PROCEDURE testUpdatePlayerName  
BEGIN 
    --screnario
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    --test
    DECLARE @resultcode; 
    CALL updatePlayerName(111,'Minh ml',@resultcode); 
    IF (@resultcode = 203) THEN
        --Success updated
        SELECT "Success" AS "result of testUpdatePlayerName"
    ELSE 
        SELECT "Fail" AS "result of testUpdatePlayerName"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testUpdatePlayerNameInvalidPlayerId
BEGIN 
    --screnario
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    --test
    DECLARE @resultcode; 
    CALL updatePlayerName(101,'Minh ml',@resultcode); 
    IF (@resultcode = 478) THEN
        -- 478 Invalid Player Id
        SELECT "Fail" AS "result of testUpdatePlayerNameInvalidPlayerId"
    ELSE 
        SELECT "Success" AS "result of testUpdatePlayerNameInvalidPlayerId"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testUpdatePlayerNameInvalid
BEGIN 
    --screnario
    INSERT INTO players(player_id, player_name) VALUES (111,"PN")
    --test
    DECLARE @resultcode; 
    CALL updatePlayerName(111,'Minh@ml',@resultcode); 
    IF (@resultcode = 475) THEN
        -- 475 Invalid Player Name
        SELECT "Fail" AS "result of testUpdatePlayerNameInvalid"
    ELSE 
        SELECT "Success" AS "result of testUpdatePlayerNameInvalid"
    END IF;
END; 

---------------------------------------------------------
/*
CREATE PROCEDURE testUpdatePlayerPhoneNumber
    --screnario
    INSERT INTO player(player_id, p_name, p_email, p_username, p_password, p_phonenumber, p_address) VALUES (111, 'Phat', 'phat@mail.com', 'PN', '123abc', '0123456789', 'hcmcity');
    --test
    DECLARE @resultcode; 
    CALL updatePlayerPhoneNumber(111,'987654321',@resultcode); 
    IF (@resultcode = 203) THEN
        --Success updated
        SELECT "Success" AS "result of testUpdatePlayerPhoneNumber"
    ELSE 
        SELECT "Fail" AS "result of testUpdatePlayerPhoneNumber"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testUpdatePlayerPhoneNumberInvalidPlayerId
BEGIN 
    --screnario
    INSERT INTO player(player_id, p_name, p_email, p_username, p_password, p_phonenumber, p_address) VALUES (111, 'Phat', 'phat@mail.com', 'PN', '123abc', '0123456789', 'hcmcity');
    --test
    DECLARE @resultcode; 
    CALL updatePlayerPhoneNumber(101,'0987654321',@resultcode); 
    IF (@resultcode = 478) THEN
        -- 478 Invalid Player Id
        SELECT "Fail" AS "result of testUpdatePlayerPhoneNumberInvalidPlayerId"
    ELSE 
        SELECT "Success" AS "result of testUpdatePlayerPhoneNumberInvalidPlayerId"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testUpdatePlayerPhoneNumberInvalid
BEGIN 
    --screnario
    INSERT INTO player(player_id, p_name, p_email, p_username, p_password, p_phonenumber, p_address) VALUES (111, 'Phat', 'phat@mail.com', 'PN', '123abc', '0123456789', 'hcmcity');
    --test
    DECLARE @resultcode; 
    CALL updatePlayerPhoneNumber(111,'098765432a',@resultcode); 
    IF (@resultcode = 476) THEN
        -- 476 Invalid Phone number
        SELECT "Fail" AS "result of testUpdatePlayerPhoneNumberInvalid"
    ELSE 
        SELECT "Success" AS "result of testUpdatePlayerPhoneNumberInvalid"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testUpdatePlayerEmail
    --screnario
    INSERT INTO player(player_id, p_name, p_email, p_username, p_password, p_phonenumber, p_address) VALUES (111, 'Phat', 'phat@mail.com', 'PN', '123abc', '0123456789', 'hcmcity');
    --test
    DECLARE @resultcode; 
    CALL updatePlayerEmail(111,'fat@mail.com',@resultcode); 
    IF (@resultcode = 203) THEN
        --Success updated
        SELECT "Success" AS "result of testUpdatePlayerEmail"
    ELSE 
        SELECT "Fail" AS "result of testUpdatePlayerEmail"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testUpdatePlayerEmailInvalidPlayerId
BEGIN 
    --screnario
    INSERT INTO player(player_id, p_name, p_email, p_username, p_password, p_phonenumber, p_address) VALUES (111, 'Phat', 'phat@mail.com', 'PN', '123abc', '0123456789', 'hcmcity');
    --test
    DECLARE @resultcode; 
    CALL updatePlayerEmail(101,'phat@mail.com',@resultcode); 
    IF (@resultcode = 478) THEN
        -- 478 Invalid Player Id
        SELECT "Fail" AS "result of testUpdatePlayerEmailInvalidPlayerId"
    ELSE 
        SELECT "Success" AS "result of testUpdatePlayerEmailInvalidPlayerId"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testUpdatePlayerEmailInvalid
BEGIN 
    --screnario
    INSERT INTO player(player_id, p_name, p_email, p_username, p_password, p_phonenumber, p_address) VALUES (111, 'Phat', 'phat@mail.com', 'PN', '123abc', '0123456789', 'hcmcity');
    --test
    DECLARE @resultcode; 
    CALL updatePlayerEmail(111,'@phat.mail',@resultcode); 
    IF (@resultcode = 479) THEN
        -- 479 Invalid email
        SELECT "Fail" AS "result of testUpdatePlayerEmailInvalid"
    ELSE 
        SELECT "Success" AS "result of testUpdatePlayerEmailInvalid"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testUpdatePlayerPassword
    --screnario
    INSERT INTO player(player_id, p_name, p_email, p_username, p_password, p_phonenumber, p_address) VALUES (111, 'Phat', 'phat@mail.com', 'PN', '123abc', '0123456789', 'hcmcity');
    --test
    DECLARE @resultcode; 
    CALL updatePlayerEmail(111,'123456789abc@@',@resultcode); 
    IF (@resultcode = 203) THEN
        --Success updated
        SELECT "Success" AS "result of testUpdatePlayerPassword"
    ELSE 
        SELECT "Fail" AS "result of testUpdatePlayerPassword"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testUpdatePlayerPasswordInvalidPlayerId
BEGIN 
    --screnario
    INSERT INTO player(player_id, p_name, p_email, p_username, p_password, p_phonenumber, p_address) VALUES (111, 'Phat', 'phat@mail.com', 'PN', '123abc', '0123456789', 'hcmcity');
    --test
    DECLARE @resultcode; 
    CALL updatePlayerEmail(101,'phat@@12345',@resultcode); 
    IF (@resultcode = 478) THEN
        -- 478 Invalid Player Id
        SELECT "Fail" AS "result of testUpdatePlayerPasswordInvalidPlayerId"
    ELSE 
        SELECT "Success" AS "result of testUpdatePlayerPasswordInvalidPlayerId"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testUpdatePlayerPasswordInvalid
BEGIN 
    --screnario
    INSERT INTO player(player_id, p_name, p_email, p_username, p_password, p_phonenumber, p_address) VALUES (111, 'Phat', 'phat@mail.com', 'PN', '123abc', '0123456789', 'hcmcity');
    --test
    DECLARE @resultcode; 
    CALL updatePlayerPhoneNumber(111,'1',@resultcode); 
    IF (@resultcode = 480) THEN
        -- 480 Invalid password
        SELECT "Fail" AS "result of testUpdatePlayerPasswordInvalid"
    ELSE 
        SELECT "Success" AS "result of testUpdatePlayerPasswordInvalid"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testUpdatePlayerAddress
    --screnario
    INSERT INTO player(player_id, p_name, p_email, p_username, p_password, p_phonenumber, p_address) VALUES (111, 'Phat', 'phat@mail.com', 'PN', '123abc', '0123456789', 'hcmcity');
    --test
    DECLARE @resultcode; 
    CALL updatePlayerAddress(111,'mythoCity',@resultcode); 
    IF (@resultcode = 203) THEN
        --Success updated
        SELECT "Success" AS "result of testUpdatePlayerAddress"
    ELSE 
        SELECT "Fail" AS "result of testUpdatePlayerAddress"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testUpdatePlayerAddressInvalidPlayerId
BEGIN 
    --screnario
    INSERT INTO player(player_id, p_name, p_email, p_username, p_password, p_phonenumber, p_address) VALUES (111, 'Phat', 'phat@mail.com', 'PN', '123abc', '0123456789', 'hcmcity');
    --test
    DECLARE @resultcode; 
    CALL updatePlayerEmail(101,'mythoCity',@resultcode); 
    IF (@resultcode = 478) THEN
        -- 478 Invalid Player Id
        SELECT "Fail" AS "result of testUpdatePlayerAddressInvalidPlayerId"
    ELSE 
        SELECT "Success" AS "result of testUpdatePlayerAddressInvalidPlayerId"
    END IF;
END; 

---------------------------------------------------------

CREATE PROCEDURE testUpdatePlayerAddressInvalid
BEGIN 
    --screnario
    INSERT INTO player(player_id, p_name, p_email, p_username, p_password, p_phonenumber, p_address) VALUES (111, 'Phat', 'phat@mail.com', 'PN', '123abc', '0123456789', 'hcmcity');
    --test
    DECLARE @resultcode; 
    CALL updatePlayerPhoneNumber(111,'abc',@resultcode); 
    IF (@resultcode = 473) THEN
        -- 473 Invalid address
        SELECT "Fail" AS "result of testUpdatePlayerAddressInvalid"
    ELSE 
        SELECT "Success" AS "result of testUpdatePlayerAddressInvalid"
    END IF;
END; 
*/