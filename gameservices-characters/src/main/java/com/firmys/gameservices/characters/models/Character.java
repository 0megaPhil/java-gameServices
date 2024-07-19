package com.firmys.gameservices.characters.models;

import com.firmys.gameservices.common.CommonEntity;
import java.util.UUID;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
public record Character(
    @With @Id UUID uuid,
    String name,
    String description,
    String gender,
    int age,
    int height,
    int weight,
    UUID userId,
    UUID inventoryId)
    implements CommonEntity {}
