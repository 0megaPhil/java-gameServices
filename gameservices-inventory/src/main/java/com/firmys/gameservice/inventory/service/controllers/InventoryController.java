package com.firmys.gameservice.inventory.service.controllers;

import com.firmys.gameservice.common.GameData;
import com.firmys.gameservice.common.GameServiceError;
import com.firmys.gameservice.common.GameServiceProperties;
import com.firmys.gameservice.common.ServiceConstants;
import com.firmys.gameservice.inventory.service.data.Inventory;
import com.firmys.gameservice.inventory.service.data.Item;
import com.firmys.gameservice.inventory.service.data.OwnedItem;
import com.firmys.gameservice.inventory.service.inventory.InventoryDataLookup;
import com.firmys.gameservice.inventory.service.inventory.InventoryService;
import com.firmys.gameservice.inventory.service.item.ItemDataLookup;
import com.firmys.gameservice.inventory.service.item.ItemService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@EnableConfigurationProperties(GameServiceProperties.class)
public class InventoryController {
    public static final String baseDataName = ServiceConstants.INVENTORY;
    public static final String basePath = "/" + ServiceConstants.INVENTORY;
    public static final String uuidPath = "/" + "{" + ServiceConstants.UUID + "}";

    private final InventoryService inventoryService;
    private final InventoryDataLookup dataLookup;
    private final ItemDataLookup itemDataLookup;
    private final ItemService itemService;

    public InventoryController(
            InventoryService inventoryService,
            InventoryDataLookup dataLookup,
            ItemDataLookup itemDataLookup,
            ItemService itemService) {
        this.itemDataLookup = itemDataLookup;
        this.itemService = itemService;
        this.inventoryService = inventoryService;
        this.dataLookup = dataLookup;
    }

    @GetMapping(basePath)
    public Set<GameData> findAll() {
        return StreamSupport.stream(inventoryService.findAll(Sort.unsorted()).spliterator(), false)
                .collect(Collectors.toSet());
    }

    @PostMapping(basePath)
    public GameData save(@RequestBody Inventory entity) {
        return inventoryService.save(entity);
    }

    @PutMapping(basePath + uuidPath + ItemController.basePath)
    public GameData addOwnedItem(
            @PathVariable(ServiceConstants.UUID) String uuid,
            @RequestParam(value = "item", required = false) String uuidParam,
            @RequestBody(required = false) Item entityBody) {
        try {
            String itemUuid = Optional.ofNullable(uuidParam).orElse(entityBody.getUuid().toString());
            Inventory inventory = (Inventory) findByUuid(uuid);
            Item foundItem = itemService.findById(itemDataLookup.getPrimaryKeyByUuid(itemUuid))
                    .orElseThrow(() -> new RuntimeException("Unable to find entity for " + itemUuid));
            Set<OwnedItem> ownedItems = Optional.ofNullable(inventory.getOwnedItems()).orElse(new HashSet<>());
            ownedItems.add(new OwnedItem(foundItem));
            inventory.setOwnedItems(ownedItems);
            save(inventory);
        } catch (Throwable e) {
            return new GameServiceError(entityBody, baseDataName, e.getMessage(), e);
        }
        return findByUuid(uuid);
    }

    @DeleteMapping(basePath + uuidPath + ItemController.basePath)
    public GameData consumeOwnedItem(
            @PathVariable(ServiceConstants.UUID) String uuid,
            @RequestParam(value = "item", required = false) String uuidParam,
            @RequestBody(required = false) Item entityBody) {
        try {
            String itemUuid = Optional.ofNullable(uuidParam).orElse(entityBody.getUuid().toString());
            Inventory inventory = (Inventory) findByUuid(uuid);
            Item foundItem = itemService.findById(itemDataLookup.getPrimaryKeyByUuid(itemUuid)).orElseThrow();
            Set<OwnedItem> ownedItems = Optional.ofNullable(inventory.getOwnedItems()).orElse(new HashSet<>());
            OwnedItem consumable = ownedItems.stream().filter(i -> i.getItem().getUuid().toString()
                            .equals(foundItem.getUuid().toString())).findFirst()
                    .orElseThrow(() -> new RuntimeException("Unable to find entity for " + itemUuid));
            ownedItems.remove(consumable);
            inventory.setOwnedItems(ownedItems);
            save(inventory);
        } catch (Throwable e) {
            return new GameServiceError(entityBody, baseDataName, e.getMessage(), e);
        }
        return findByUuid(uuid);
    }

    @DeleteMapping(basePath)
    public void delete(@RequestBody Inventory entity) {
        inventoryService.deleteById(dataLookup.getPrimaryKeyByUuid(entity.getUuid()));
    }

    @GetMapping(basePath + uuidPath)
    public GameData findByUuid(@PathVariable(ServiceConstants.UUID) String uuidString) {
        return inventoryService.findById(dataLookup.getPrimaryKeyByUuid(uuidString)).orElseThrow(
                () -> new RuntimeException(
                        new GameServiceError(null, baseDataName + ": " + " not found by uuid",
                                "No matching record found", null).toString()));
    }

    @DeleteMapping(basePath + uuidPath)
    public void deleteByUuid(@PathVariable(ServiceConstants.UUID) String uuidString) {
        inventoryService.deleteById(dataLookup.getPrimaryKeyByUuid(uuidString));
    }

    @PutMapping(basePath)
    public GameData update(
            @RequestParam(value = "inventory", required = false) String uuidParam,
            @RequestBody Inventory entityBody) {
        String uuidString = Optional.ofNullable(uuidParam).orElse(entityBody.getUuid().toString());
        Inventory existing = (Inventory) findByUuid(uuidString);
        existing.setOwnedItems(
                Optional.ofNullable(entityBody.getOwnedItems()).orElse(existing.getOwnedItems())
        );
        existing.setCurrency(
                Optional.of(entityBody.getCurrency()).orElse(existing.getCurrency())
        );
        save(existing);
        return dataLookup.getDataByUuid(uuidString);
    }

}
