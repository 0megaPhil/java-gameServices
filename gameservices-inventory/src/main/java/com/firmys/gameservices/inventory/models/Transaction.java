package com.firmys.gameservices.inventory.models;

import com.firmys.gameservices.common.CommonEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
public record Transaction(
    @Id UUID uuid,
    UUID objectId,
    UUID characterId,
    Long delta,
    Long initial,
    Long result,
    LocalDateTime timestamp)
    implements CommonEntity {

  public Transaction {
    uuid = Optional.ofNullable(uuid).orElseGet(UUID::randomUUID);
  }
}
