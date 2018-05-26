-- Database initialization script.
-- If any error occurs when executing this script,
-- please update and reinstall dependency 'hydrogen-dao'.

CREATE TABLE IF NOT EXISTS dict (
  dict_key   VARCHAR(50) PRIMARY KEY,
  dict_value VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS ftp_user (
  id           INT PRIMARY KEY,
  username     VARCHAR(30) UNIQUE,
  display_name VARCHAR(30),
  password     VARCHAR(30),
  disabled     INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS ftp_group (
  id         INT PRIMARY KEY,
  group_name VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS ftp_user_group (
  user_id  INT,
  group_id INT,
  PRIMARY KEY (user_id, group_id)
);

