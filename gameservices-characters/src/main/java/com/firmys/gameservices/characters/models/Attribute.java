package com.firmys.gameservices.characters.models;

import com.firmys.gameservices.common.CommonEntity;
import java.util.Optional;
import java.util.UUID;
import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
public record Attribute(@Id UUID uuid, String name, String description) implements CommonEntity {
  public Attribute {
    uuid = Optional.ofNullable(uuid).orElseGet(UUID::randomUUID);
  }
}
