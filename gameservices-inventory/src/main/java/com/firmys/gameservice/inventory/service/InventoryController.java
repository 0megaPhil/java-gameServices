package com.firmys.gameservice.inventory.service;

import com.firmys.gameservice.inventory.impl.Item;
import com.firmys.gameservice.inventory.impl.OwnedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @Autowired
    ItemService itemService;

    @GetMapping("/inventory")
    private List<OwnedItem> getAllItems() {
        return inventoryService.getOwnedItems();
    }

    @PostMapping("/inventory")
    private UUID addItem(@RequestBody Item item) {
        itemService.saveOrUpdate(item);
        return inventoryService.addOwnedItem(item);
    }
}
