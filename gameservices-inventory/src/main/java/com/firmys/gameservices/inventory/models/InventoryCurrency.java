package com.firmys.gameservices.inventory.models;

import com.firmys.gameservices.common.CommonObject;
import java.util.UUID;
import lombok.Builder;

@Builder(toBuilder = true)
public record InventoryCurrency(UUID itemId, UUID currencyId, Long quantity)
    implements CommonObject {}
