/*
    Test if a relation containing the expected data is returned,
    when all the parameters are correct
*/
CALL createCity('A', @statusCode);
CALL createCity('A', @statusCode);
CALL createCity('A', @statusCode);
CALL createCity('A', @statusCode);
CALL getcities(@statusCode)
-- expected no error code
