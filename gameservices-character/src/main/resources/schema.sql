CREATE TABLE IF NOT EXISTS Race
(
    uuid    UUID PRIMARY KEY NOT NULL,
    type    TEXT,
    name    TEXT UNIQUE      NOT NULL,
    flavor  JSON,
    prompt  TEXT,
    created DATETIME,
    updated DATETIME,
    version INTEGER
);

CREATE TABLE IF NOT EXISTS Skill
(
    uuid    UUID PRIMARY KEY NOT NULL,
    type    TEXT,
    name    TEXT UNIQUE      NOT NULL,
    flavor  JSON,
    prompt  TEXT,
    created DATETIME,
    updated DATETIME,
    version INTEGER
);

CREATE TABLE IF NOT EXISTS Stat
(
    uuid    UUID PRIMARY KEY NOT NULL,
    type    TEXT,
    name    TEXT UNIQUE      NOT NULL,
    flavor  JSON,
    prompt  TEXT,
    created DATETIME,
    updated DATETIME,
    version INTEGER
);

CREATE TABLE IF NOT EXISTS Effect
(
    uuid    UUID PRIMARY KEY NOT NULL,
    type    TEXT,
    name    TEXT UNIQUE      NOT NULL,
    flavor  JSON,
    prompt  TEXT,
    created DATETIME,
    updated DATETIME,
    version INTEGER
);

CREATE TABLE IF NOT EXISTS Attribute
(
    uuid    UUID PRIMARY KEY NOT NULL,
    type    TEXT,
    name    TEXT UNIQUE      NOT NULL,
    flavor  JSON,
    prompt  TEXT,
    created DATETIME,
    updated DATETIME,
    version INTEGER
);

CREATE TABLE IF NOT EXISTS Profession
(
    uuid       UUID PRIMARY KEY NOT NULL,
    type       TEXT,
    name       TEXT UNIQUE      NOT NULL,
    skills     JSON,
    stats      JSON,
    attributes JSON,
    flavor     JSON,
    prompt     TEXT,
    created    DATETIME,
    updated    DATETIME,
    version    INTEGER
);

CREATE TABLE IF NOT EXISTS Character
(
    uuid       UUID PRIMARY KEY NOT NULL,
    name       TEXT UNIQUE      NOT NULL,
    sex        JSON,
    race       JSON,
    _user      JSON,
    inventory  JSON,
    profession JSON,
    dimensions JSON,
    skills     JSON,
    stats      JSON,
    attributes JSON,
    effects    JSON,
    flavor     JSON,
    prompt     TEXT,
    created    DATETIME,
    updated    DATETIME,
    version    INTEGER
);

CREATE TABLE IF NOT EXISTS Creature
(
    uuid       UUID PRIMARY KEY NOT NULL,
    name       TEXT UNIQUE      NOT NULL,
    sex        JSON,
    race       JSON,
    _user      JSON,
    inventory  JSON,
    profession JSON,
    dimensions JSON,
    skills     JSON,
    stats      JSON,
    attributes JSON,
    effects    JSON,
    flavor     JSON,
    prompt     TEXT,
    created    DATETIME,
    updated    DATETIME,
    version    INTEGER
);