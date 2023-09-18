DROP DATABASE IF EXISTS storage;

CREATE DATABASE storage;
USE storage;

DROP USER IF EXISTS 'tsw'@'localhost';
CREATE USER 'tsw'@'localhost' IDENTIFIED BY 'adminadmin';
GRANT ALL ON storage.* TO 'tsw'@'localhost';

DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS user;

CREATE TABLE product (
	code int primary key AUTO_INCREMENT,
	name char(50) not null,
	description char(200),
	price int default 0
);

INSERT INTO product values (1, "Looper","Box archetipo completa: Looper - Orao The Game",20);
INSERT INTO product values (2, "Legione","Box archetipo completa: Legione - Orao The Game",20);
INSERT INTO product values (3, "Goldwarr","Box archetipo completa: Goldwarr - Orao The Game",20);
INSERT INTO product values (4, "Anticultura","Box archetipo completa: Anticultura - Orao The Game",20);
INSERT INTO product values (5, "Astrattivi","Box archetipo completa: Astrattivi - Orao The Game",20);
INSERT INTO product values (6, "Power-up","Box di carte Power-up - Orao The Game",25);
INSERT INTO product values (7, "Monopoly","Un classico gioco da tavolo in cui i giocatori competono per acquistare, scambiare e sviluppare proprietà con l'obiettivo di bancarottare i loro avversari",35);
INSERT INTO product values (8, "Risiko","Un gioco da tavolo strategico in cui i giocatori guidano armate e si impegnano in battaglie per conquistare territori e continenti, con l'obiettivo di raggiungere la dominazione mondiale",30);

CREATE TABLE user (
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(100) NOT NULL,
  `tipo` varchar(5) NOT NULL,
  `portafoglio` double DEFAULT '0',
  PRIMARY KEY(`username`)
);

INSERT INTO user values ("Michele", "1234", 'admin', 100);
INSERT INTO user values ("Marco", "1234", 'user',0);

CREATE TABLE `carrello` (
  `name` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `acquistato` tinyint(1) NOT NULL,
  `data_acquisto` date,
  `price` double NOT NULL
);

INSERT INTO `carrello` VALUES ("Power-up","Michele", true, '2015-02-02', 25), ("Risiko","Michele", true, '2015-02-02', 30),("Power-up","Marco", false, '2015-02-02', 25), ("Legione","Marco", true, '2015-02-02', 20);