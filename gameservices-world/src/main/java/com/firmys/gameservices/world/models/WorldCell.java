package com.firmys.gameservices.world.models;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record WorldCell(UUID uuid) {}
