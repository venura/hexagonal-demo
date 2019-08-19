DROP TABLE IF EXISTS verification;

CREATE TABLE verification
(
	id bigint  auto_increment,
	email varchar(255),
	password varchar(255),
	code varchar(255)
);

DROP TABLE IF EXISTS app_user;

CREATE TABLE app_user
(
	id int  NOT NULL PRIMARY KEY,
	email varchar(255),
	password varchar(255),
	state char(15)
);