package com.firmys.gameservices.inventory.models;

import com.firmys.gameservices.common.CommonEntity;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
public record Inventory(
    @With @Id UUID uuid, UUID characterId, Set<UUID> items, Set<UUID> currencies)
    implements CommonEntity {}
