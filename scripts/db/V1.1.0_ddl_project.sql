--execute the script into postgres database
CREATE TABLE book (
    isbn varchar(50) NOT NULL,
    title varchar(250) ,
    authors varchar[] NULL,
    year   integer NULL,
    price    numeric(12, 2) NULL,
    genre    varchar(250) NULL,
    CONSTRAINT isbn_unique UNIQUE (isbn)
);

CREATE TABLE author (
    name  varchar(250) NOT NULL,
    birthday  date
);

INSERT INTO book (isbn, title, authors, year,price,genre) VALUES ('00001', 'A Tale of Two Cities', '{"Charles Dickens"}', 1859, 42.00,'Historical fiction');
INSERT INTO book (isbn, title, authors, year,price,genre) VALUES ('00002', 'Harry Potter and the Chamber of Secrets', '{"J. K. Rowling"}', 1998, 52.50,'Fantasy');
INSERT INTO book (isbn, title, authors, year,price,genre) VALUES ('00003', 'Harry Potter and the Goblet of Fire', '{"J. K. Rowling"}', 1998, 52.50,'Fantasy');
INSERT INTO book (isbn, title, authors, year,price,genre) VALUES ('00004', 'The Young Guard', '{"Alexander","Alexandrovich","Fadeyev"}', 1945, 77.50,'Young adult historical novel');


