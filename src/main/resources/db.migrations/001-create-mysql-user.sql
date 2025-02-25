-- Drop user first if they exist
DROP USER if exists 'mysql'@'%' ;

-- Now create user with prop privileges
CREATE USER 'mysql'@'%' IDENTIFIED BY 'mysql';

GRANT ALL PRIVILEGES ON * . * TO 'mysql'@'%';