CREATE TABLE IF NOT EXISTS Race
(
    uuid    UUID PRIMARY KEY,
    type    TEXT,
    name    TEXT NOT NULL,
    flavor  TEXT,
    prompt  TEXT,
    created TIMESTAMP,
    updated TIMESTAMP,
    version INTEGER
);

CREATE TABLE IF NOT EXISTS Skill
(
    uuid    UUID PRIMARY KEY,
    type    TEXT,
    name    TEXT NOT NULL,
    flavor  TEXT,
    prompt  TEXT,
    created TIMESTAMP,
    updated TIMESTAMP,
    version INTEGER
);

CREATE TABLE IF NOT EXISTS Stat
(
    uuid    UUID PRIMARY KEY,
    type    TEXT,
    name    TEXT NOT NULL,
    flavor  TEXT,
    prompt  TEXT,
    created TIMESTAMP,
    updated TIMESTAMP,
    version INTEGER
);

CREATE TABLE IF NOT EXISTS Effect
(
    uuid    UUID PRIMARY KEY,
    type    TEXT,
    name    TEXT NOT NULL,
    flavor  TEXT,
    prompt  TEXT,
    created TIMESTAMP,
    updated TIMESTAMP,
    version INTEGER
);

CREATE TABLE IF NOT EXISTS Attribute
(
    uuid    UUID PRIMARY KEY,
    type    TEXT,
    name    TEXT NOT NULL,
    flavor  TEXT,
    prompt  TEXT,
    created TIMESTAMP,
    updated TIMESTAMP,
    version INTEGER
);

CREATE TABLE IF NOT EXISTS Profession
(
    uuid       UUID PRIMARY KEY,
    type       TEXT,
    name       TEXT NOT NULL,
    skills     TEXT ARRAY,
    stats      TEXT ARRAY,
    attributes TEXT ARRAY,
    flavor     TEXT,
    prompt     TEXT,
    created    TIMESTAMP,
    updated    TIMESTAMP,
    version    INTEGER
);

CREATE TABLE IF NOT EXISTS Character
(
    uuid       UUID PRIMARY KEY,
    name       TEXT NOT NULL,
    sex        TEXT,
    race       TEXT,
    _user      TEXT,
    inventory  TEXT,
    profession TEXT,
    dimensions TEXT ARRAY,
    skills     TEXT ARRAY,
    stats      TEXT ARRAY,
    attributes TEXT ARRAY,
    effects    TEXT ARRAY,
    flavor     TEXT,
    prompt     TEXT,
    created    TIMESTAMP,
    updated    TIMESTAMP,
    version    INTEGER
);

CREATE TABLE IF NOT EXISTS Creature
(
    uuid       UUID PRIMARY KEY,
    name       TEXT NOT NULL,
    sex        TEXT,
    race       TEXT,
    _user      TEXT,
    inventory  TEXT,
    profession TEXT,
    dimensions TEXT ARRAY,
    skills     TEXT ARRAY,
    stats      TEXT ARRAY,
    attributes TEXT ARRAY,
    effects    TEXT ARRAY,
    flavor     TEXT,
    prompt     TEXT,
    created    TIMESTAMP,
    updated    TIMESTAMP,
    version    INTEGER
);