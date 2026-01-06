CREATE TABLE users(
    INTEGER PRIMARY KEY user_id,
    VARCHAR(256) name,
    VARCHAR(256) UNIQUE email,
    VARCHAR(256) password
)



