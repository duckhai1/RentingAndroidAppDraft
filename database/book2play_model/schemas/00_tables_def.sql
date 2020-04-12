CREATE TABLE IF NOT EXISTS cities (
    cityPk INT NOT NULL AUTO_INCREMENT,
    cityId VARCHAR(100) NOT NULL,

    PRIMARY KEY (cityPk),
    CONSTRAINT cities
        UNIQUE (cityId)
);

CREATE TABLE IF NOT EXISTS sportcenters (
    sportcenterPk INT NOT NULL AUTO_INCREMENT,
    sportcenterId VARCHAR(100) NOT NULL,
	cityPk INT NOT NULL,

    PRIMARY KEY (sportcenterPk),
	FOREIGN KEY (cityPk)
		REFERENCES cities (cityPk)
		ON DELETE CASCADE,
    CONSTRAINT sportcentersInCity
        UNIQUE (cityPk, sportcenterId)
); 

CREATE TABLE IF NOT EXISTS courts (
    courtPk INT NOT NULL AUTO_INCREMENT,
    courtId VARCHAR(100) NOT NULL,
    sportcenterPk INT NOT NULL,

    PRIMARY KEY (courtPk),
	FOREIGN KEY (sportcenterPk)
		REFERENCES sportcenters (sportcenterPk)
		ON DELETE CASCADE,
    CONSTRAINT courtsInSportcenter
        UNIQUE (sportcenterPk, courtId)
);

CREATE TABLE IF NOT EXISTS staffs (
    staffPk INT NOT NULL AUTO_INCREMENT,
    staffId VARCHAR(100) NOT NULL,
    sportcenterPk INT NOT NULL,

    PRIMARY KEY (staffPk),
	FOREIGN KEY (sportcenterPk)
		REFERENCES sportcenters (sportcenterPk)
		ON DELETE CASCADE,
    CONSTRAINT staffsInSportcenter
        UNIQUE (sportcenterPk, staffId)
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