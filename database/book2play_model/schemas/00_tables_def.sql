CREATE TABLE IF NOT EXISTS cities (
    cityPk INT NOT NULL AUTO_INCREMENT,
    cityId VARCHAR(100) NOT NULL,
    cityLatitude DOUBLE DEFAULT 0.0,
    cityLongitude DOUBLE DEFAULT 0.0,

    PRIMARY KEY (cityPk),
    CONSTRAINT cities
        UNIQUE (cityId)
);

CREATE TABLE IF NOT EXISTS sportCenters (
    sportCenterPk INT NOT NULL AUTO_INCREMENT,
    sportCenterId VARCHAR(100) NOT NULL,
	cityPk INT NOT NULL,
    sportCenterLatitude DOUBLE DEFAULT 0.0,
    sportCenterLongitude DOUBLE DEFAULT 0.0,
    sportCenterAddress VARCHAR(500) DEFAULT 'Unknown',

    PRIMARY KEY (sportCenterPk),
	FOREIGN KEY (cityPk)
		REFERENCES cities (cityPk)
		ON DELETE CASCADE,
    CONSTRAINT sportCentersInCity
        UNIQUE (cityPk, sportCenterId)
); 

CREATE TABLE IF NOT EXISTS courts (
    courtPk INT NOT NULL AUTO_INCREMENT,
    courtId VARCHAR(100) NOT NULL,
    sportCenterPk INT NOT NULL,

    PRIMARY KEY (courtPk),
	FOREIGN KEY (sportCenterPk)
		REFERENCES sportCenters (sportCenterPk)
		ON DELETE CASCADE,
    CONSTRAINT courtsInSportCenter
        UNIQUE (sportCenterPk, courtId)
);

CREATE TABLE IF NOT EXISTS staffs (
    staffPk INT NOT NULL AUTO_INCREMENT,
    staffId VARCHAR(100) NOT NULL,
    sportCenterPk INT NOT NULL,

    PRIMARY KEY (staffPk),
	FOREIGN KEY (sportCenterPk)
		REFERENCES sportCenters (sportCenterPk)
		ON DELETE CASCADE,
    CONSTRAINT staffsInSportCenter
        UNIQUE (sportCenterPk, staffId)
); 

CREATE TABLE IF NOT EXISTS players (
    playerPk INT NOT NULL AUTO_INCREMENT,
    playerId VARCHAR(100) NOT NULL,

    PRIMARY KEY (playerPk),
    CONSTRAINT player
        UNIQUE (playerId)
); 

CREATE TABLE IF NOT EXISTS bookings (
    bookingPk INT NOT NULL AUTO_INCREMENT,
    bookingId VARCHAR(100) NOT NULL,
    bookingDate DATE NOT NULL,
    bookingStartTime TIME NOT NULL,
    bookingEndTime TIME NOT NULL,
    createdAt TIMESTAMP NOT NULL,
    isPaid BOOLEAN DEFAULT FALSE, -- FALSE-unpaid; TRUE-paid
    playerPk INT NOT NULL,
    courtPk INT NULL,

    PRIMARY KEY(bookingPk),
	FOREIGN KEY (playerPk)
		REFERENCES players(playerPk)
		ON DELETE CASCADE,
	FOREIGN KEY(courtPk)
		REFERENCES courts (courtPk)
		ON DELETE SET NULL,

    CONSTRAINT bookings
        UNIQUE (bookingId)
); 