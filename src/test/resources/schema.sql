CREATE TABLE book (
    id VARCHAR(50) PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    isbn VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE app_user (
    registration_number VARCHAR(7) PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    active BOOLEAN NOT NULL
);

CREATE TABLE loan (
    id VARCHAR(50) PRIMARY KEY,
    book_id VARCHAR(50) NOT NULL,
    registration_number VARCHAR(7) NOT NULL,
    borrowed_at DATE NOT NULL,
    due_at DATE NOT NULL,
    returned_at DATE,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (book_id) REFERENCES book(id)
);