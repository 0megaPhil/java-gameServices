CREATE TABLE IF NOT EXISTS _User
(
    uuid      UUID PRIMARY KEY,
    firstName TEXT NOT NULL,
    lastName  TEXT NOT NULL,
    flavor    TEXT,
    prompt    TEXT,
    created   TIMESTAMP,
    updated   TIMESTAMP,
    version   INTEGER
);