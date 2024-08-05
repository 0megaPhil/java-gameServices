package com.firmys.gameservices.world.models;

import com.firmys.gameservices.common.CommonEntity;
import com.firmys.gameservices.world.enums.Terrains;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@With
@Builder(toBuilder = true)
public record WorldCell(
    @Id UUID uuid,
    Long xLocation,
    Long yLocation,
    Long zLocation,
    UUID worldId,
    Terrains terrain,
    Set<UUID> characters,
    Map<UUID, Long> items,
    Map<UUID, Long> currencies)
    implements CommonEntity {}
