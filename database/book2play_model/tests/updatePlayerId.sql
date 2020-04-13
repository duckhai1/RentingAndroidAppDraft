/*
    Test if the player id is updated and no error code is returned,
    when all the parameters are correct

    (1st case)
*/
CALL createPlayer('X', @statusCode);
CALL createPlayer('Y', @statusCode);
CALL updatePlayerId('Z', 'X', @statusCode);
-- expected: no error code


/*
    Test if the player id is updated and no error code is returned,
    when all the parameters are correct

    (2nd case)
*/
CALL createPlayer('X', @statusCode);
CALL createPlayer('Y', @statusCode);
CALL updatePlayerId('Z', 'Y', @statusCode);
-- expected: no error code


/*
    Test if an error code is returned, when the new player id already exists in the data prior to the change

    (1st case)
*/
CALL createPlayer('X', @statusCode);
CALL createPlayer('Y', @statusCode);
CALL updatePlayerId('Y', 'X', @statusCode);
-- expected: error code 405


/*
    Test if an error code is returned, when the new player id already exists in the data prior to the change

    (2nd case)
*/
CALL createPlayer('X', @statusCode);
CALL createPlayer('Y', @statusCode);
CALL updatePlayerId('X', 'Y', @statusCode);
-- expected: error code 405


/*
    Test if an error code is returned, when the new player id is in invalid format
*/
CALL createPlayer('X', @statusCode);
CALL createPlayer('Y', @statusCode);
CALL updatePlayerId('@', 'X', @statusCode);
-- expected: error code 464


/*
    Test if an error code is returned, when the old player id does not exist in the database 
*/
CALL createPlayer('X', @statusCode);
CALL createPlayer('Y', @statusCode);
CALL updatePlayerId('X', 'Z', @statusCode);
-- expected: error code 464