USE `online-courses`;

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` char(68) NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `users`
--
-- NOTE: The passwords are encrypted using BCrypt
--
-- A generation tool is avail at: https://www.luv2code.com/generate-bcrypt-password
--
-- Default passwords here are: pswd12345
--

INSERT INTO `users`
VALUES
('buckybarnes@gmail.com','{bcrypt}$2a$10$f38HiLFHQfn4NOmik3kdyOcXMf7KumHirkWImYBZQrST8CQh/5NLm',1),
('peggycarter@gmail.com','{bcrypt}$2a$10$f38HiLFHQfn4NOmik3kdyOcXMf7KumHirkWImYBZQrST8CQh/5NLm',1),
('steverogers@gmail.com','{bcrypt}$2a$10$f38HiLFHQfn4NOmik3kdyOcXMf7KumHirkWImYBZQrST8CQh/5NLm',1);


--
-- Table structure for table `authorities`
--

CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities4_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities4_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `authorities`
--

INSERT INTO `authorities`
VALUES
('buckybarnes@gmail.com','ROLE_EMPLOYEE'),
('peggycarter@gmail.com','ROLE_EMPLOYEE'),
('peggycarter@gmail.com','ROLE_MANAGER'),
('steverogers@gmail.com','ROLE_EMPLOYEE'),
('steverogers@gmail.com','ROLE_MANAGER'),
('steverogers@gmail.com','ROLE_ADMIN');