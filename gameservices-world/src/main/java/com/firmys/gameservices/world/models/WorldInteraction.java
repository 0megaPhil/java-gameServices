package com.firmys.gameservices.world.models;

import java.util.UUID;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@With
@Builder(toBuilder = true)
public record WorldInteraction(@Id UUID uuid) {}
