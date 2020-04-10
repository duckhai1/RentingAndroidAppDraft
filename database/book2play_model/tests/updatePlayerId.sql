/*
    Test if the player id is updated and no error code is returned,
    when all the parameters are correct

    (1st case)
*/
CALL createPlayer('X');
CALL createPlayer('Y');
CALL updatePlayerId('Z', 'X');
-- expected: no error code


/*
    Test if the player id is updated and no error code is returned,
    when all the parameters are correct

    (2nd case)
*/
CALL createPlayer('X');
CALL createPlayer('Y');
CALL updatePlayerId('Z', 'Y');
-- expected: no error code


/*
    Test if an error code is returned, when the new player id already exists in the data prior to the change

    (1st case)
*/
CALL createPlayer('X');
CALL createPlayer('Y');
CALL updatePlayerId('Y', 'X');
-- expected: error code 405


/*
    Test if an error code is returned, when the new player id already exists in the data prior to the change

    (2nd case)
*/
CALL createPlayer('X');
CALL createPlayer('Y');
CALL updatePlayerId('X', 'Y');
-- expected: error code 405


/*
    Test if an error code is returned, when the new player id is in invalid format
*/
CALL createPlayer('X');
CALL createPlayer('Y');
CALL updatePlayerId('@', 'X');
-- expected: error code 464


/*
    Test if an error code is returned, when the old player id does not exist in the database 
*/
CALL createPlayer('X');
CALL createPlayer('Y');
CALL updatePlayerId('X', 'Z');
-- expected: error code 464