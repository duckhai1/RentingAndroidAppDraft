/*
    Test if the staff id is updated correctly when all the parameters are correct

    (1st case)
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenter('C', 'A');
CALL createStaff('C', 'A', 'B');
CALL createStaff('D', 'A', 'B');
CALL createStaff('E', 'A', 'B');
CALL createStaff('F', 'A', 'C');
CALL createStaff('G', 'A', 'C');
CALL updateStaffId('F', 'C', 'A', 'B');
-- expected: no error code


/*
    Test if the staff id is updated correctly when all the parameters are correct

    (2nd case)
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenter('C', 'A');
CALL createStaff('C', 'A', 'B');
CALL createStaff('D', 'A', 'B');
CALL createStaff('E', 'A', 'B');
CALL createStaff('F', 'A', 'C');
CALL createStaff('G', 'A', 'C');
CALL updateStaffId('G', 'E', 'A', 'B');
-- expected: no error code


/*
    Test if the staff id is not updated when the new id already exists in the database prior to change

    (1st case)
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenter('C', 'A');
CALL createStaff('C', 'A', 'B');
CALL createStaff('D', 'A', 'B');
CALL createStaff('E', 'A', 'B');
CALL createStaff('F', 'A', 'C');
CALL createStaff('G', 'A', 'C');
CALL updateStaffId('G', 'F', 'A', 'C');
-- expected: error code 406


/*
    Test if the staff id is not updated when the new id already exists in the database prior to change

    (2nd case)
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenter('C', 'A');
CALL createStaff('C', 'A', 'B');
CALL createStaff('D', 'A', 'B');
CALL createStaff('E', 'A', 'B');
CALL createStaff('F', 'A', 'C');
CALL createStaff('G', 'A', 'C');
CALL updateStaffId('F', 'F', 'A', 'C');
-- expected: error code 406


/*
    Test if the court id is updated correctly when all the parameters are correct

    (1st case)
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenter('C', 'A');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createCityCenterCourt('D', 'A', 'B');
CALL createCityCenterCourt('E', 'A', 'B');
CALL createCityCenterCourt('F', 'A', 'C');
CALL createCityCenterCourt('G', 'A', 'C');
CALL updateCourtId('F', 'C', 'A', 'B');
-- expected: no error code


/*
    Test if the court id is updated correctly when all the parameters are correct

    (2nd case)
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenter('C', 'A');
CALL createCityCenterCourt('C', 'A', 'B');
CALL createCityCenterCourt('D', 'A', 'B');
CALL createCityCenterCourt('E', 'A', 'B');
CALL createCityCenterCourt('F', 'A', 'C');
CALL createCityCenterCourt('G', 'A', 'C');
CALL updateCourtId('G', 'E', 'A', 'B');
-- expected: no error code


/*
    Test if the staff id is not updated when the new staff id is in invalid format
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenter('C', 'A');
CALL createStaff('C', 'A', 'B');
CALL createStaff('D', 'A', 'B');
CALL createStaff('E', 'A', 'B');
CALL createStaff('F', 'A', 'C');
CALL createStaff('G', 'A', 'C');
CALL updateStaffId('@', 'G', 'A', 'C');
-- expected: error code 463



/*
    Test if the staff id is not updated when the old staff id does not exist in the database
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenter('C', 'A');
CALL createStaff('C', 'A', 'B');
CALL createStaff('D', 'A', 'B');
CALL createStaff('E', 'A', 'B');
CALL createStaff('F', 'A', 'C');
CALL createStaff('G', 'A', 'C');
CALL updateStaffId('I', 'H', 'A', 'C');
-- expected: error code 463


/*
    Test if the staff id is not updated when the city id does not exist in the database
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenter('C', 'A');
CALL createStaff('C', 'A', 'B');
CALL createStaff('D', 'A', 'B');
CALL createStaff('E', 'A', 'B');
CALL createStaff('F', 'A', 'C');
CALL createStaff('G', 'A', 'C');
CALL updateStaffId('H', 'G', 'B', 'C');
-- expected: error code 460


/*
    Test if the staff id is not updated when the sportcenter id does not exist in the database
*/
CALL createCity('A');
CALL createCityCenter('B', 'A');
CALL createCityCenter('C', 'A');
CALL createStaff('C', 'A', 'B');
CALL createStaff('D', 'A', 'B');
CALL createStaff('E', 'A', 'B');
CALL createStaff('F', 'A', 'C');
CALL createStaff('G', 'A', 'C');
CALL updateStaffId('H', 'G', 'A', 'K');
-- expected: error code 461