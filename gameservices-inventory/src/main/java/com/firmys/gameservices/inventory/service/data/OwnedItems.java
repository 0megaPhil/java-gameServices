package com.firmys.gameservices.inventory.service.data;

import com.firmys.gameservices.common.AbstractGameData;
import com.firmys.gameservices.common.GameData;
import com.firmys.gameservices.common.error.GameServiceError;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class OwnedItems extends AbstractGameData {
    private UUID uuid = UUID.randomUUID();
    private Map<UUID, OwnedItem> ownedItemMap = new ConcurrentHashMap<>();

    public Map<UUID, OwnedItem> getOwnedItemMap() {
        return ownedItemMap;
    }

    public void setOwnedItemMap(Map<UUID, OwnedItem> ownedItemMap) {
        this.ownedItemMap = ownedItemMap;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public OwnedItems addItem(Item item, int amount) {
        OwnedItem ownedItem = ownedItemMap.computeIfAbsent(item.getUuid(), v -> new OwnedItem(item));
        ownedItem.add(amount);
        clearEmpty();
        return this;
    }

    public OwnedItems consumeItem(Item item, int amount) {
        OwnedItem ownedItem = ownedItemMap.computeIfAbsent(item.getUuid(), v -> new OwnedItem(item));
        ownedItem.consume(amount);
        clearEmpty();
        return this;
    }

    private void clearEmpty() {
        this.setOwnedItemMap(this.getOwnedItemMap()
                .entrySet().stream().filter(e -> e.getValue().getCount() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    public OwnedItem getOwnedItemByItem(Item item) {
        return Optional.ofNullable(this.getOwnedItemMap().get(item.getUuid())).orElse(new OwnedItem(item));
    }

    @Override
    public void setError(GameServiceError error) {

    }

    @Override
    public GameServiceError getError() {
        return null;
    }
}
