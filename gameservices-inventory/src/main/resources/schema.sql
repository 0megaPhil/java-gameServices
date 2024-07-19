CREATE TABLE IF NOT EXISTS Inventory
(
    uuid        UUID NOT NULL PRIMARY KEY,
    characterId UUID,
    currencies  UUID ARRAY[4196],
    items       UUID ARRAY[4196]
);

CREATE TABLE IF NOT EXISTS InventoryItem
(
    uuid        UUID NOT NULL PRIMARY KEY,
    itemId      UUID,
    inventoryId UUID,
    quantity    BIGINT,
    FOREIGN KEY (inventoryId) REFERENCES Inventory (uuid)
);

CREATE TABLE IF NOT EXISTS InventoryCurrency
(
    uuid        UUID NOT NULL PRIMARY KEY,
    currencyId  UUID,
    inventoryId UUID,
    quantity    BIGINT,
    FOREIGN KEY (inventoryId) REFERENCES Inventory (uuid)
);