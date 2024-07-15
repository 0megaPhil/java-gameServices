package com.firmys.gameservices.inventory.models;

import com.firmys.gameservices.common.CommonEntity;
import java.util.Set;
import java.util.UUID;

public record Inventory(UUID uuid, UUID characterId, Set<InventoryItem> inventoryItems)
    implements CommonEntity {}
