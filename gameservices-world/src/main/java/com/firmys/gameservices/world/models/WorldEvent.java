package com.firmys.gameservices.world.models;

import com.firmys.gameservices.common.CommonEntity;
import com.firmys.gameservices.world.enums.WorldEvents;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@With
@Builder(toBuilder = true)
public record WorldEvent(
    @Id UUID uuid, WorldCell cell, Set<WorldDelta> deltas, WorldEvents eventType)
    implements CommonEntity {}
