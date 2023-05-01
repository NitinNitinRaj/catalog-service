DROP TABLE IF EXISTS book;

CREATE TABLE
    book(
        id BIGSERIAL PRIMARY KEY NOT NULL,
        author VARCHAR(255) NOT NULL,
        isbn VARCHAR(255) UNIQUE NOT NULL,
        price FLOAT8 NOT NULL,
        title VARCHAR(255) NOT NULL,
        createdDate TIMESTAMP NOT NULL,
        lastModifiedDate TIMESTAMP NOT NULL,
        version INTEGER NOT NULL
    );