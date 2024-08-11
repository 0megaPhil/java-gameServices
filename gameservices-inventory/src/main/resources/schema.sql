CREATE TABLE IF NOT EXISTS Inventory
(
    uuid       UUID PRIMARY KEY,
    items      TEXT ARRAY,
    currencies TEXT ARRAY,
    flavor     TEXT,
    prompt     TEXT
);

CREATE TABLE IF NOT EXISTS Item
(
    uuid       UUID PRIMARY KEY,
    name       TEXT NOT NULL,
    dimensions TEXT ARRAY,
    flavor     TEXT,
    prompt     TEXT
);