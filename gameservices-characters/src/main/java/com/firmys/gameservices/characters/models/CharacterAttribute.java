package com.firmys.gameservices.characters.models;

import com.firmys.gameservices.common.CommonObject;
import java.util.UUID;
import lombok.Builder;

@Builder(toBuilder = true)
public record CharacterAttribute(UUID characterId, Attribute attribute, long value)
    implements CommonObject {}
