CREATE TABLE todos
(
    id INT NOT NULL PRIMARY KEY,
    description VARCHAR NOT NULL,
    completed BOOLEAN DEFAULT false
);