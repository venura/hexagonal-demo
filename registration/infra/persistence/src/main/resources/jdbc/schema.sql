DROP TABLE IF EXISTS verification;

CREATE TABLE verification
(
	id INT AUTO_INCREMENT  PRIMARY KEY,
	email varchar(255),
	password varchar(255),
	code varchar(255)
);

DROP TABLE IF EXISTS app_user;

CREATE TABLE app_user
(
	id INT AUTO_INCREMENT  PRIMARY KEY,
	email varchar(255),
	password varchar(255),
	state char(15)
);



