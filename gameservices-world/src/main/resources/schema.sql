CREATE TABLE IF NOT EXISTS World
(
    uuid      UUID PRIMARY KEY,
    terrainId UUID, -- Assuming that Terrain is another table and terrainId is a foreign key.
    flavor    TEXT,
    prompt    TEXT
);

CREATE TABLE IF NOT EXISTS Terrain
(
    uuid   UUID PRIMARY KEY,
    type   TEXT NOT NULL,
    flavor TEXT,
    prompt TEXT
);