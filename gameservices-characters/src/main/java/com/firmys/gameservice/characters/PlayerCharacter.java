package com.firmys.gameservice.characters;

import com.firmys.gameservice.inventory.impl.PlayerCharacterInventory;

import java.util.UUID;

public class PlayerCharacter extends CharacterAbstract {

    public PlayerCharacter(UUID uuid, String name,
                           String description, Gender gender,
                           int height, int weight,
                           PlayerCharacterInventory inventory) {
        super(uuid, name, description, gender, height, weight, inventory);
    }
}
