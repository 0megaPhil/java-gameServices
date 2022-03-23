package com.firmys.gameservice.inventory.service;

import com.firmys.gameservice.inventory.impl.Item;
import com.firmys.gameservice.inventory.impl.ItemRepository;
import com.firmys.gameservice.inventory.impl.LocationInventory;
import com.firmys.gameservice.inventory.impl.OwnedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    // TODO - Setup character inventory

    @Autowired
    LocationInventory locationInventory;

    public UUID addOwnedItem(Item item) {
        return locationInventory.addOwnedItem(item);
    }

    public OwnedItem getOwnedItem(UUID uuid) {
        return locationInventory.getOwnedItem(uuid);
    }

    public List<OwnedItem> getOwnedItems() {
        return locationInventory.getInventoryItems().keySet().stream()
                .flatMap(k -> locationInventory.getInventoryItems().get(k).stream()).collect(Collectors.toList());
    }

}
