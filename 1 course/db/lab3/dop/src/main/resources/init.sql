CREATE TYPE classification AS ENUM ('low', 'middle', 'hight', 'expert');

CREATE TABLE weather (
                         id SERIAL PRIMARY KEY,
                         city VARCHAR(100),
                         temperature DECIMAL,
                         date DATE
);

CREATE TABLE people(
                       id SERIAL PRIMARY KEY,
                       name varchar(200) NOT NULL,
                       sex text NOT NULL,
                       age INT NOT NULL
);


CREATE TABLE action_people(
                              id SERIAL PRIMARY KEY,
                              name varchar(200) NOT NULL,
                              time date NOT NULL,
                              place varchar(200) NOT NULL,
                              count_place INT NOT NULL CHECK (count_place > 0),
                              user_id BIGINT NOT NULL,


                              CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES  people (id)
);


CREATE TABLE science(
                        id SERIAL PRIMARY KEY,
                        name varchar(200) NOT NULL,
                        classification varchar(200) NOT NULL
);


CREATE TABLE problems(
                         id SERIAL PRIMARY KEY,
                         name varchar(200) NOT NULL
);


CREATE TABLE solution(
                         name varchar(200) NOT NULL,
                         action_id BIGINT NOT NULL,
                         science_id BIGINT NOT NULL,
                         problems_id BIGINT NOT NULL,


                         CONSTRAINT action_id_fk FOREIGN KEY (action_id) REFERENCES  action_people (id),
                         CONSTRAINT scince_id_fk FOREIGN KEY (science_id) REFERENCES  science (id),
                         CONSTRAINT problems_id_fk FOREIGN KEY (problems_id) REFERENCES  problems (id)
);




CREATE TABLE education(
                          name varchar(200) NOT NULL,
                          user_id BIGINT NOT NULL,
                          science_id BIGINT NOT NULL,




                          CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES  action_people (id),
                          CONSTRAINT scince_id_fk FOREIGN KEY (science_id) REFERENCES  science (id)
);


CREATE TABLE subtype_science(
                                id SERIAL PRIMARY KEY,
                                name varchar(200) NOT NULL,
                                science_id BIGINT NOT NULL,


                                CONSTRAINT scince_id_fk FOREIGN KEY (science_id) REFERENCES  science (id)
);
INSERT INTO people (name, sex, age)
VALUES('Grant','man', 35);


INSERT INTO action_people (user_id, name, place, time, count_place)
VALUES(1,'Прочитал доклад', 'Бруклин', '2004-12-25', 2);


INSERT INTO science (name, classification)
VALUES('patalogy','expert');


INSERT INTO education (user_id,science_id,name)
VALUES(1,1,'Гарвард');


INSERT INTO problems (name)
VALUES('Исчезновение леса');


INSERT INTO solution (action_id, science_id, problems_id, name)
VALUES(1,1,1,'Решение проблемы исчезновение леса: ...');

CREATE OR REPLACE FUNCTION calculate_avg_temperature()
RETURNS TRIGGER AS $$
BEGIN
UPDATE weather
SET temperature = (SELECT AVG(temperature) FROM weather WHERE city = NEW.city)
WHERE city = NEW.city;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_avg_temperature
    AFTER INSERT ON weather
    FOR EACH ROW
    EXECUTE FUNCTION calculate_avg_temperature();