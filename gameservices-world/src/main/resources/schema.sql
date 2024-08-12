CREATE TABLE IF NOT EXISTS World
(
    uuid       UUID PRIMARY KEY,
    type       TEXT NOT NULL,
    terrains   TEXT ARRAY,
    races      TEXT ARRAY,
    techLevel  TEXT,
    magicLevel TEXT,
    flavor     TEXT,
    prompt     TEXT,
    created    TIMESTAMP,
    updated    TIMESTAMP,
    version    INTEGER
);

CREATE TABLE IF NOT EXISTS Terrain
(
    uuid    UUID PRIMARY KEY,
    type    TEXT NOT NULL,
    effects TEXT ARRAY,
    flavor  TEXT,
    prompt  TEXT,
    created TIMESTAMP,
    updated TIMESTAMP,
    version INTEGER
);