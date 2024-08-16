CREATE TABLE IF NOT EXISTS _User
(
    uuid      UUID PRIMARY KEY NOT NULL,
    firstName TEXT             NOT NULL,
    lastName  TEXT             NOT NULL,
    email     TEXT UNIQUE      NOT NULL,
    flavor    TEXT,
    prompt    TEXT,
    created   DATETIME,
    updated   DATETIME,
    version   INTEGER
);