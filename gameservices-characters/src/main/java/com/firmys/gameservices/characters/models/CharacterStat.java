package com.firmys.gameservices.characters.models;

import com.firmys.gameservices.common.CommonObject;
import java.util.UUID;
import lombok.Builder;
import lombok.With;

@With
@Builder(toBuilder = true)
public record CharacterStat(UUID statId, String name, Long value) implements CommonObject {}
