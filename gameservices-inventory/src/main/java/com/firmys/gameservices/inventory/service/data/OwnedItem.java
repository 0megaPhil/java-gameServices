package com.firmys.gameservices.inventory.service.data;

import com.firmys.gameservices.common.AbstractGameData;
import com.firmys.gameservices.common.GameData;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class OwnedItem extends AbstractGameData {

    private UUID itemUuid;
    private final AtomicReference<SortedSet<UUID>> ownedItemUuids = new AtomicReference<>(new TreeSet<>());
    private int count = ownedItemUuids.get().size();

    public OwnedItem(Item item) {
        this.itemUuid = item.getUuid();
    }

    public OwnedItem() {
    }

    public int getCount() {
        this.count = ownedItemUuids.get().size();
        return count;
    }

    public UUID getItemUuid() {
        return itemUuid;
    }

    public OwnedItem add() {
        return add(1);
    }

    public OwnedItem add(int amount) {
        ownedItemUuids.getAndUpdate(u -> {
            IntStream.range(0, amount)
                    .forEach(i -> u.add(UUID.randomUUID()));
            return u;
        });
        return this;
    }

    public OwnedItem consume() {
        return consume(1);
    }

    public OwnedItem consume(int amount) {
        if(getCount() < amount) {
            throw new RuntimeException("Insufficient inventory count of " +
                    getCount() + " for consumption of " + amount + " Item " + itemUuid.toString());
        }
        ownedItemUuids.getAndUpdate(u -> {
            IntStream.range(0, amount)
                    .forEach(i -> u.remove(ownedItemUuids.get().last()));
            return u;
        });
        return this;
    }

    public void setItemUuid(UUID itemUuid) {
        this.itemUuid = itemUuid;
    }

    public Set<UUID> getOwnedItemUuids() {
        return ownedItemUuids.get();
    }

}
