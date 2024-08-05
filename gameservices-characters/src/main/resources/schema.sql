CREATE TABLE IF NOT EXISTS Race
(
    uuid            UUID PRIMARY KEY,
    name            TEXT UNIQUE NOT NULL,
    description     TEXT,
    worldId         UUID,
    characteristics TEXT ARRAY
);


CREATE TABLE IF NOT EXISTS Skill
(
    uuid        UUID PRIMARY KEY,
    name        TEXT UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS Stat
(
    uuid        UUID PRIMARY KEY,
    name        TEXT UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS Profession
(
    uuid        UUID PRIMARY KEY,
    name        TEXT UNIQUE NOT NULL,
    description TEXT
);

-- DROP TABLE Character;

CREATE TABLE IF NOT EXISTS Character
(
    uuid        UUID PRIMARY KEY,
    name        TEXT UNIQUE NOT NULL,
    summary     TEXT,
    appearance  TEXT,
    personality TEXT,
    background  TEXT,
    sex         TEXT,
    race        TEXT,
    dimensions  TEXT ARRAY,
    _user       TEXT,
    inventory   TEXT,
    skills      TEXT ARRAY,
    stats       TEXT ARRAY
);