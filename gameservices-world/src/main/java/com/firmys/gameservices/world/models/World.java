package com.firmys.gameservices.world.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.firmys.gameservices.common.CommonEntity;
import com.firmys.gameservices.common.attributes.Characteristic;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@With
@Builder(toBuilder = true)
public record World(
    @Id UUID uuid,
    Long xSize,
    Long ySize,
    Long zSize,
    Long startTime,
    Long worldTime,
    String description,
    Set<UUID> races,
    Set<Characteristic> characteristics,
    Map<String, WorldCell> locationCells)
    implements CommonEntity {

  public World {
    startTime =
        Optional.ofNullable(startTime)
            .orElse(LocalDateTime.now(Clock.systemUTC()).toEpochSecond(ZoneOffset.UTC));
    worldTime = Optional.ofNullable(worldTime).orElse(0L);
  }

  @JsonGetter("worldTime")
  public Long worldTime() {
    return withWorldTime(
            worldTime
                + (LocalDateTime.now(Clock.systemUTC()).toEpochSecond(ZoneOffset.UTC) - startTime))
        .worldTime();
  }
}
