/*
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    username
    VARCHAR
(
    50
) NOT NULL PRIMARY KEY,
    password VARCHAR
(
    100
) NOT NULL,
    enabled BOOLEAN NOT NULL
    );

CREATE TABLE IF NOT EXISTS authorities
(
    username
    VARCHAR
(
    50
) NOT NULL,
    authority VARCHAR
(
    50
) NOT NULL,
    CONSTRAINT fk_username FOREIGN KEY
(
    username
) REFERENCES users
(
    username
)
    );

INSERT INTO users (username, password, enabled)
VALUES ('admin', '$2a$10$pSM3dZIRXDX9qX2R94tF1ehbH6HcKGwApQJDwl3NmC8bvmlIbmqrK', true);

INSERT INTO users (username, password, enabled)
VALUES ('user', '$2a$10$XTYrfRQzPUDsJ.smAKwRougTNtZ80Ak267mGWnaYYQMZG9KaZD2jy', true);

INSERT INTO authorities (username, authority)
VALUES ('user', 'ROLE_USER');

INSERT INTO authorities (username, authority)
VALUES ('admin', 'ROLE_ADMIN');
 */