CREATE TABLE IF NOT EXISTS World
(
    uuid     UUID PRIMARY KEY,
    type     TEXT NOT NULL,
    terrains TEXT ARRAY,
    races    TEXT ARRAY,
    flavor   TEXT,
    prompt   TEXT
);

CREATE TABLE IF NOT EXISTS Terrain
(
    uuid   UUID PRIMARY KEY,
    type   TEXT NOT NULL,
    flavor TEXT,
    prompt TEXT
);