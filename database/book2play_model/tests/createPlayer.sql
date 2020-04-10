/*
    Test if the request is accepted when the playerId is valid
*/
CALL createPlayer('P', @statusCode);
-- expected: no error code


/*
    Test if the request is accepted when the playerId is valid
*/
CALL createPlayer('P', @statusCode);
CALL createPlayer('P', @statusCode);
-- expected: error code 405


/* 
    Test if the request is rejected when the playerId is invalid
*/
CALL createPlayer('@', @statusCode);
-- expected: error code: 464