package com.firmys.gameservice.inventory.service.data;

import com.firmys.gameservice.common.GameData;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class OwnedItems implements GameData {
    private Set<OwnedItem> ownedItemsSet = ConcurrentHashMap.newKeySet();

    public OwnedItems withOwnedItemSet (Set<OwnedItem> ownedItemsSet) {
        this.ownedItemsSet = ownedItemsSet;
        return this;
    }

    public OwnedItems withOwnedItem (OwnedItem ownedItem) {
        this.ownedItemsSet.add(ownedItem);
        return this;
    }

    public Set<OwnedItem> getOwnedItemsSet() {
        return ownedItemsSet;
    }

}
