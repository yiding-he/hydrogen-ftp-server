CREATE TABLE IF NOT EXISTS user (
  id int,
  username VARCHAR (30),
  password VARCHAR(30),
  disabled int DEFAULT 0
);