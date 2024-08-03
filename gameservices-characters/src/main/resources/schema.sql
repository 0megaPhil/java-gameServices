CREATE TABLE IF NOT EXISTS Race
(
    uuid            UUID PRIMARY KEY,
    name            TEXT NOT NULL,
    description     TEXT,
    worldId         UUID,
    characteristics TEXT ARRAY
);


CREATE TABLE IF NOT EXISTS Skill
(
    uuid        UUID PRIMARY KEY,
    name        TEXT NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS Stat
(
    uuid        UUID PRIMARY KEY,
    name        TEXT NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS Character
(
    uuid        UUID PRIMARY KEY,
    name        TEXT NOT NULL,
    summary     TEXT,
    appearance  TEXT,
    personality TEXT,
    background  TEXT,
    sex         TEXT,
    raceId      UUID,
    age         TEXT,
    height      TEXT,
    weight      TEXT,
    userId      UUID,
    inventoryId UUID,
    skillIds    UUID ARRAY,
    statIds     UUID ARRAY,
    skills      TEXT,
    stats       TEXT,
    FOREIGN KEY (raceId) REFERENCES Race (uuid)
);

CREATE TABLE IF NOT EXISTS CharacterStat
(
    uuid        UUID PRIMARY KEY,
    statId      UUID,
    name        TEXT NOT NULL,
    description TEXT,
    statValue   BIGINT
);

CREATE TABLE IF NOT EXISTS CharacterSkill
(
    uuid        UUID PRIMARY KEY,
    skillId     UUID,
    name        TEXT NOT NULL,
    description TEXT,
    skillValue  BIGINT
);