package com.firmys.gameservices.inventory.models;

import com.firmys.gameservices.common.CommonEntity;
import java.time.LocalDateTime;
import java.util.UUID;

public record Transaction(
    UUID uuid,
    UUID objectId,
    UUID characterId,
    Long delta,
    Long initial,
    Long result,
    LocalDateTime timestamp)
    implements CommonEntity {}
