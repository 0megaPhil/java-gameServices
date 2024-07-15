package com.firmys.gameservices.inventory.models;

import java.util.UUID;

public record InventoryCurrency(UUID itemId, UUID currencyId, Long quantity) {}
