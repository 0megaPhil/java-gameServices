package com.firmys.gameservices.characters.models;

import com.firmys.gameservices.common.CommonEntity;
import java.util.UUID;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@With
@Builder(toBuilder = true)
public record CharacterSkill(
    @Id UUID uuid, UUID skillId, String name, Long skillValue, String description)
    implements CommonEntity {}
