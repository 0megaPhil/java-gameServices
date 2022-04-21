package com.firmys.gameservices.inventory.service.data;

import com.firmys.gameservices.common.GameData;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class OwnedItem implements GameData {

    private UUID itemUuid;
    private final AtomicReference<SortedSet<UUID>> UUIDs = new AtomicReference<>(new TreeSet<>());
    private int count = UUIDs.get().size();

    public OwnedItem(Item item) {
        this.itemUuid = item.getUuid();
    }

    public OwnedItem() {
    }

    public int getCount() {
        this.count = UUIDs.get().size();
        return count;
    }

    public UUID getItemUuid() {
        return itemUuid;
    }

    public OwnedItem add() {
        return add(1);
    }

    public OwnedItem add(int amount) {
        UUIDs.getAndUpdate(u -> {
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
        UUIDs.getAndUpdate(u -> {
            IntStream.range(0, amount)
                    .forEach(i -> u.remove(UUIDs.get().last()));
            return u;
        });
        return this;
    }

    public void setItemUuid(UUID itemUuid) {
        this.itemUuid = itemUuid;
    }

    public Set<UUID> getUUIDs() {
        return UUIDs.get();
    }

}
