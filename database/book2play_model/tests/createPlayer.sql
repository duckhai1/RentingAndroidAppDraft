/*
    Test if the request is accepted when the playerId is valid
*/
CALL createPlayer('P');
-- expected: no error code


/*
    Test if the request is accepted when the playerId is valid
*/
CALL createPlayer('P');
CALL createPlayer('P');
-- expected: error code 405


/* 
    Test if the request is rejected when the playerId is invalid
*/
CALL createPlayer('@');
-- expected: error code: 464