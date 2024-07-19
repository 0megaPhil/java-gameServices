package com.firmys.gameservices.inventory.models;

import com.firmys.gameservices.common.CommonEntity;
import java.util.UUID;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
public record InventoryItem(@With @Id UUID uuid, UUID itemId, UUID inventoryId, Long quantity)
    implements CommonEntity {}
