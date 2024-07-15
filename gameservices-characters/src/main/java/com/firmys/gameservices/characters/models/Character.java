package com.firmys.gameservices.characters.models;

import com.firmys.gameservices.common.CommonEntity;
import java.util.Set;
import java.util.UUID;
import lombok.experimental.Accessors;

@Accessors(fluent = true, chain = true)
public record Character(
    UUID uuid,
    String name,
    String description,
    String gender,
    int age,
    int height,
    int weight,
    UUID userId,
    UUID inventoryId,
    Set<CharacterAttribute> attributes)
    implements CommonEntity {}
