CREATE TABLE IF NOT EXISTS Transaction
(
    uuid        UUID PRIMARY KEY NOT NULL,
    inventoryId TEXT             NOT NULL,
    currency    TEXT             NOT NULL,
    item        TEXT,
    flavor      TEXT,
    prompt      TEXT,
    created     DATETIME,
    updated     DATETIME,
    version     INTEGER
);

CREATE TABLE IF NOT EXISTS Currency
(
    uuid    UUID PRIMARY KEY NOT NULL,
    name    TEXT UNIQUE      NOT NULL,
    flavor  TEXT,
    prompt  TEXT,
    created DATETIME,
    updated DATETIME,
    version INTEGER
);