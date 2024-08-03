package com.firmys.gameservices.world.models;

import java.util.UUID;
import lombok.Builder;

@Builder(toBuilder = true)
public record WorldCell(UUID uuid) {}
