CREATE USER 'testuser'@'localhost' IDENTIFIED BY '123abc';
GRANT SELECT, INSERT, UPDATE, DELETE ON SAKILA.* TO 'testuser'@'localhost';
GRANT EXECUTE ON PROCEDURE sakila.rewards_report TO 'testuser'@'localhost';

SHOW GRANTS FOR 'testuser'@'localhost';