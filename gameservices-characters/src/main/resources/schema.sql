CREATE TABLE IF NOT EXISTS Race
(
    uuid        UUID PRIMARY KEY,
    name        TEXT NOT NULL,
    description TEXT,
    worldId     UUID,
    flavor      TEXT,
    prompt      TEXT
);

CREATE TABLE IF NOT EXISTS Skill
(
    uuid        UUID PRIMARY KEY,
    name        TEXT NOT NULL,
    description TEXT,
    flavor      TEXT,
    prompt      TEXT
);

CREATE TABLE IF NOT EXISTS Stat
(
    uuid   UUID PRIMARY KEY,
    name   TEXT NOT NULL,
    flavor TEXT,
    prompt TEXT
);

CREATE TABLE IF NOT EXISTS Character
(
    uuid       UUID PRIMARY KEY,
    name       TEXT NOT NULL,
    sex        TEXT,
    race       TEXT,
    dimensions TEXT, -- May need a separate table depending on the structure of Dimension
    _user      TEXT, -- May need a separate table depending on the structure of User
    inventory  TEXT, -- May need a separate table depending on the structure of Inventory
    skills     TEXT ARRAY,
    stats      TEXT ARRAY,
    flavor     TEXT,
    prompt     TEXT
);