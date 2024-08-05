package com.firmys.gameservices.characters.models;

import com.firmys.gameservices.common.CommonEntity;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@With
@Builder
public record Profession(
    @Id UUID uuid, String name, String description, Set<Skill> skills, Map<Stat, Long> requirements)
    implements CommonEntity {}
