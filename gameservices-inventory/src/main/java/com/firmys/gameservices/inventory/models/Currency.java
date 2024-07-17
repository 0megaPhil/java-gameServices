package com.firmys.gameservices.inventory.models;

import com.firmys.gameservices.common.CommonEntity;
import com.firmys.gameservices.common.data.Attribute;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
public record Currency(@Id UUID uuid, String name, String description, Set<Attribute> attributes)
    implements CommonEntity {
  public Currency {
    uuid = Optional.ofNullable(uuid).orElseGet(UUID::randomUUID);
  }
}
