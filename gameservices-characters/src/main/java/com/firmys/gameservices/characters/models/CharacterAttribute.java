package com.firmys.gameservices.characters.models;

import java.util.UUID;

public record CharacterAttribute(UUID characterId, Attribute attribute, long value) {}
