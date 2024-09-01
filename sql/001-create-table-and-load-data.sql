DROP TABLE IF EXISTS pets;

CREATE TABLE pets (
  id int unsigned AUTO_INCREMENT,
  animal_species VARCHAR(50) NOT NULL,
  name VARCHAR(50) NOT NULL,
  birthday DATE NOT NULL,
  weight DECIMAL(5, 2) NOT NULL,
  PRIMARY KEY(id)
);

INSERT INTO pets (animal_species, name, birthday, weight) VALUES ("dog", "ポチ", "2020-01-01", "10.23");
INSERT INTO pets (animal_species, name, birthday, weight) VALUES ("cat", "ミケ", "2019-09-09", "6.94");
INSERT INTO pets (animal_species, name, birthday, weight) VALUES ("cat", "リン", "2017-09-10", "8.94");
INSERT INTO pets (animal_species, name, birthday, weight) VALUES ("dog", "いちご", "2024-04-24", "4.94");
INSERT INTO pets (animal_species, name, birthday, weight) VALUES ("cat", "シロ", "2011-10-09", "3.94");