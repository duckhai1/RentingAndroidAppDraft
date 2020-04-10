/*
    Test if all information about the user in the database is returned,
    when all the parameters are correct
*/
CALL createPlayer('P', @statusCode);
CALL createPlayer('Q', @statusCode);
CALL getPlayerInfo('P', @statusCode);
-- expected: no error code, a relation with 1 element is returned


/*
    Test if all information about the user in the database is returned,
    when all the parameters are correct
*/
CALL createPlayer('P', @statusCode);
CALL createPlayer('Q', @statusCode);
CALL getPlayerInfo('Q', @statusCode);
-- expected: no error code, a relation with 1 element is returned


/*
    Test if the information about a user is not returned,
    when provided player id is incorrect
*/
CALL createPlayer('P', @statusCode);
CALL createPlayer('Q', @statusCode);
CALL getPlayerInfo('@', @statusCode);
-- expected: error code 464