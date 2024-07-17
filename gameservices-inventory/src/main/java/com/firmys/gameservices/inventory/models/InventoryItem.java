package com.firmys.gameservices.inventory.models;

import com.firmys.gameservices.common.CommonObject;
import java.util.UUID;
import lombok.Builder;

@Builder(toBuilder = true)
public record InventoryItem(UUID itemId, UUID inventoryId, Long quantity) implements CommonObject {}
