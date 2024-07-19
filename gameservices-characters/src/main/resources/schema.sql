CREATE TABLE IF NOT EXISTS Character
(
    uuid         UUID PRIMARY KEY,
    name         TEXT,
    description  TEXT,
    gender       TEXT,
    age          INT,
    height       INT,
    weight       INT,
    user_id      UUID,
    inventory_id UUID
);