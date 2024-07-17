package com.firmys.gameservices.inventory.models;

import com.firmys.gameservices.common.CommonEntity;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
public record Inventory(@Id UUID uuid, UUID characterId, Set<InventoryItem> inventoryItems)
    implements CommonEntity {
  public Inventory {
    uuid = Optional.ofNullable(uuid).orElseGet(UUID::randomUUID);
  }
}
