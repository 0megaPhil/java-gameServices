CREATE TABLE IF NOT EXISTS World
(
    uuid       UUID PRIMARY KEY NOT NULL,
    type       TEXT             NOT NULL,
    name       TEXT UNIQUE      NOT NULL,
    terrains   TEXT,
    races      TEXT,
    techLevel  TEXT,
    magicLevel TEXT,
    flavor     TEXT,
    prompt     TEXT,
    created    DATETIME,
    updated    DATETIME,
    version    INTEGER
);

CREATE TABLE IF NOT EXISTS Terrain
(
    uuid    UUID PRIMARY KEY NOT NULL,
    name    TEXT UNIQUE      NOT NULL,
    type    TEXT             NOT NULL,
    effects TEXT,
    flavor  TEXT,
    prompt  TEXT,
    created DATETIME,
    updated DATETIME,
    version INTEGER
);