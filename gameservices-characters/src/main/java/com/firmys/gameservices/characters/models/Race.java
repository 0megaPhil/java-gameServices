package com.firmys.gameservices.characters.models;

import com.firmys.gameservices.common.CommonEntity;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@With
@Builder(toBuilder = true)
public record Race(
    @Id UUID uuid, String name, String description, UUID worldId, Set<String> characteristics)
    implements CommonEntity {}
