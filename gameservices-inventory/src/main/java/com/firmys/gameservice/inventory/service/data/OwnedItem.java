package com.firmys.gameservice.inventory.service.data;

import com.firmys.gameservice.common.GameData;

import java.util.UUID;

public class OwnedItem implements GameData {

    private UUID uuid = UUID.randomUUID();
    private Item item;

    public OwnedItem(Item item) {
        this.item = item;
    }

    public OwnedItem() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String toString() {
        return "OwnedItem{" +
                "uuid=" + uuid +
                ", item=" + item +
                '}';
    }
}
