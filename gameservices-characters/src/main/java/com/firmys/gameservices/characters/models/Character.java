package com.firmys.gameservices.characters.models;

import com.firmys.gameservices.characters.enums.Sexes;
import com.firmys.gameservices.common.CommonEntity;
import com.firmys.gameservices.common.attributes.Dimension;
import com.firmys.gameservices.inventory.models.Inventory;
import com.firmys.gameservices.users.models.User;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Singular;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@With
@Builder(toBuilder = true)
public record Character(
    @With @Id UUID uuid,
    @NotEmpty String name,
    String summary,
    String appearance,
    String personality,
    String background,
    Sexes sex,
    Race race,
    @Singular Set<Dimension> dimensions,
    @Column("_user") User user,
    Inventory inventory,
    CharacterStatus status,
    @Singular Set<CharacterAttribute> attributes,
    @Singular Set<CharacterSkill> skills,
    @Singular Set<CharacterStat> stats)
    implements CommonEntity {}
