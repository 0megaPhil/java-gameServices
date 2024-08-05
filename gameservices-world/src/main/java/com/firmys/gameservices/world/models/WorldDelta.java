package com.firmys.gameservices.world.models;

import com.firmys.gameservices.characters.models.Character;
import com.firmys.gameservices.common.CommonEntity;
import com.firmys.gameservices.inventory.models.Inventory;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@With
@Builder(toBuilder = true)
public record WorldDelta(
    @Id UUID uuid, WorldCell cell, Set<Character> characters, Set<Inventory> inventories)
    implements CommonEntity {}
