package com.firmys.gameservices.characters.models;

import com.firmys.gameservices.characters.enums.Effects;
import com.firmys.gameservices.common.CommonEntity;
import java.util.UUID;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@With
@Builder(toBuilder = true)
public record Effect(
    @Id UUID uuid, String name, String description, Effects type, UUID targetId, Long delta)
    implements CommonEntity {}
