package com.firmys.gameservices.characters.models;

import com.firmys.gameservices.common.CommonEntity;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
public record Character(
    @Id UUID uuid,
    String name,
    String description,
    String gender,
    int age,
    int height,
    int weight,
    UUID userId,
    UUID inventoryId,
    Set<CharacterAttribute> attributes)
    implements CommonEntity {

  public Character {
    uuid = Optional.ofNullable(uuid).orElseGet(UUID::randomUUID);
  }
}
