DROP DATABASE IF EXISTS mediscreendb_test;

CREATE DATABASE mediscreendb_test CHARACTER SET utf8mb4;

USE mediscreendb_test;

CREATE TABLE patients (
  id BIGINT AUTO_INCREMENT NOT NULL,
  last_name VARCHAR(125) NOT NULL,
  first_name VARCHAR(125) NOT NULL,
  date_of_birth VARCHAR(10) NOT NULL,
  sex CHAR (1) NOT NULL,
  address VARCHAR(150) DEFAULT NULL,
  phone_number VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT IGNORE INTO patients (id, last_name, first_name, date_of_birth, sex, address, phone_number)
VALUES
(1, "Ferguson", "Lucas", "1968-06-22", 'M', "2 Warren Street", "387-866-1399"),
(2, "Rees", "Pippa", "1952-09-27", 'F', "745 West Valley Farms Drive", "628-423-0993"),
(3, "Arnold", "Edward", "1952-11-11", 'M', "599 East Garden Ave", "123-727-2779"),
(4, "Sharp", "Anthony", "1946-11-26", 'M', "894 Hall Street", "451-761-8383"),
(5, "Ince", "Wendy", "1958-06-29", 'F', "4 Southampton Road", "802-911-9975"),
(6, "Ross", "Tracey", "1949-12-07", 'F', "40 Sulphur Springs Dr", "131-396-5049"),
(7, "Wilson", "Claire", "1966-12-31", 'F', "12 Cobblestone St", "300-452-1091"),
(8, "Buckland", "Max", "1945-06-24", 'M', "193 Vale St", "833-534-0864"),
(9, "Clark", "Natalie", "1964-06-18", 'F', "12 Beechwood Road", "241-467-9197"),
(10, "Bailey", "Piers", "1959-06-28", 'M', "1202 Bumble Dr", "747-815-0557");