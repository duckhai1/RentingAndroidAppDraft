/*
    Test if all information about the user in the database is returned,
    when all the parameters are correct
*/
CALL createPlayer('P');
CALL createPlayer('Q');
CALL getPlayerInfo('P');
-- expected: no error code, a relation with 1 element is returned


/*
    Test if all information about the user in the database is returned,
    when all the parameters are correct
*/
CALL createPlayer('P');
CALL createPlayer('Q');
CALL getPlayerInfo('Q');
-- expected: no error code, a relation with 1 element is returned


/*
    Test if the information about a user is not returned,
    when provided player id is incorrect
*/
CALL createPlayer('P');
CALL createPlayer('Q');
CALL getPlayerInfo('@');
-- expected: error code 464