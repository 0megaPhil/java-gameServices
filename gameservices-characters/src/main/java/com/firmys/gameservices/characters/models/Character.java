package com.firmys.gameservices.characters.models;

import com.firmys.gameservices.common.CommonEntity;
import jakarta.validation.constraints.NotEmpty;
import java.util.UUID;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
public record Character(
    @With @Id UUID uuid,
    @NotEmpty String name,
    String description,
    String gender,
    int age,
    int height,
    int weight,
    UUID userId,
    UUID inventoryId)
    implements CommonEntity {}
