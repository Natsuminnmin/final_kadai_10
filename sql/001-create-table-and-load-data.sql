DROP TABLE IF EXISTS pets;

CREATE TABLE pets (
  id int unsigned AUTO_INCREMENT,
  animal_species VARCHAR(50) NOT NULL,
  name VARCHAR(50) NOT NULL,
  birthday DATE NOT NULL,
  weight DECIMAL(5, 2) NOT NULL,
  PRIMARY KEY(id)
);

INSERT INTO pets (animal_species, name, birthday, weight) VALUES ("cat", "ミケ", "2020-01-01", "5.23");
INSERT INTO pets (animal_species, name, birthday, weight) VALUES ("dog", "ポチ", "2019-09-09", "6.94");
