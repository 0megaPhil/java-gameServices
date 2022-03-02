package com.firmys.gameservice.inventory.impl;

import java.util.UUID;

public class OwnedItem extends GameItems {
    private final UUID uuid;

    /**
     * Item which can be possessed by a Player instance or Location instance
     * @param description simple description of item
     * @param name simple name for item
     *
     * Simple UUID derived from OwnedItem name
     */
    public OwnedItem(String description, String name) {
        super(description, name, 1, new int[]{1, 1});
        uuid = UUID.randomUUID();
    }

    public OwnedItem(String description, String name, Float weight, int[] dimensions) {
        super(description, name, weight, dimensions);
        uuid = UUID.randomUUID();
    }

    public OwnedItem(Item item) {
        super(item.getDescription(),
                item.getName(), item.getWeight(), new int[]{item.getSizeLength(),
                item.getSizeWidth(), item.getSizeHeight()});
        uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }
}
