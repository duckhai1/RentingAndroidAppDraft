DROP SCHEMA IF EXISTS book2play_test;
CREATE SCHEMA book2play_test;
USE book2play_test;

CREATE TABLE IF NOT EXISTS cities (
  city_id INT NOT NULL AUTO_INCREMENT,
  city_name VARCHAR(64) NOT NULL,

  PRIMARY KEY (city_id)
);

CREATE TABLE IF NOT EXISTS sportcenters (
    sportcenter_id INT NOT NULL AUTO_INCREMENT,
    sportcenter_name VARCHAR(64) NOT NULL,
	city_id INT NOT NULL,

    PRIMARY KEY (sportcenter_id),
	FOREIGN KEY (city_id)
		REFERENCES cities (city_id)
		ON DELETE CASCADE
); 

CREATE TABLE IF NOT EXISTS courts (
    court_id INT NOT NULL AUTO_INCREMENT,
    court_name VARCHAR(64) NOT NULL UNIQUE,
    sportcenter_id INT NOT NULL,

    PRIMARY KEY (court_id),
	FOREIGN KEY (sportcenter_id)
		REFERENCES sportcenters (sportcenter_id)
		ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS staffs (
    staff_id INT NOT NULL AUTO_INCREMENT,
    staff_name VARCHAR(64) NOT NULL,
    sportcenter_id INT NOT NULL,

    PRIMARY KEY (staff_id),
	FOREIGN KEY (sportcenter_id)
		REFERENCES sportcenters (sportcenter_id)
		ON DELETE CASCADE
); 

CREATE TABLE IF NOT EXISTS players (
    player_id INT NOT NULL AUTO_INCREMENT,
    player_name VARCHAR(64) NOT NULL,

    PRIMARY KEY (player_id)
); 

CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT NOT NULL AUTO_INCREMENT,
    booking_date DATE NOT NULL,
    booking_start_time TIME NOT NULL,
    booking_end_time TIME NOT NULL,
    created_at TIMESTAMP NOT NULL,
    is_paid BOOLEAN DEFAULT FALSE, -- FALSE-unpaid; TRUE-paid
    is_cancelled BOOLEAN DEFAULT FALSE, -- FALSE-not cancelled; TRUE-cancelled
    player_id INT NOT NULL,
    court_id INT NULL,

    PRIMARY KEY(booking_id),
	FOREIGN KEY (player_id)
		REFERENCES players(player_id)
		ON DELETE CASCADE,
	FOREIGN KEY(court_id)
		REFERENCES courts (court_id)
		ON DELETE SET NULL
); 
