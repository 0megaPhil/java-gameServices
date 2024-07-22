CREATE TABLE IF NOT EXISTS Character
(
    UUID         UUID PRIMARY KEY,
    NAME         TEXT,
    DESCRIPTION  TEXT,
    GENDER       TEXT,
    AGE          INT,
    HEIGHT       INT,
    WEIGHT       INT,
    USER_ID      UUID,
    INVENTORY_ID UUID
);