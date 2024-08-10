CREATE TABLE IF NOT EXISTS Inventory
(
    uuid        UUID PRIMARY KEY,
    characterId UUID NOT NULL,
    items       TEXT ARRAY,
    currencies  TEXT ARRAY,
    flavor      TEXT,
    prompt      TEXT
);

CREATE TABLE IF NOT EXISTS Item
(
    uuid   UUID PRIMARY KEY,
    name   TEXT NOT NULL,
    flavor TEXT,
    prompt TEXT
);