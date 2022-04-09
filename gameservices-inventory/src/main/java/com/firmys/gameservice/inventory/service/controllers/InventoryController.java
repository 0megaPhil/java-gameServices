package com.firmys.gameservice.inventory.service.controllers;

import com.firmys.gameservice.common.AbstractController;
import com.firmys.gameservice.common.GameServiceProperties;
import com.firmys.gameservice.common.ServicePaths;
import com.firmys.gameservice.inventory.service.data.Currency;
import com.firmys.gameservice.inventory.service.data.Inventory;
import com.firmys.gameservice.inventory.service.data.Item;
import com.firmys.gameservice.inventory.service.data.OwnedItem;
import com.firmys.gameservice.inventory.service.inventory.InventoryDataLookup;
import com.firmys.gameservice.inventory.service.inventory.InventoryService;
import com.firmys.gameservice.inventory.service.inventory.InventoryUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@EnableConfigurationProperties(GameServiceProperties.class)
public class InventoryController extends AbstractController<Inventory> {
    private final ItemController itemController;
    private final CurrencyController currencyController;

    public InventoryController(
            InventoryService inventoryService,
            InventoryDataLookup dataLookup,
            ItemController itemController,
            CurrencyController currencyController) {
        super(inventoryService, dataLookup, Inventory.class, Inventory::new);
        this.itemController = itemController;
        this.currencyController = currencyController;
    }

    /**
     * {@link ServicePaths#INVENTORIES_PATH}
     * Multiple methods do not support UUID as path variable
     *
     * Some methods, such as findMultiple, can collect UUIDs across parameters and body array
     */
    @GetMapping(ServicePaths.INVENTORIES_PATH)
    public Set<Inventory> findAll() {
        return super.findAll();
    }

    @GetMapping(ServicePaths.INVENTORIES_PATH)
    public Set<Inventory> findMultiple(
            @RequestParam(value = ServicePaths.UUID, required = false) Set<String> uuidParams,
            @RequestBody(required = false) Set<Inventory> entities) {
        return super.findByUuids(gatherUuids(uuidParams, entities));
    }

    @PostMapping(ServicePaths.INVENTORIES_PATH)
    public Set<Inventory> addMultiple(@RequestBody(required = false) Set<Inventory> entity) {
        return entity.stream().map(super::save).collect(Collectors.toSet());
    }

    @DeleteMapping(ServicePaths.INVENTORIES_PATH)
    public void deleteMultiple(
            @RequestParam(value = ServicePaths.UUID, required = false) Set<String> uuidParams,
            @RequestBody(required = false) Set<Inventory> entities) {
        super.deleteByUuids(gatherUuids(uuidParams, entities));
    }

    @PutMapping(ServicePaths.INVENTORIES_PATH)
    public Set<Inventory> updateMultiple(
            @RequestBody Set<Inventory> entities) {
        return entities.stream().map(e -> super.updateByUuid(e.getUuid(), e)).collect(Collectors.toSet());
    }

    /**
     * {@link ServicePaths#INVENTORY_PATH}
     * Singular methods support UUID as part of path
     */
    @GetMapping(ServicePaths.INVENTORY_PATH)
    public Inventory findOne(
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestBody(required = false) Inventory entity) {
        return super.findByUuid(getUuidFromBodyOrParam(entity, uuidParam));
    }

    @GetMapping(ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE)
    public Inventory findOneByPath(@PathVariable(ServicePaths.UUID) String pathUuid) {
        return super.findByUuid(UUID.fromString(pathUuid));
    }

    @PostMapping(ServicePaths.INVENTORY_PATH)
    public Inventory addOne(@RequestBody(required = false) Inventory entity) {
        return super.save(entity);
    }

    @DeleteMapping(ServicePaths.INVENTORY_PATH)
    public void deleteOne(
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestBody(required = false) Inventory entity) {
        super.delete(UUID.fromString(uuidParam), entity);
    }

    @GetMapping(ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE)
    public Inventory findOneByUuid(@PathVariable(ServicePaths.UUID) String pathUuid) {
        return super.findByUuid(UUID.fromString(pathUuid));
    }

    @DeleteMapping(ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE)
    public void deleteOneByUuid(@PathVariable(ServicePaths.UUID) String pathUuid) {
        super.deleteByUuid(UUID.fromString(pathUuid));
    }

    @PutMapping(ServicePaths.INVENTORY_PATH)
    public Inventory updateOne(@RequestBody Inventory entity) {
        return super.update(entity);
    }

    @PutMapping(ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE)
    public Inventory updateForUuid(@PathVariable(ServicePaths.UUID) String pathUuid,
                                   @RequestBody Inventory entity) {
        return super.updateByUuid(UUID.fromString(pathUuid), entity);
    }

    /**
     * Complex control methods
     * Singular Inventory
     */
    @PutMapping(ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE + ServicePaths.CURRENCY_PATH)
    public Inventory creditCurrencyByUuid(
            @RequestParam(value = ServicePaths.UUID, required = false) String currencyUuid,
            @RequestParam(value = ServicePaths.AMOUNT) Integer amountParam,
            @PathVariable(ServicePaths.UUID) String uuid,
            @RequestBody(required = false) Currency requestBody) {
        return super.save(InventoryUtils.creditCurrency(
                currencyController.findByUuid(
                        getUuidFromBodyOrParam(requestBody, currencyUuid)),
                super.findByUuid(UUID.fromString(uuid)), amountParam));
    }

    @DeleteMapping(ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE + ServicePaths.CURRENCY_PATH)
    public Inventory debitCurrencyByUuid(
            @RequestParam(value = ServicePaths.UUID, required = false) String currencyUuid,
            @RequestParam(value = ServicePaths.AMOUNT) Integer amountParam,
            @PathVariable(ServicePaths.UUID) String uuid,
            @RequestBody(required = false) Currency requestBody) {
        return super.save(InventoryUtils.debitCurrency(
                currencyController.findByUuid(
                        getUuidFromBodyOrParam(requestBody, currencyUuid)),
                super.findByUuid(UUID.fromString(uuid)),
                amountParam));
    }

    @GetMapping(ServicePaths.INVENTORY_PATH + ServicePaths.ITEM_PATH + ServicePaths.UUID_PATH_VARIABLE)
    public Set<Inventory> findInventoriesWithItemByUuidPath(
            @PathVariable(value = ServicePaths.UUID, required = false) String uuid) {
        return InventoryUtils
                .getInventoriesWithItem(super.findAll(), itemController
                        .findByUuid(UUID.fromString(uuid)));
    }

    @GetMapping(ServicePaths.INVENTORY_PATH + ServicePaths.ITEM_PATH)
    public Set<Inventory> findInventoriesWithItemByUuidParam(
            @RequestParam(value = ServicePaths.UUID, required = false) String itemUuid) {
        return InventoryUtils
                .getInventoriesWithItem(super.findAll(), itemController
                        .findByUuid(UUID.fromString(itemUuid)));
    }


    @PutMapping(ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE + ServicePaths.ITEM_PATH)
    public Inventory addOwnedItem(
            @PathVariable(ServicePaths.UUID) String uuid,
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestParam(value = ServicePaths.AMOUNT, required = false) Integer amount,
            @RequestBody(required = false) Item requestBody) {
        return super.save(InventoryUtils.addOwnedItemByItemUuid(itemController
                        .findByUuid(getUuidFromBodyOrParam(requestBody, uuidParam)),
                super.findByUuid(UUID.fromString(uuid)), Optional.ofNullable(amount).orElse(1)));
    }

    @PutMapping(ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE + ServicePaths.ITEMS_PATH)
    public Inventory addOwnedItems(
            @PathVariable(ServicePaths.UUID) String uuid,
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestParam(value = ServicePaths.AMOUNT, required = false) Integer amount,
            @RequestBody(required = false) List<Item> requestBodies) {
        Inventory inventory = super.findByUuid(UUID.fromString(Optional.ofNullable(uuid).orElse(uuidParam)));
        List<Item> items = Optional.ofNullable(requestBodies).orElse(new ArrayList<>());
        if(uuidParam != null) {
            Item paramItem = new Item();
            paramItem.setUuid(UUID.fromString(uuidParam));
            items.add(paramItem);
        }
        for(Item item: items) {
            InventoryUtils.addOwnedItemByItemUuid(itemController
                            .findByUuid(item.getUuid()), inventory,
                    Optional.ofNullable(amount).orElse(1));
        }
        return super.save(inventory);
    }

    @DeleteMapping(ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE + ServicePaths.ITEM_PATH)
    public Inventory consumeOwnedItem(
            @PathVariable(ServicePaths.UUID) String uuid,
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestParam(value = ServicePaths.AMOUNT, required = false) Integer amount,
            @RequestBody(required = false) Item requestBody) {
        return super.save(InventoryUtils.consumeOwnedItemByItemUuid(
                itemController.findByUuid(getUuidFromBodyOrParam(
                        requestBody, uuidParam)), super.findByUuid(UUID.fromString(uuid)),
                Optional.ofNullable(amount).orElse(1)));
    }

    @DeleteMapping(ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE + ServicePaths.ITEMS_PATH)
    public Inventory consumeOwnedItems(
            @PathVariable(ServicePaths.UUID) String uuid,
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestParam(value = ServicePaths.AMOUNT, required = false) Integer amount,
            @RequestBody(required = false) List<Item> requestBodies) {
        Inventory inventory = super.findByUuid(UUID.fromString(Optional.ofNullable(uuid).orElse(uuidParam)));
        List<Item> items = Optional.ofNullable(requestBodies).orElse(new ArrayList<>());
        if(uuidParam != null) {
            Item paramItem = new Item();
            paramItem.setUuid(UUID.fromString(uuidParam));
            items.add(paramItem);
        }
        for(Item item: items) {
            InventoryUtils.consumeOwnedItemByItemUuid(itemController
                            .findByUuid(item.getUuid()), inventory,
                    Optional.ofNullable(amount).orElse(1));
        }
        return super.save(inventory);
    }

    @GetMapping(ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE + ServicePaths.ITEM_PATH)
    public Set<OwnedItem> getOwnedItemsByInventoryUuid(
            @PathVariable(value = ServicePaths.UUID, required = false) String uuid,
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestBody(required = false) Inventory requestBody) {
        return super.findByUuid(
                getUuidFromBodyOrParam(requestBody,
                        Optional.ofNullable(uuid).orElse(uuidParam))).getOwnedItems();
    }

}
