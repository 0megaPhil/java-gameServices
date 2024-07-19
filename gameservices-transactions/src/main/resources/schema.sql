CREATE TABLE IF NOT EXISTS Transaction
(
    uuid        UUID NOT NULL PRIMARY KEY,
    type        VARCHAR(255),
    fungibleId  UUID,
    characterId UUID,
    delta       BIGINT,
    initial     BIGINT,
    result      BIGINT,
    timestamp   TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Currency
(
    uuid        UUID NOT NULL PRIMARY KEY,
    name        VARCHAR(255),
    description VARCHAR(255)
);