package com.firmys.gameservices.characters.models;

import lombok.Builder;
import lombok.With;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@With
@Builder(toBuilder = true)
public record CharacterStatus(
    UUID characterId, Map<Attribute, Long> attributes, Set<Effect> effects) {}
