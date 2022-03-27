package com.firmys.gameservice.inventory.service.data;

import java.io.Serializable;
import java.util.UUID;

public class OwnedItem implements Serializable {

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

    @Override
    public String toString() {
        return "OwnedItem{" +
                "uuid=" + uuid +
                ", item=" + item +
                '}';
    }
}
