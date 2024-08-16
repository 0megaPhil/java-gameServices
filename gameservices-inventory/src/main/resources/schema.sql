CREATE TABLE IF NOT EXISTS Inventory
(
    uuid       UUID PRIMARY KEY NOT NULL,
    items      TEXT,
    currencies TEXT,
    flavor     TEXT,
    prompt     TEXT,
    created    DATETIME,
    updated    DATETIME,
    version    INTEGER
);

CREATE TABLE IF NOT EXISTS Item
(
    uuid       UUID PRIMARY KEY NOT NULL,
    name       TEXT UNIQUE      NOT NULL,
    dimensions TEXT,
    flavor     TEXT,
    prompt     TEXT,
    created    DATETIME,
    updated    DATETIME,
    version    INTEGER
);