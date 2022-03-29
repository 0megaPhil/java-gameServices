package com.firmys.gameservice.inventory.service.controllers;

import com.firmys.gameservice.common.*;
import com.firmys.gameservice.inventory.service.currency.CurrencyDataLookup;
import com.firmys.gameservice.inventory.service.currency.CurrencyService;
import com.firmys.gameservice.inventory.service.data.*;
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
public class InventoryController extends AbstractController {
    public static final String baseDataName = ServiceConstants.INVENTORY;
    public static final String basePath = "/" + ServiceConstants.INVENTORY;
    public static final String uuidPath = "/" + "{" + ServiceConstants.UUID + "}";

    private final InventoryService inventoryService;
    private final InventoryDataLookup dataLookup;
    private final ItemDataLookup itemDataLookup;
    private final ItemService itemService;
    private final CurrencyDataLookup currencyDataLookup;
    private final CurrencyService currencyService;

    public InventoryController(
            InventoryService inventoryService,
            InventoryDataLookup dataLookup,
            ItemDataLookup itemDataLookup,
            ItemService itemService,
            CurrencyDataLookup currencyDataLookup,
            CurrencyService currencyService) {
        this.itemDataLookup = itemDataLookup;
        this.itemService = itemService;
        this.inventoryService = inventoryService;
        this.dataLookup = dataLookup;
        this.currencyDataLookup = currencyDataLookup;
        this.currencyService = currencyService;
    }

    @GetMapping(basePath)
    public Set<GameData> findAll() {
        return StreamSupport.stream(inventoryService.findAll(Sort.unsorted()).spliterator(), false)
                .collect(Collectors.toSet());
    }

    @PutMapping(basePath + uuidPath + CurrencyController.basePath)
    public GameData creditCurrencyByUuid(
            @RequestParam(value = "uuid", required = false) String uuidParam,
            @RequestParam(value = "amount") Integer amountParam,
            @PathVariable(ServiceConstants.UUID) String entityUuid,
            @RequestBody(required = false) Currency requestBody) {
        try {
            String itemUuid = getUuidFromBodyOrParam(requestBody, uuidParam).toString();
            Currency foundCurrency = currencyService.findById(currencyDataLookup.getPrimaryKeyByUuid(itemUuid))
                    .orElseThrow(() -> new RuntimeException("No Currency found for uuid " + itemUuid));
            Inventory foundInventory = (Inventory) findByUuid(entityUuid);
            OwnedCurrency ownedCurrency = foundInventory.getOwnedCurrency();
            ownedCurrency.creditCurrency(foundCurrency, amountParam);
            foundInventory.setOwnedCurrency(ownedCurrency);
            return save(foundInventory);
        } catch (Exception e) {
            return new GameServiceError(requestBody, ServiceConstants.CURRENCY, e.getMessage(), e);
        }
    }

    @DeleteMapping(basePath + uuidPath + CurrencyController.basePath)
    public GameData debitCurrencyByUuid(
            @RequestParam(value = "uuid", required = false) String uuidParam,
            @RequestParam(value = "amount") Integer amountParam,
            @PathVariable(ServiceConstants.UUID) String entityUuid,
            @RequestBody(required = false) Currency requestBody) {
        try {
            String itemUuid = getUuidFromBodyOrParam(requestBody, uuidParam).toString();
            Currency foundCurrency = currencyService.findById(currencyDataLookup.getPrimaryKeyByUuid(itemUuid))
                    .orElseThrow(() -> new RuntimeException("No Currency found for uuid " + itemUuid));
            Inventory foundInventory = (Inventory) findByUuid(entityUuid);
            OwnedCurrency ownedCurrency = foundInventory.getOwnedCurrency();
            ownedCurrency.debitCurrency(foundCurrency, amountParam);
            foundInventory.setOwnedCurrency(ownedCurrency);
            return save(foundInventory);
        } catch (Exception e) {
            return new GameServiceError(requestBody, ServiceConstants.CURRENCY, e.getMessage(), e);
        }
    }

    @GetMapping(basePath + uuidPath + ItemController.basePath)
    public Set<GameData> findInventoriesWithItemByUuid(
            @PathVariable(ServiceConstants.UUID) String itemUuid) {
        try {
            Item item = itemService.findById(itemDataLookup.getPrimaryKeyByUuid(itemUuid))
                    .orElseThrow(() -> new RuntimeException("Not item found for uuid " + itemUuid));
            Set<Inventory> inventories = findAll().stream().map(g -> (Inventory) g)
                    .filter(i -> i.getOwnedItems().stream().filter(o -> o.getItem() != null)
                            .anyMatch(o -> o.getItem().getUuid().toString().equals(itemUuid)))
                    .collect(Collectors.toSet());
            return inventories.stream().map(i -> (GameData) i).collect(Collectors.toSet());
        } catch (Exception e) {
            return Set.of(new GameServiceError(null, baseDataName, e.getMessage(), e));
        }
    }

    @PutMapping(basePath)
    public GameData save(
            @RequestParam(name = "uuid", required = false) String uuidParam,
            @RequestBody(required = false) Inventory requestBody) {
        String itemUuid = getUuidFromBodyOrParam(requestBody, uuidParam).toString();
        GameData gameData = findByUuid(itemUuid);
        if (gameData == null) {
            return new GameServiceError(requestBody, baseDataName, "Does not exist for " + itemUuid,
                    new RuntimeException("Does not exist for " + itemUuid));
        }
        Inventory original = (Inventory) gameData;
        original.update(requestBody);
        return inventoryService.save(original);
    }

    private GameData save(Inventory entityBody) {
        return save(null, entityBody);
    }

    @PostMapping(basePath)
    public GameData addInventory() {
        Inventory inventory = new Inventory();
        return inventoryService.save(inventory);
    }

    @PutMapping(basePath + uuidPath + ItemController.basePath)
    public GameData addOwnedItem(
            @PathVariable(ServiceConstants.UUID) String uuid,
            @RequestParam(value = "uuid", required = false) String uuidParam,
            @RequestBody(required = false) Item requestBody) {
        try {
            String itemUuid = getUuidFromBodyOrParam(requestBody, uuidParam).toString();
            Inventory inventory = (Inventory) findByUuid(uuid);
            Item foundItem = itemService.findById(itemDataLookup.getPrimaryKeyByUuid(itemUuid))
                    .orElseThrow(() -> new RuntimeException("Unable to find entity for " + itemUuid));
            Set<OwnedItem> ownedItems = Optional.ofNullable(inventory.getOwnedItems()).orElseGet(HashSet::new);
            ownedItems.add(new OwnedItem(foundItem));
            inventory.setOwnedItems(ownedItems);
            return save(inventory);
        } catch (Throwable e) {
            return new GameServiceError(requestBody, baseDataName, e.getMessage(), e);
        }
    }

    @DeleteMapping(basePath + uuidPath + ItemController.basePath)
    public GameData consumeOwnedItem(
            @PathVariable(ServiceConstants.UUID) String uuid,
            @RequestParam(value = "uuid", required = false) String uuidParam,
            @RequestBody(required = false) Item requestBody) {
        try {
            String itemUuid = getUuidFromBodyOrParam(requestBody, uuidParam).toString();
            Inventory inventory = (Inventory) findByUuid(uuid);
            Item foundItem = itemService.findById(itemDataLookup.getPrimaryKeyByUuid(itemUuid)).orElseThrow();
            Set<OwnedItem> ownedItems = Optional.ofNullable(inventory.getOwnedItems()).orElseGet(HashSet::new);
            OwnedItem consumable = ownedItems.stream().filter(i -> i.getItem().getUuid().toString()
                            .equals(foundItem.getUuid().toString())).findFirst()
                    .orElseThrow(() -> new RuntimeException("Unable to find entity for " + itemUuid));
            ownedItems.remove(consumable);
            inventory.setOwnedItems(ownedItems);
            return save(inventory);
        } catch (Throwable e) {
            return new GameServiceError(requestBody, baseDataName, e.getMessage(), e);
        }
    }

    @GetMapping(basePath + uuidPath)
    public GameData findByUuid(@PathVariable(ServiceConstants.UUID) String uuidString) {
        return inventoryService.findById(dataLookup.getPrimaryKeyByUuid(uuidString)).orElseThrow(
                () -> new RuntimeException(
                        new GameServiceError(null, baseDataName + ": " + " not found by uuid",
                                "No matching record found", null).toString()));
    }

    @DeleteMapping(basePath + uuidPath)
    public void deleteInventoryByUuid(@PathVariable(ServiceConstants.UUID) String uuidString) {
        inventoryService.deleteById(dataLookup.getPrimaryKeyByUuid(uuidString));
    }

    @DeleteMapping(basePath)
    public void deleteInventory(
            @RequestParam(value = "uuid", required = false) String uuidParam,
            @RequestBody(required = false) Inventory entityBody) {
        String uuidString = Optional.ofNullable(uuidParam).orElse(entityBody.getUuid().toString());
        inventoryService.deleteById(dataLookup.getPrimaryKeyByUuid(uuidString));
    }

}
