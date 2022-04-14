package com.firmys.gameservice.inventory.impl;

import com.firmys.gameservice.inventory.service.data.Item;

import java.util.UUID;

public class OwnedItemObject extends GameItems {
    private final UUID uuid;

    /**
     * Item which can be possessed by a Player instance or Location instance
     * @param description simple description of item
     * @param name simple name for item
     *
     * Simple UUID derived from OwnedItem name
     */
    public OwnedItemObject(String description, String name) {
        super(description, name, 1, new int[]{1, 1});
        uuid = UUID.randomUUID();
    }

    public OwnedItemObject(String description, String name, Float weight, int[] dimensions) {
        super(description, name, weight, dimensions);
        uuid = UUID.randomUUID();
    }

    public OwnedItemObject(Item item) {
        super(item.getDescription(),
                item.getName(), item.getWeight(), new int[]{item.getLength(),
                item.getWidth(), item.getHeight()});
        uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }
}
