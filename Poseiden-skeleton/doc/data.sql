CREATE TABLE Trade (
  Id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  buyQuantity DOUBLE,
  sellQuantity DOUBLE,
  buyPrice DOUBLE ,
  sellPrice DOUBLE,
  tradeDate TIMESTAMP,
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creationName VARCHAR(125),
  creationDate TIMESTAMP ,
  revisionName VARCHAR(125),
  revisionDate TIMESTAMP ,
  dealName VARCHAR(125),
  dealType VARCHAR(125),
  sourceListId VARCHAR(125),
  side VARCHAR(125)
);
CREATE TABLE curvepoint (
  Id int NOT NULL AUTO_INCREMENT,
  CurveId int,
  asOfDate timestamp,
  term double,
  value double,
  creationDate timestamp,
  as_of_date datetime(6),
  creation_date datetime(6),
  curve_idint,
  PRIMARY KEY (Id)
);
CREATE TABLE bidlist (
  Id int NOT NULL AUTO_INCREMENT,
  account varchar(30) NOT NULL,
  type varchar(30) NOT NULL,
  bidQuantity double,
  askQuantity double,
  bid double,
  ask double,
  benchmark varchar(125),
  bidListDate timestamp,
  commentary varchar(125),
  security varchar(125),
  status varchar(10),
  trader varchar(125),
  book varchar(125),
  creationName varchar(125),
  creationDate timestamp,
  revisionName varchar(125),
  revisionDate timestamp,
  dealName varchar(125),
  dealType varchar(125),
  sourceListId varchar(125),
  side varchar(125),
  PRIMARY KEY (Id)
);
CREATE TABLE rating (
  Id int NOT NULL AUTO_INCREMENT,
  moodysRating varchar(125),
  sandPRating varchar(125),
  fitchRating varchar(125),
  orderNumber int,
  PRIMARY KEY (Id)
);
CREATE TABLE rulename (
  Id int NOT NULL AUTO_INCREMENT,
  name varchar(255),
  description varchar(255),
  json varchar(255),
  template varchar(255),
  sqlStr varchar(125),
  sqlPart varchar(125),
  sql_part varchar(255),
  sql_str varchar(255),
  PRIMARY KEY (Id)
);
CREATE TABLE users (
  Id int NOT NULL AUTO_INCREMENT,
  username varchar(255),
  password varchar(255),
  fullname varchar(255),
  role varchar(255),
  PRIMARY KEY (Id)
);
-- Création du user "admin" / mdp "admin"
insert into users values(1, 'admin', '$2a$12$L9PXbwwiLsK56L2xaJ2UaO3ACa7N8HG70nyrZJVG1QhZm42x4bch6', 'admin', 'ADMIN');