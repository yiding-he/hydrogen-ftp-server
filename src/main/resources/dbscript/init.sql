-- Database initialization script.

CREATE TABLE IF NOT EXISTS dict (
  dict_key   VARCHAR(50) PRIMARY KEY,
  dict_value VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS ftp_user (
  id           BIGINT PRIMARY KEY,
  username     VARCHAR(30) UNIQUE,
  display_name VARCHAR(30),
  password     VARCHAR(30),
  disabled     INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS ftp_group (
  id         BIGINT PRIMARY KEY,
  group_name VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS ftp_user_group (
  user_id  BIGINT,
  group_id BIGINT,
  PRIMARY KEY (user_id, group_id)
);

