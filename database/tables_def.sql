CREATE TABLE IF NOT EXISTS cities (
  cityId INT NOT NULL AUTO_INCREMENT,
  cityName VARCHAR(64) NOT NULL,

  PRIMARY KEY (cityId)
);

CREATE TABLE IF NOT EXISTS sportcenters (
    sportcenterId INT NOT NULL AUTO_INCREMENT,
    sportcenterName VARCHAR(64) NOT NULL,
	cityId INT NOT NULL,

    PRIMARY KEY (sportcenterId),
	FOREIGN KEY (cityId)
		REFERENCES cities (cityId)
		ON DELETE CASCADE
); 

CREATE TABLE IF NOT EXISTS courts (
    courtId INT NOT NULL AUTO_INCREMENT,
    courtName VARCHAR(64) NOT NULL UNIQUE,
    sportcenterId INT NOT NULL,

    PRIMARY KEY (courtId),
	FOREIGN KEY (sportcenterId)
		REFERENCES sportcenters (sportcenterId)
		ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS staffs (
    staffId INT NOT NULL AUTO_INCREMENT,
    staffName VARCHAR(64) NOT NULL,
    sportcenterId INT NOT NULL,

    PRIMARY KEY (staffId),
	FOREIGN KEY (sportcenterId)
		REFERENCES sportcenters (sportcenterId)
		ON DELETE CASCADE
); 

CREATE TABLE IF NOT EXISTS players (
    playerId INT NOT NULL AUTO_INCREMENT,
    playerName VARCHAR(64) NOT NULL,

    PRIMARY KEY (playerId)
); 

CREATE TABLE IF NOT EXISTS bookings (
    bookingId INT NOT NULL AUTO_INCREMENT,
    bookingDate DATE NOT NULL,
    bookingStartTime TIME NOT NULL,
    bookingEndTime TIME NOT NULL,
    createdAt TIMESTAMP NOT NULL,
    isPaid BOOLEAN DEFAULT FALSE, -- FALSE-unpaid; TRUE-paid
    isCancelled BOOLEAN DEFAULT FALSE, -- FALSE-not cancelled; TRUE-cancelled
    playerId INT NOT NULL,
    courtId INT NULL,

    PRIMARY KEY(bookingId),
	FOREIGN KEY (playerId)
		REFERENCES players(playerId)
		ON DELETE CASCADE,
	FOREIGN KEY(courtId)
		REFERENCES courts (courtId)
		ON DELETE SET NULL
); 
