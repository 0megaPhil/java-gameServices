package com.firmys.gameservices.inventory.models;

import com.firmys.gameservices.common.CommonEntity;
import java.util.UUID;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
public record InventoryCurrency(
    @With @Id UUID uuid, String name, UUID currencyId, UUID inventoryId, Long quantity)
    implements CommonEntity {}
