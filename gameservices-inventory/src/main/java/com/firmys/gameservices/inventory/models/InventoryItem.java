package com.firmys.gameservices.inventory.models;

import com.firmys.gameservices.common.CommonObject;
import java.util.UUID;

public record InventoryItem(UUID itemId, UUID inventoryId, Long quantity) implements CommonObject {}
