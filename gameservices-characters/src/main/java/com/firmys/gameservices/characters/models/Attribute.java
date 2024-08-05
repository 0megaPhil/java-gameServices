package com.firmys.gameservices.characters.models;

import com.firmys.gameservices.characters.enums.Attributes;
import com.firmys.gameservices.common.CommonEntity;
import java.util.UUID;
import lombok.Builder;
import lombok.With;

@With
@Builder(toBuilder = true)
public record Attribute(UUID uuid, Attributes type, String description) implements CommonEntity {}
