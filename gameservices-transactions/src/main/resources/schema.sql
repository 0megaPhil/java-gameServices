CREATE TABLE IF NOT EXISTS Transaction
(
    uuid        UUID PRIMARY KEY,
    inventoryId TEXT NOT NULL,
    currency    TEXT NOT NULL,
    item        TEXT,
    flavor      TEXT,
    prompt      TEXT
);

CREATE TABLE IF NOT EXISTS Currency
(
    uuid   UUID PRIMARY KEY,
    name   TEXT NOT NULL,
    flavor TEXT,
    prompt TEXT
);